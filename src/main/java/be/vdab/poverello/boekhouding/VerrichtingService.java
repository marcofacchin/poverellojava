package be.vdab.poverello.boekhouding;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class VerrichtingService {
    private final VerrichtingRepository verrichtingRepository;

    VerrichtingService(VerrichtingRepository verrichtingRepository) {
        this.verrichtingRepository = verrichtingRepository;
    }

    /*long findAantal() {
        return verrichtingRepository.count();
    }

    List<Verrichting> findAll() {
        return verrichtingRepository.findAll(Sort.by("datum"));
    }

    List<Verrichting> findAllMetOmschrijvingMetType() {
        return verrichtingRepository.findAllMetOmschrijvingEnType();
    }

    List<Verrichting> findByAfdelingId(int id) {
        return verrichtingRepository.findByAfdelingId(id);
    }

    List<Verrichting> findByJaar(int year) {
        return verrichtingRepository.findByJaar(year);
    }

    List<Verrichting> findByAfdelingIdEnJaarEnMaand(int id, int jaar, int maand) {
        return verrichtingRepository.findByAfdelingIdEnJaarEnMaand(id, jaar, maand);
    }

    Optional<Verrichting> findById(long id) {
        return verrichtingRepository.findById(id);
    }

    @Transactional
    long create(NieuweVerrichting nieuweVerrichting) {
        try {
            // NOG TE BEPALEN : MAAK verrichting -object op basis van nieuwe verrichting -record
            var verrichting = new Verrichting();
            verrichtingRepository.save(verrichting);
            return verrichting.getId();
        } catch (DataIntegrityViolationException ex) {
            throw new VerrichtingBestaatAlException();
        }
    }

    @Transactional
    void delete(long id) {
        verrichtingRepository.deleteById(id);
    }*/

}
