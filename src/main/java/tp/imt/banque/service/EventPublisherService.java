package tp.imt.banque.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import tp.imt.banque.event.ClientDeletedEvent;
import tp.imt.banque.event.OverdraftExceededEvent;
import tp.imt.banque.model.Conseiller;

@Service
public class EventPublisherService {

    private final ApplicationEventPublisher eventPublisher;

    public EventPublisherService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishOverdraftExceededEvent(String accountId, String advisorId) {
        OverdraftExceededEvent event = new OverdraftExceededEvent(this, accountId, advisorId);
        eventPublisher.publishEvent(event);
    }

    public void publishClientDeletedEvent(String clientId, Conseiller conseiller) {
        ClientDeletedEvent event = new ClientDeletedEvent(this, clientId, conseiller);
        eventPublisher.publishEvent(event);
        System.out.println("Published ClientDeletedEvent for clientId: " + clientId);
    }
}
