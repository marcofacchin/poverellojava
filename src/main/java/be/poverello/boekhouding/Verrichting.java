package be.poverello.boekhouding;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Verrichting {
    private int volgnummer;
    private int dag;
    private BigDecimal bedrag;
    private String omschrijving;
    private Boolean kasticket;
    //@Enumerated(EnumType.STRING)
    //private VerrichtingsType verrichtingsType;

    public Verrichting(int volgnummer, int dag, BigDecimal bedrag, String omschrijving, Boolean kasticket) {
        this.volgnummer = volgnummer;
        this.dag = dag;
        this.bedrag = bedrag;
        this.omschrijving = omschrijving;
        this.kasticket = kasticket;
        //this.verrichtingsType = verrichtingsType;
    }

    public Verrichting() {
    }

    public int getVolgnummer() {
        return volgnummer;
    }

    public int getDag() {
        return dag;
    }

    public BigDecimal getBedrag() {
        return bedrag;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public Boolean getKasticket() {
        return kasticket;
    }

//    public VerrichtingsType getVerrichtingsType() {
//        return verrichtingsType;
//    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Verrichting that)) return false;
        return volgnummer == that.volgnummer && dag == that.dag && Objects.equals(bedrag, that.bedrag) && Objects.equals(omschrijving, that.omschrijving);
    }

    @Override
    public int hashCode() {
        return Objects.hash(volgnummer, dag, bedrag, omschrijving);
    }
}
