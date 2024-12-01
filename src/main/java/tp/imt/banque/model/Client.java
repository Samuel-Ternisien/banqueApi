package tp.imt.banque.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client extends User {
    private String conseillersId;
    private List<String> contractIds;

    /**
     * Vérifie qu'il n'y a pas de doublons dans les types de contrats.
     *
     * @param contracts Liste des contrats associés au client
     * @throws IllegalArgumentException si un type est dupliqué
     */
    public void validateUniqueContractTypes(List<Contract> contracts) {
        Set<Class<? extends Contract>> types = new HashSet<>();
        for (Contract contract : contracts) {
            if (!types.add(contract.getClass())) {
                throw new IllegalArgumentException(
                        "Un client ne peut pas avoir plusieurs contrats du même type : " + contract.getClass().getSimpleName()
                );
            }
        }
    }
}
