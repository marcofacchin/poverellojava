package be.vdab.poverello.boekhouding;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OmschrijvingBestaatAlException extends RuntimeException {
    public OmschrijvingBestaatAlException() {
        super("Deze omschrijving bestaat al.");
    }
}
