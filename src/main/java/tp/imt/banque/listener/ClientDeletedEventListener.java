package tp.imt.banque.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import tp.imt.banque.event.ClientDeletedEvent;
import tp.imt.banque.repository.ContractRepository;

@Component
public class ClientDeletedEventListener {

    private final ContractRepository contractRepository;

    public ClientDeletedEventListener(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @EventListener
    public void handleClientDeletedEvent(ClientDeletedEvent event) {
        String clientId = event.getClientId();

        System.out.println("Processing ClientDeletedEvent for clientId: " + clientId);

        // Supprimer tous les contrats liés au client
        contractRepository.findByClientId(clientId).forEach(contractRepository::delete);
        System.out.println("All contracts for clientId " + clientId + " have been deleted.");
    }
}
