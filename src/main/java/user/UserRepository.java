package user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.rmi.server.UID;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}
