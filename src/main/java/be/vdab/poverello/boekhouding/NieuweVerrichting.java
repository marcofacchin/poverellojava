package be.vdab.poverello.boekhouding;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record NieuweVerrichting(
        @NotBlank String datum,
        @PositiveOrZero int afdelingId,
        @NotNull BigDecimal bedrag,
        @PositiveOrZero int omschrijvingId,
        @PositiveOrZero int typeId,
        Boolean kasticket
) {
}
