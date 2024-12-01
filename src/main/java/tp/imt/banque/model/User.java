package tp.imt.banque.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Client.class, name = "client"),
        @JsonSubTypes.Type(value = Conseiller.class, name = "advisor")
})
@Document(collection = "users")
@Data
public abstract class User {
    @Id
    private String id;

    @NotNull(message = "Le nom ne peut pas être nul.")
    private String name;

    @NotNull(message = "L'email ne peut pas être nul.")
    @Email(message = "L'email doit être valide.")
    private String email;
}
