package tp.imt.banque.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivretA extends Contract {
    private double tauxInteret; // Taux d'intérêt
}
