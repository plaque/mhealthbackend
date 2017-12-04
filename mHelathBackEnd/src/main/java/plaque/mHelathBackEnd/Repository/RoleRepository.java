package plaque.mHelathBackEnd.Repository;

import plaque.mHelathBackEnd.Model.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Szymon on 2016-10-14.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    @Query("SELECT a.role " +
            "FROM Role a, User b " +
            "WHERE b.username=?1 AND a.userId=b.userId")
    public List<String> findRoleByUserName(String userName);

    @Query("SELECT a.role " +
            "FROM Role a, User b " +
            "WHERE b.email=?1 AND a.userId=b.userId")
    public List<String> findRoleByUserEmail(String email);

}
