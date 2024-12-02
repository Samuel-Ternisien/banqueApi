package tp.imt.banque.model;

import jakarta.validation.Valid;
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
    private String conseillerId;
    private List<String> contractIds;
}
