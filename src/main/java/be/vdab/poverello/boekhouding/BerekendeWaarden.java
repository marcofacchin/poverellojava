package be.vdab.poverello.boekhouding;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BerekendeWaarden(
        @NotNull BigDecimal totaalIn,
        @NotNull BigDecimal totaalUit,
        @NotNull BigDecimal totaalSaldo,
        @NotNull BigDecimal verschil
        ) {
}
