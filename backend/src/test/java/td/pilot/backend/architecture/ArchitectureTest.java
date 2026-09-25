package td.pilot.backend.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(
        packages = "td.pilot.backend",
        importOptions = ImportOption.DoNotIncludeTests.class)
class ArchitectureTest {

    @ArchTest
    static final ArchRule leDomaineNeDependPasDuFramework =
            noClasses()
                    .that().resideInAPackage("..domaine..")
                    .should().dependOnClassesThat()
                    .resideInAnyPackage(
                            "org.springframework..",
                            "jakarta.persistence..",
                            "org.hibernate..")
                    .because("le domaine doit rester independant du framework (TASK-002)");

    // TODO : une seconde regle, le domaine ne depend pas de la couche infrastructure
    @ArchTest
    static final ArchRule leDomaineNeDependPasDeLInfrastructure =
            noClasses()
                    .that().resideInAPackage("..domaine..")
                    .should().dependOnClassesThat()
                    .resideInAnyPackage(
                            "td.pilot.backend.infrastructure..")
                    .because("le domaine doit rester independant de l'infrastructure (TASK-002)");
}