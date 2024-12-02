package tp.imt.banque.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tp.imt.banque.model.Conseiller;

public interface ConseillerRepository extends MongoRepository<Conseiller, String> {
    Conseiller findByClientIdsContaining(String clientId); // Trouve le conseiller d’un client
}
