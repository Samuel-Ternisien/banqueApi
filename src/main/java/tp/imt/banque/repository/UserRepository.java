package tp.imt.banque.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import tp.imt.banque.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByEmail(String email); // Vérifie si un email existe déjà
}
