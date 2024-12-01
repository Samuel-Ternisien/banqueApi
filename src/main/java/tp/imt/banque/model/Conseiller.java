package tp.imt.banque.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conseiller extends User {
    private List<String> clientIds = new ArrayList<>(); // Liste des IDs des clients associés
    private List<String> notifications = new ArrayList<>(); // Notifications pour les événements
}
