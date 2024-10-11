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

    Optional<Omschrijving> findById(long id) {
        return omschrijvingRepository.findById(id);
    }

    List<Omschrijving> findAllByAfdelingId(long afdelingId) {
        return omschrijvingRepository.findAllByAfdelingIdOrderByInhoud(afdelingId);
    }

    long save(Omschrijving omschrijving) {
        omschrijvingRepository.save(omschrijving);
        return omschrijving.getId();
    }
}
