package be.poverello.boekhouding;

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

    List<Omschrijving> findAllByAfdelingIdAndAfdelingId1OrderByInhoud(long afdelingId) {
        return omschrijvingRepository.findAllByAfdelingIdAndAfdelingId1OrderByInhoud(afdelingId);
    }

    Optional<Omschrijving> findById(long id) {
        return omschrijvingRepository.findById(id);
    }

    Optional<Omschrijving> findByAfdelingIdAndInhoud(long afdelingId, String inhoud) {
        return omschrijvingRepository.findByAfdelingIdAndInhoud(afdelingId, inhoud);
    }

    void deleteById(long id) {
        omschrijvingRepository.deleteById(id);
    }

    long save(Omschrijving omschrijving) {
        omschrijvingRepository.save(omschrijving);
        return omschrijving.getId();
    }

    Boolean existsByAfdelingIdAndInhoud(long afdelingId, String inhoud) {
        return omschrijvingRepository.existsByAfdelingIdAndInhoud(1, inhoud)
                || omschrijvingRepository.existsByAfdelingIdAndInhoud(afdelingId, inhoud);
    }
}
