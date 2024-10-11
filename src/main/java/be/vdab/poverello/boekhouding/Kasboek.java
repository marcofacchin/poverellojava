package be.vdab.poverello.boekhouding;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "kasboeken")
public class Kasboek {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
/*    @ManyToOne(optional = false)
    @JoinColumn(name = "afdelingId")
    private Afdeling afdeling;*/
    private long afdelingId;
    private int jaar;
    private int maand;
    private int totaalBedragBiljetten;
    @Column(name = "totaalBedragMunten2E", precision = 4, scale = 2)
    private BigDecimal totaalBedragMunten2E;
    @Column(name = "totaalBedragMunten1E", precision = 4, scale = 2)
    private BigDecimal totaalBedragMunten1E;
    @Column(name = "totaalBedragMunten50cE", precision = 4, scale = 2)
    private BigDecimal totaalBedragMunten50cE;
    @Column(name = "totaalBedragMunten20cE", precision = 4, scale = 2)
    private BigDecimal totaalBedragMunten20cE;
    @Column(name = "totaalBedragMunten10cE", precision = 4, scale = 2)
    private BigDecimal totaalBedragMunten10cE;
    @Column(name = "totaalBedragMuntenBruinE", precision = 4, scale = 2)
    private BigDecimal totaalBedragMuntenBruinE;
    @ElementCollection
    @CollectionTable(name = "verrichtingen",
    joinColumns = @JoinColumn(name = "kasboekId"))
    @OrderBy("volgnummer")
    private Set<Verrichting> verrichtingen;

    public Kasboek(long id, long afdelingId, int jaar, int maand,
                   int totaalBedragBiljetten,
                   BigDecimal totaalBedragMunten2E,
                   BigDecimal totaalBedragMunten1E,
                   BigDecimal totaalBedragMunten50cE,
                   BigDecimal totaalBedragMunten20cE,
                   BigDecimal totaalBedragMunten10cE,
                   BigDecimal totaalBedragMuntenBruinE) {
        this.id = id;
        this.afdelingId = afdelingId;
        this.jaar = jaar;
        this.maand = maand;
        this.totaalBedragBiljetten = totaalBedragBiljetten;
        this.totaalBedragMunten2E = totaalBedragMunten2E;
        this.totaalBedragMunten1E = totaalBedragMunten1E;
        this.totaalBedragMunten50cE = totaalBedragMunten50cE;
        this.totaalBedragMunten20cE = totaalBedragMunten20cE;
        this.totaalBedragMunten10cE = totaalBedragMunten10cE;
        this.totaalBedragMuntenBruinE = totaalBedragMuntenBruinE;
        verrichtingen = new LinkedHashSet<Verrichting>();
    }

    public Kasboek() {
    }

    public long getId() {
        return id;
    }

    public long getAfdelingId() {
        return afdelingId;
    }

    public int getJaar() {
        return jaar;
    }

    public int getMaand() {
        return maand;
    }

    public int getTotaalBedragBiljetten() {
        return totaalBedragBiljetten;
    }

    public BigDecimal getTotaalBedragMunten2E() {
        return totaalBedragMunten2E;
    }

    public BigDecimal getTotaalBedragMunten1E() {
        return totaalBedragMunten1E;
    }

    public BigDecimal getTotaalBedragMunten50cE() {
        return totaalBedragMunten50cE;
    }

    public BigDecimal getTotaalBedragMunten20cE() {
        return totaalBedragMunten20cE;
    }

    public BigDecimal getTotaalBedragMunten10cE() {
        return totaalBedragMunten10cE;
    }

    public BigDecimal getTotaalBedragMuntenBruinE() {
        return totaalBedragMuntenBruinE;
    }

    public Set<Verrichting> getVerrichtingen() {
        return Collections.unmodifiableSet(verrichtingen);
    }

}