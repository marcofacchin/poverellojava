package be.poverello.boekhouding;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record NieuwKasboek(
        @NotNull @Positive long afdelingId,
        @NotNull @Positive int jaar,
        @NotNull @Positive int maand,
        @NotNull BigDecimal beginSaldo) {
}
