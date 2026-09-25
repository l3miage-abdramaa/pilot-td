package td.pilot.backend.domaine.modele;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PeriodeTest {

    private static final LocalDate DEBUT = LocalDate.of(2027, 1, 15);
    private static final LocalDate FIN = LocalDate.of(2027, 10, 31);

    @Nested
    @DisplayName("RG-VO-04 : dateFin >= dateDebut")
    class RegleDesDates {

        @Test
        @DisplayName("accepte une periode dont la fin suit le debut")
        void accepteUnePeriodeValide() {
            assertThatCode(() -> new Periode(DEBUT, FIN)).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("accepte une periode d'une seule journee")
        void accepteUnePeriodeDUnJour() {
            assertThatCode(() -> new Periode(DEBUT, DEBUT)).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("refuse une periode dont la fin precede le debut")
        void refuseUnePeriodeInversee() {
            assertThatThrownBy(() -> new Periode(FIN, DEBUT))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("RG-VO-04");
        }

        @Test
        @DisplayName("refuse une date nulle")
        void refuseUneDateNulle() {
            assertThatThrownBy(() -> new Periode(null, FIN))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("contient")
    class Contient {

        private final Periode periode = new Periode(DEBUT, FIN);

        @Test
        @DisplayName("inclut les bornes")
        void inclutLesBornes() {
            assertThat(periode.contient(DEBUT)).isTrue();
            assertThat(periode.contient(FIN)).isTrue();
        }

        @Test
        @DisplayName("exclut une date hors periode")
        void exclutUneDateHors() {
            assertThat(periode.contient(DEBUT.minusDays(1))).isFalse();
            assertThat(periode.contient(FIN.plusDays(1))).isFalse();
        }

        @Test
        @DisplayName("reconnait une periode incluse et en refuse une qui deborde")
        void reconnaitUnePeriodeIncluse() {
            assertThat(periode.contient(new Periode(DEBUT.plusDays(10), FIN.minusDays(10)))).isTrue();
            assertThat(periode.contient(new Periode(DEBUT, FIN.plusDays(1)))).isFalse();
        }
    }
}