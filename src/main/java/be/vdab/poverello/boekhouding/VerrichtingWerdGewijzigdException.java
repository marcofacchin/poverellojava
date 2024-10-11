package be.vdab.poverello.boekhouding;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class VerrichtingWerdGewijzigdException extends RuntimeException {
    public VerrichtingWerdGewijzigdException() {
        super("De verrichting werd gewijzigd door een andere gebruiker.");
    }
}
