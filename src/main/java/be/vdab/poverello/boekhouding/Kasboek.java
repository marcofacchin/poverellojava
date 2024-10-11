package be.vdab.poverello.boekhouding;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    @Column(name = "totaalGewichtMunten2E", precision = 4, scale = 2)
    private BigDecimal totaalGewichtMunten2E = BigDecimal.ZERO;
    @Column(name = "totaalGewichtMunten1E", precision = 4, scale = 2)
    private BigDecimal totaalGewichtMunten1E = BigDecimal.ZERO;
    @Column(name = "totaalGewichtMunten50cE", precision = 4, scale = 2)
    private BigDecimal totaalGewichtMunten50cE = BigDecimal.ZERO;
    @Column(name = "totaalGewichtMunten20cE", precision = 4, scale = 2)
    private BigDecimal totaalGewichtMunten20cE = BigDecimal.ZERO;
    @Column(name = "totaalGewichtMunten10cE", precision = 4, scale = 2)
    private BigDecimal totaalGewichtMunten10cE = BigDecimal.ZERO;
    @Column(name = "totaalGewichtMuntenBruinE", precision = 4, scale = 2)
    private BigDecimal totaalGewichtMuntenBruinE = BigDecimal.ZERO;
    @Column(name = "beginSaldo", precision = 4, scale = 2)
    private BigDecimal beginSaldo = BigDecimal.ZERO;
    @ElementCollection
    @CollectionTable(name = "verrichtingen",
    joinColumns = @JoinColumn(name = "kasboekId"))
    @OrderBy("volgnummer")
    private Set<Verrichting> verrichtingen;
    @Version
    private long versie;

    public Kasboek(long afdelingId, int jaar, int maand, BigDecimal beginSaldo
    ) {
        this.afdelingId = afdelingId;
        this.jaar = jaar;
        this.maand = maand;
        this.beginSaldo = beginSaldo;
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

    public BigDecimal getTotaalGewichtMunten2E() {
        return totaalGewichtMunten2E;
    }

    public BigDecimal getTotaalGewichtMunten1E() {
        return totaalGewichtMunten1E;
    }

    public BigDecimal getTotaalGewichtMunten50cE() {
        return totaalGewichtMunten50cE;
    }

    public BigDecimal getTotaalGewichtMunten20cE() {
        return totaalGewichtMunten20cE;
    }

    public BigDecimal getTotaalGewichtMunten10cE() {
        return totaalGewichtMunten10cE;
    }

    public BigDecimal getTotaalGewichtMuntenBruinE() {
        return totaalGewichtMuntenBruinE;
    }

    public Set<Verrichting> getVerrichtingen() {
        return Collections.unmodifiableSet(verrichtingen);
    }

    public long getVersie() {

        return versie;
    }

    public CashMetGewichten getCash() {
        return new CashMetGewichten(this);
    }

    public BigDecimal getBeginSaldo() {
        return beginSaldo;
    }

    public CashInEuro getCashInEuro() {
        BigDecimal totaalBedragMunten2E = totaalGewichtMunten2E.divide(BigDecimal.valueOf(4.32), 2, RoundingMode.HALF_UP);
        BigDecimal totaalBedragMunten1E = totaalGewichtMunten1E.divide(BigDecimal.valueOf(7.62), 2, RoundingMode.HALF_UP);
        BigDecimal totaalBedragMunten50cE = totaalGewichtMunten50cE.divide(BigDecimal.valueOf(15.8), 2, RoundingMode.HALF_UP);
        BigDecimal totaalBedragMunten20cE = totaalGewichtMunten20cE.divide(BigDecimal.valueOf(29), 2, RoundingMode.HALF_UP);
        BigDecimal totaalBedragMunten10cE = totaalGewichtMunten10cE.divide(BigDecimal.valueOf(42), 2, RoundingMode.HALF_UP);
        BigDecimal totaalBedragMuntenBruinE = totaalGewichtMuntenBruinE.divide(BigDecimal.valueOf(84), 2, RoundingMode.HALF_UP);
        BigDecimal totaalBedragMunten = totaalBedragMunten2E
                .add(totaalBedragMunten1E)
                .add(totaalBedragMunten50cE)
                .add(totaalBedragMunten20cE)
                .add(totaalBedragMunten10cE)
                .add(totaalBedragMuntenBruinE).setScale(2, RoundingMode.HALF_UP);
        int totaalBedragMuntenAfgerond = totaalBedragMunten.setScale(0, RoundingMode.HALF_UP).intValue();
        BigDecimal totaalGewichtMunten = totaalGewichtMunten2E
                .add(totaalGewichtMunten1E)
                .add(totaalGewichtMunten50cE)
                .add(totaalGewichtMunten20cE)
                .add(totaalGewichtMunten10cE)
                .add(totaalGewichtMuntenBruinE);
        int totaalGewichtMuntenInKilo = totaalGewichtMunten.divide(BigDecimal.valueOf(1000), 0, RoundingMode.HALF_UP).intValue();
        return new CashInEuro(
            totaalBedragBiljetten,
            totaalBedragMunten2E,
            totaalBedragMunten1E,
            totaalBedragMunten50cE,
            totaalBedragMunten20cE,
            totaalBedragMunten10cE,
            totaalBedragMuntenBruinE,
            totaalBedragMunten,
            totaalBedragMuntenAfgerond,
            totaalGewichtMunten,
            totaalGewichtMuntenInKilo
        );
    }

    public BerekendeWaarden getBerekendeWaarden() {
        BigDecimal totIn = verrichtingen.stream().map(verrichting -> verrichting.getBedrag()).filter(bedrag -> bedrag.compareTo(BigDecimal.ZERO) > 0)
                .reduce(BigDecimal.ZERO, (vorigeSom, getal) -> vorigeSom.add(getal));
        BigDecimal totUit = verrichtingen.stream().map(verrichting -> verrichting.getBedrag()).filter(bedrag -> bedrag.compareTo(BigDecimal.ZERO) < 0)
                .reduce(BigDecimal.ZERO, (vorigeSom, getal) -> vorigeSom.add(getal));
        BigDecimal berekendSaldo = verrichtingen.stream().map(verrichting -> verrichting.getBedrag())
                .reduce(BigDecimal.ZERO, (vorigeSom, getal) -> vorigeSom.add(getal));
        BigDecimal verschil = beginSaldo.subtract(berekendSaldo);
        long totaalTicketten = verrichtingen.stream().filter(verrichting -> verrichting.getKasticket() == true).count();
        return new BerekendeWaarden(totIn, totUit, berekendSaldo, verschil, totaalTicketten);
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

    public void setBeginSaldo(BigDecimal saldo) {
        this.beginSaldo = saldo;
    }

    public void setCash(CashMetGewichten cashMetGewichten) {
        this.totaalBedragBiljetten = cashMetGewichten.totaalBedragBiljetten();
        this.totaalGewichtMunten2E = cashMetGewichten.totaalGewichtMunten2E();
        this.totaalGewichtMunten1E = cashMetGewichten.totaalGewichtMunten1E();
        this.totaalGewichtMunten50cE = cashMetGewichten.totaalGewichtMunten50cE();
        this.totaalGewichtMunten20cE = cashMetGewichten.totaalGewichtMunten20cE();
        this.totaalGewichtMunten10cE = cashMetGewichten.totaalGewichtMunten10cE();
        this.totaalGewichtMuntenBruinE = cashMetGewichten.totaalGewichtMuntenBruinE();
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