package tp.imt.banque.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tp.imt.banque.model.LivretA;
import tp.imt.banque.repository.ContractRepository;

@Service
public class SchedulerService {

    private final ContractRepository contractRepository;

    public SchedulerService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void calculateLivretAInterest() {
        System.out.println("Calcul des intérêts pour LivretA...");
        var contrats = contractRepository.findAll();
        for (var contrat : contrats) {
            if (contrat instanceof LivretA livretA) {
                double interest = livretA.getTauxInteret() * livretA.getAmount();
                livretA.setAmount(livretA.getAmount() + interest);
                contractRepository.save(livretA);
                System.out.println("Intérêt ajouté pour LivretA ID : " + livretA.getId());
            }
        }
    }
}
