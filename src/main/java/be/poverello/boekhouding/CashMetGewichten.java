package be.poverello.boekhouding;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CashMetGewichten(
        @NotNull BigDecimal totaalGewichtMunten2E,
        @NotNull BigDecimal totaalGewichtMunten1E,
        @NotNull BigDecimal totaalGewichtMunten50cE,
        @NotNull BigDecimal totaalGewichtMunten20cE,
        @NotNull BigDecimal totaalGewichtMunten10cE,
        @NotNull BigDecimal totaalGewichtMuntenBruinE
) {
    CashMetGewichten(Kasboek kasboek) {
        this(
        kasboek.getTotaalGewichtMunten2E(),
        kasboek.getTotaalGewichtMunten1E(),
        kasboek.getTotaalGewichtMunten50cE(),
        kasboek.getTotaalGewichtMunten20cE(),
        kasboek.getTotaalGewichtMunten10cE(),
        kasboek.getTotaalGewichtMuntenBruinE()
        );
    }
}