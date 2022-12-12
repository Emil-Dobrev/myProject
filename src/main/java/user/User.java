package user;

import enums.Provider;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import partner.Partner;

import java.rmi.server.UID;
import java.time.Instant;

@Data
@Document
@Builder
public class User {

    @Id
    private String id;
    @NonNull
    @Indexed(unique = true)
    private String email;
    private String password;
    private String name;
    private Provider provider;
    private Instant createdAt;
    private Partner partner;
}
