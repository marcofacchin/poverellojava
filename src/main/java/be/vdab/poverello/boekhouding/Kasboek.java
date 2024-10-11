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
    private long afdelingId;
    private int jaar;
    private int maand;
    private int totaalBedragBiljetten;
    @Column(name = "totaalBedragMunten2E", precision = 4, scale = 2)
    private BigDecimal totaalBedragMunten2E = BigDecimal.ZERO;
    @Column(name = "totaalBedragMunten1E", precision = 4, scale = 2)
    private BigDecimal totaalBedragMunten1E = BigDecimal.ZERO;
    @Column(name = "totaalBedragMunten50cE", precision = 4, scale = 2)
    private BigDecimal totaalBedragMunten50cE = BigDecimal.ZERO;
    @Column(name = "totaalBedragMunten20cE", precision = 4, scale = 2)
    private BigDecimal totaalBedragMunten20cE = BigDecimal.ZERO;
    @Column(name = "totaalBedragMunten10cE", precision = 4, scale = 2)
    private BigDecimal totaalBedragMunten10cE = BigDecimal.ZERO;
    @Column(name = "totaalBedragMuntenBruinE", precision = 4, scale = 2)
    private BigDecimal totaalBedragMuntenBruinE = BigDecimal.ZERO;
    @ElementCollection
    @CollectionTable(name = "verrichtingen",
    joinColumns = @JoinColumn(name = "kasboekId"))
    @OrderBy("volgnummer")
    private Set<Verrichting> verrichtingen;
    @Version
    private long versie;

    public Kasboek(long afdelingId, int jaar, int maand
    ) {
        this.afdelingId = afdelingId;
        this.jaar = jaar;
        this.maand = maand;
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

    public long getVersie() {

        return versie;
    }

    public CashRecord getCash() {
        return new CashRecord(this);
    }

    public void setAfdelingId(long afdelingId) {
        this.afdelingId = afdelingId;
    }

    public void setJaar(int jaar) {
        this.jaar = jaar;
    }

    public void setMaand(int maand) {
        this.maand = maand;
    }

    public void setCash(CashRecord cashRecord) {
        this.totaalBedragBiljetten = cashRecord.totaalBedragBiljetten();
        this.totaalBedragMunten2E = cashRecord.totaalBedragMunten2E();
        this.totaalBedragMunten1E = cashRecord.totaalBedragMunten1E();
        this.totaalBedragMunten50cE = cashRecord.totaalBedragMunten50cE();
        this.totaalBedragMunten20cE = cashRecord.totaalBedragMunten20cE();
        this.totaalBedragMunten10cE = cashRecord.totaalBedragMunten10cE();
        this.totaalBedragMuntenBruinE = cashRecord.totaalBedragMuntenBruinE();
    }

    public void voegVerrichtingToe(Verrichting verrichting) {
        if (! verrichtingen.add(verrichting)) {
            throw new VerrichtingBestaatAlException();
        }
    }

    public void verwijderVerrichting(Verrichting verrichting) {
        verrichtingen.remove(verrichting);
    }

    public void wijzigVerrichting(NieuweVerrichting nieuweVerrichting, Omschrijving omschrijving) {
        var oudeVerrichting = verrichtingen.stream().filter(element -> element.getVolgnummer() == nieuweVerrichting.volgnummer())
                .findFirst()
                .orElseThrow(() -> new VerrichtingNietGevondenException());
        verrichtingen.remove(oudeVerrichting);
        var verrichting = new Verrichting(nieuweVerrichting.volgnummer(), nieuweVerrichting.dag(), nieuweVerrichting.bedrag(), omschrijving, nieuweVerrichting.kasticket(), nieuweVerrichting.verrichtingsType());
        verrichtingen.add(verrichting);
    }
}