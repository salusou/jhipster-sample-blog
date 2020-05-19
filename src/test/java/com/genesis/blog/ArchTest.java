package com.genesis.blog;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.genesis.blog");

        noClasses()
            .that()
            .resideInAnyPackage("com.genesis.blog.service..")
            .or()
            .resideInAnyPackage("com.genesis.blog.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.genesis.blog.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
