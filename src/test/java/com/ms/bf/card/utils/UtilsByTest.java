package com.ms.bf.card.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UtilsByTest {

    public static String JsonToString(final Object object){
        try {
            return new ObjectMapper().writeValueAsString(object);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
