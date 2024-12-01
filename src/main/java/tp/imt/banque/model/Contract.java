package tp.imt.banque.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "contracts")
@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CompteBancaire.class, name = "comptebancaire"),
        @JsonSubTypes.Type(value = AssuranceVie.class, name = "assurancevie"),
        @JsonSubTypes.Type(value = LivretA.class, name = "livreta")
})
public abstract class Contract {
    @Id
    private String id;
    private String clientId;
    private double amount;
}