package be.vdab.poverello.boekhouding;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "kleinekassa")
public class Verrichting {
    @Id
    private long id;
    private Date datum;
    private BigDecimal bedrag;

    public long getId() {
        return id;
    }

    public Date getDatum() {
        return datum;
    }

    public BigDecimal getBedrag() {
        return bedrag;
    }
}
