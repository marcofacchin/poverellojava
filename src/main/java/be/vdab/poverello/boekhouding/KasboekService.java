package be.vdab.poverello.boekhouding;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class KasboekService {
    private final KasboekRepository kasboekRepository;

    public KasboekService(KasboekRepository kasboekRepository) {
        this.kasboekRepository = kasboekRepository;
    }

    long findAantal() {
        return kasboekRepository.count();
    }

    Optional<Kasboek> findKasboekByIdMetDetails(long id) {
        return kasboekRepository.findKasboekByIdMetDetails(id);
    }

    Optional<Kasboek> findKasboekByAfdelingIdJaarMaandMetDetails(long afdelingId, int jaar, int maand) {
        Afdeling afdeling = kasboekRepository.findAfdelingByAfdelingId(afdelingId)
                .orElseThrow(() -> new KasboekNietGevondenException());
        return kasboekRepository.findKasboekByAfdelingJaarMaandMetDetails(afdeling, jaar, maand);
    }
}
