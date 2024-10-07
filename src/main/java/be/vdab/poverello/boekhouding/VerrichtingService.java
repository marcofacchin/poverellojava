package be.vdab.poverello.boekhouding;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class VerrichtingService {
    private final VerrichtingRepository verrichtingRepository;

    VerrichtingService(VerrichtingRepository verrichtingRepository) {
        this.verrichtingRepository = verrichtingRepository;
    }

    long findAantal() {
        return verrichtingRepository.count();
    }

    List<Verrichting> findAll() {
        return verrichtingRepository.findAll(Sort.by("datum"));
    }
}
