package plaque.mHelathBackEnd.Repository;

import plaque.mHelathBackEnd.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Szymon on 2016-10-14.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    public User findByUsername(String username);
    public User findByUserId(Long id);
    public User findByEmail(String email);
}
