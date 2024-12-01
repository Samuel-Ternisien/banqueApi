package tp.imt.banque.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssuranceVie extends Contract {
    private double montantAction; // Montant en actions
    private double montantEuros;  // Montant en euros
}
