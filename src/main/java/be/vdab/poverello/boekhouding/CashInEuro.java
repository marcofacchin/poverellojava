package be.vdab.poverello.boekhouding;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CashInEuro(
        @PositiveOrZero int totaalBedragBiljetten,
        @NotNull BigDecimal totaalBedragMunten2E,
        @NotNull BigDecimal totaalBedragMunten1E,
        @NotNull BigDecimal totaalBedragMunten50cE,
        @NotNull BigDecimal totaalBedragMunten20cE,
        @NotNull BigDecimal totaalBedragMunten10cE,
        @NotNull BigDecimal totaalBedragMuntenBruinE
) {
}