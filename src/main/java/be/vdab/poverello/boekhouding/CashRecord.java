package be.vdab.poverello.boekhouding;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CashRecord(
        @PositiveOrZero int totaalBedragBiljetten,
        @NotNull BigDecimal totaalBedragMunten2E,
        @NotNull BigDecimal totaalBedragMunten1E,
        @NotNull BigDecimal totaalBedragMunten50cE,
        @NotNull BigDecimal totaalBedragMunten20cE,
        @NotNull BigDecimal totaalBedragMunten10cE,
        @NotNull BigDecimal totaalBedragMuntenBruinE
) {
    CashRecord(Kasboek kasboek) {
        this(
        kasboek.getTotaalBedragBiljetten(),
        kasboek.getTotaalBedragMunten2E(),
        kasboek.getTotaalBedragMunten1E(),
        kasboek.getTotaalBedragMunten50cE(),
        kasboek.getTotaalBedragMunten20cE(),
        kasboek.getTotaalBedragMunten10cE(),
        kasboek.getTotaalBedragMuntenBruinE()
        );
    }
}