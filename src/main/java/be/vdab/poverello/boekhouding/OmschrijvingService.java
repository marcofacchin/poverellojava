package be.vdab.poverello.boekhouding;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OmschrijvingService {
    private final OmschrijvingRepository omschrijvingRepository;

    public OmschrijvingService(OmschrijvingRepository omschrijvingRepository) {
        this.omschrijvingRepository = omschrijvingRepository;
    }

    List<Omschrijving> findAllByAfdelingIdOrderByInhoud(long afdelingId) {
        return omschrijvingRepository.findAllByAfdelingIdOrderByInhoud(afdelingId);
    }

    Optional<Omschrijving> findByAfdelingIdAndId(long afdelingId, long id) {
        return omschrijvingRepository.findByAfdelingIdAndId(afdelingId, id);
    }

    void deleteByAfdelingIdAndId(long afdelingId, long id) {
        omschrijvingRepository.deleteByAfdelingIdAndId(afdelingId, id);
    }

    long save(Omschrijving omschrijving) {
        omschrijvingRepository.save(omschrijving);
        return omschrijving.getId();
    }
}
