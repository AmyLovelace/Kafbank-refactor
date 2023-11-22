package com.ms.bf.client.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_ERROR(100, Series.SERVER_ERROR,"Error interno del servidor"),
    WEB_CLIENT_GENERIC(101, Series.CLIENT_ERROR,"Error del Web client"),
    INVALID_CLIENT_NAME(103,Series.CLIENT_ERROR,"error en el formato del nombre "),
    CLIENT_INVALID_REQUEST(102, Series.CLIENT_ERROR, "datos invalidos o con formato no aplicable "),
    CARD_BAD_REQUEST(103, Series.CLIENT_ERROR, "La tarjeta no existe o peticion invalida"),
    ACCOUNT_NOT_FOUND(104, Series.CLIENT_ERROR,"No se encontro numero de cuenta de usuario"),
    CARD_TIMEOUT(105, Series.SERVER_ERROR,"El llamado a tarjeta devolvio error"),
    INVALID_ACCOUNT_NUMBER(103, Series.CLIENT_ERROR,"error en formato de numero de cuenta"),
    INVALID_ACCOUNT_AGE(102,Series.CLIENT_ERROR,"error en formato de numero de cuenta");

    private static final ErrorCode[] VALUES;

    static {
        VALUES = values();
    }

    private final int value;
    private final  Series series;
    private final String reason;

    /**
     * Return the integer value of this status code.
     */
    public int value() {
        return this.value;
    }


    public Series series() {
        return this.series;
    }
    /**
     * Return the reason of this status code.
     */
    public String getReasons() {
        return this.reason;
    }

    public enum Series {

        INFORMATIONAL(1),
        SUCCESSFUL(2),
        REDIRECTION(3),
        CLIENT_ERROR(4),
        SERVER_ERROR(5);

        private final int value;

        Series(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }

        @Deprecated
        public static Series valueOf(ErrorCode status) {
            return status.series;
        }

        @Nullable
        public static ErrorCode.Series resolve(int statusCode) {
            int seriesCode = statusCode / 100;
            for (ErrorCode.Series series : values()) {
                if (series.value == seriesCode) {
                    return series;
                }
            }
            return null;
        }
    }


 }

