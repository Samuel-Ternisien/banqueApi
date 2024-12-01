package tp.imt.banque.event;

import org.springframework.context.ApplicationEvent;

public class OverdraftExceededEvent extends ApplicationEvent {

    private final String accountId;
    private final String advisorId;

    public OverdraftExceededEvent(Object source, String accountId, String advisorId) {
        super(source);
        this.accountId = accountId;
        this.advisorId = advisorId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAdvisorId() {
        return advisorId;
    }
}
