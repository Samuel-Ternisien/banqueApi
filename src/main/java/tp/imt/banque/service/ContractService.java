package tp.imt.banque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.imt.banque.model.*;
import tp.imt.banque.repository.ContractRepository;
import tp.imt.banque.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventPublisherService eventPublisherService;

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public Contract getContractById(String id) {
        return contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found with id: " + id));
    }

    public Contract createContract(Contract contract) {
        // Vérifie que le client existe
        User client = userRepository.findById(contract.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + contract.getClientId()));

        // Initialise les champs spécifiques au contrat
        contract.setClientId(contract.getClientId());
        contract.setAmount(contract.getAmount());

        if (contract instanceof CompteBancaire compteBancaire) {
            compteBancaire.setDecouvertAutorise(compteBancaire.getDecouvertAutorise());
        } else if (contract instanceof AssuranceVie assuranceVie) {
            assuranceVie.setMontantAction(assuranceVie.getMontantAction());
            assuranceVie.setMontantEuros(assuranceVie.getMontantEuros());
        } else if (contract instanceof LivretA livretA) {
            livretA.setTauxInteret(livretA.getTauxInteret());
        }

        // Sauvegarde le contrat pour générer son ID
        Contract savedContract = contractRepository.save(contract);

        // Ajoute l'ID du contrat à la liste `contractIds` du client
        if (client instanceof Client clientEntity) {
            if (clientEntity.getContractIds() == null) {
                clientEntity.setContractIds(new ArrayList<>());
            }
            clientEntity.getContractIds().add(savedContract.getId());
            userRepository.save(clientEntity); // Sauvegarde les modifications du client
        } else {
            throw new RuntimeException("The user associated with this contract is not a client.");
        }

        return savedContract;
    }


    public void deleteContract(String id) {
        if (!contractRepository.existsById(id)) {
            throw new RuntimeException("Contract not found with id: " + id);
        }
        contractRepository.deleteById(id);
    }


    public Contract updateContract(String id, Contract updatedContract) {
        // Vérifier si le contrat existe
        Contract existingContract = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found with id: " + id));

        // Mise à jour des champs communs
        existingContract.setAmount(updatedContract.getAmount());

        // Mise à jour spécifique selon le type de contrat
        if (existingContract instanceof CompteBancaire compteBancaire) {
            CompteBancaire updatedCompteBancaire = (CompteBancaire) updatedContract;
            System.out.println(updatedCompteBancaire);
            compteBancaire.setDecouvertAutorise(updatedCompteBancaire.getDecouvertAutorise());

            // Vérifier si le découvert autorisé est dépassé
            if (compteBancaire.getAmount() < -compteBancaire.getDecouvertAutorise()) {
                // Publier un événement pour signaler le dépassement

                Client client = (Client) userRepository.findById(compteBancaire.getClientId()).orElseThrow();
                eventPublisherService.publishOverdraftExceededEvent(
                        compteBancaire.getId(),
                        client.getAdvisorId()
                );
            }

        } else if (existingContract instanceof AssuranceVie assuranceVie) {
            AssuranceVie updatedAssuranceVie = (AssuranceVie) updatedContract;
            assuranceVie.setMontantAction(updatedAssuranceVie.getMontantAction());
            assuranceVie.setMontantEuros(updatedAssuranceVie.getMontantEuros());

        } else if (existingContract instanceof LivretA livretA) {
            LivretA updatedLivretA = (LivretA) updatedContract;
            livretA.setTauxInteret(updatedLivretA.getTauxInteret());
        }

        // Sauvegarder le contrat mis à jour
        return contractRepository.save(existingContract);
    }
}
