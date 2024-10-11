package be.vdab.poverello.boekhouding;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record NieuweVerrichting(
        @PositiveOrZero int volgnummer,
        @Positive int dag,
        @NotNull BigDecimal bedrag,
        @PositiveOrZero long omschrijvingId,
        @Positive long afdelingId,
        @NotBlank String omschrijving,
        Boolean kasticket,
        VerrichtingsType verrichtingsType
) {
}
