package be.vdab.poverello.boekhouding;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NieuwKasboek(
        @NotNull @Positive long afdelingId,
        @NotNull @Positive int jaar,
        @NotNull @Positive int maand) {
}
