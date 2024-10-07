package be.vdab.poverello.boekhouding;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "kleinekassa")
public class Verrichting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int volgnummer;
    private Date datum;
    private BigDecimal bedrag;
    private Boolean kasticket;

    public Verrichting(int volgnummer, Date datum, BigDecimal bedrag, Boolean kasticket) {
        this.volgnummer = volgnummer;
        this.datum = datum;
        this.bedrag = bedrag;
        this.kasticket = kasticket;
    }

    protected Verrichting() {
    }

    public long getId() {
        return id;
    }

    public Date getDatum() {
        return datum;
    }

    public BigDecimal getBedrag() {
        return bedrag;
    }

    public int getVolgnummer() {
        return volgnummer;
    }

    public Boolean getKasticket() {
        return kasticket;
    }
}
