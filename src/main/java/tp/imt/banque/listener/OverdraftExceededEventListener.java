package tp.imt.banque.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import tp.imt.banque.event.OverdraftExceededEvent;
import tp.imt.banque.model.Conseiller;
import tp.imt.banque.repository.UserRepository;

@Component
public class OverdraftExceededEventListener {

    private final UserRepository userRepository;

    public OverdraftExceededEventListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener
    public void handleOverdraftExceededEvent(OverdraftExceededEvent event) {
        String advisorId = event.getAdvisorId();
        String accountId = event.getAccountId();

        System.out.println("Processing OverdraftExceededEvent for account ID: " + accountId);

        userRepository.findById(advisorId).ifPresent(advisor -> {
            if (advisor instanceof Conseiller) {
                Conseiller adv = (Conseiller) advisor;
                adv.getNotifications().add("Overdraft exceeded for account ID: " + accountId);
                userRepository.save(adv);
            }
        });
    }
}
