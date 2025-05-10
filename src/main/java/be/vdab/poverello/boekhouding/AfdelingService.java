package be.vdab.poverello.boekhouding;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AfdelingService {
    private final AfdelingRepository afdelingRepository;

    public AfdelingService(AfdelingRepository afdelingRepository) {
        this.afdelingRepository = afdelingRepository;
    }

    List<Afdeling> findAfdelingen(Taal taal) {
        return afdelingRepository.findAfdelingen(taal);
    }
}
