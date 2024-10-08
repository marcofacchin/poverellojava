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
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "omschrijvingId")
    private Omschrijving omschrijving;
    private BigDecimal bedrag;
    private Boolean kasticket;

    public Verrichting(int volgnummer, Date datum, Omschrijving omschrijving, BigDecimal bedrag, Boolean kasticket) {
        this.volgnummer = volgnummer;
        this.datum = datum;
        this.omschrijving = omschrijving;
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

    public Omschrijving getOmschrijving() {
        return omschrijving;
    }
}
