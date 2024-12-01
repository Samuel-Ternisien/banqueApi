package tp.imt.banque.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tp.imt.banque.model.Contract;

import java.util.List;

public interface ContractRepository extends MongoRepository<Contract, String> {
    List<Contract> findByClientId(String clientId); // Trouve tous les contrats d’un client
}
