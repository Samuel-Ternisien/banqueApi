package tp.imt.banque.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tp.imt.banque.model.Client;

import java.util.List;

public interface ClientRepository extends MongoRepository<Client, String> {
    List<Client> findByAdvisorId(String advisorId); // Trouve tous les clients d'un conseiller
}
