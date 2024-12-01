package tp.imt.banque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.imt.banque.exception.AdvisorNotFoundException;
import tp.imt.banque.exception.AdvisorNotSpecifiedException;
import tp.imt.banque.model.*;
import tp.imt.banque.repository.AdvisorRepository;
import tp.imt.banque.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventPublisherService eventPublisherService;

    @Autowired
    private AdvisorRepository advisorRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User createUser(User user) {

        user.setName(user.getName());
        user.setEmail(user.getEmail());

        if (user instanceof Client client) {
            if (client.getAdvisorId() == null) {
                throw new AdvisorNotSpecifiedException("A client must have a valid conseiller ID.");
            }

            // Vérifie que le conseiller existe
            Conseiller conseiller = (Conseiller) userRepository.findById(client.getAdvisorId())
                    .orElseThrow(() -> new AdvisorNotFoundException("Conseiller not found with ID: " + client.getAdvisorId()));

            // Sauvegarde initiale du client pour générer son ID
            client.setAdvisorId(conseiller.getId());
            user = userRepository.save(user);

            // Ajoute l'ID du client au conseiller
            if (conseiller.getClientIds() == null) {
                conseiller.setClientIds(new ArrayList<>());
            }
            conseiller.getClientIds().add(user.getId());

            // Sauvegarde les modifications du conseiller
            userRepository.save(conseiller);
        }
        return user;
    }

    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (user instanceof Client client) {
            eventPublisherService.publishClientDeletedEvent(client.getId(), (Conseiller) userRepository.findById(client.getAdvisorId()).orElse(null));
        }

        userRepository.deleteById(id);
    }
}
