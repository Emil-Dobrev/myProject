package partner;

import enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.rmi.server.UID;
@AllArgsConstructor
@Data
public class Partner {

    private String fullName;
    private String email;
    private Status status;
}
