package tp.imt.banque.event;

import org.springframework.context.ApplicationEvent;
import tp.imt.banque.model.Conseiller;

public class ClientDeletedEvent extends ApplicationEvent {

    private final String clientId;

    public ClientDeletedEvent(Object source, String clientId, Conseiller conseiller) {
        super(source);
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }
}
