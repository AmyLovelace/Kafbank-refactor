package com.ms.bf.card.rules;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;


import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;


/**
 * packages: Debe ir el nombre del package del ms.
  */
@AnalyzeClasses(packages = "com.ms.bf.card", importOptions = ImportOption.DoNotIncludeTests.class)
public class RulesTest {

    @ArchTest
    public static final ArchRule execptions_respect_convention_names= classes()
            .that().resideInAnyPackage("..exception..")
            .should().haveSimpleNameEndingWith("Exception");

    @ArchTest
    static ArchRule no_generic_exception = NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

    @ArchTest
    static ArchRule no_standard_streams = NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

    @ArchTest
    static ArchRule no_java_logging = NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;

    private static final String DOMAIN = "Domain";
    private static final String ADAPTER = "Adapter";
    private static final String APPLICATION = "Application";
    private static final String CONFIG = "Config";


    @ArchTest
    public static final ArchRule layer_dependencies = Architectures.layeredArchitecture().consideringOnlyDependenciesInLayers()

            .layer(CONFIG).definedBy("com.ms.bf.card.config..")
            .layer(DOMAIN).definedBy("com.ms.bf.card.domain..")
            .layer(ADAPTER).definedBy("com.ms.bf.card.adapter..")
            .layer(APPLICATION).definedBy("com.ms.bf.card.application..")

            .whereLayer(APPLICATION).mayOnlyBeAccessedByLayers(ADAPTER,CONFIG)
            .whereLayer(ADAPTER).mayOnlyBeAccessedByLayers(CONFIG)
            .whereLayer(DOMAIN).mayOnlyBeAccessedByLayers(APPLICATION,ADAPTER,CONFIG);
}
