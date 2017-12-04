package plaque.mHelathBackEnd.Repository;

import org.springframework.stereotype.Repository;
import plaque.mHelathBackEnd.Model.User;
import plaque.mHelathBackEnd.Security.VerificationToken;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Szymon on 2016-10-25.
 */
@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
    VerificationToken findByUser(User user);
}
