package plaque.mHelathBackEnd.Service;

import plaque.mHelathBackEnd.Model.User;
import plaque.mHelathBackEnd.Repository.RoleRepository;
import plaque.mHelathBackEnd.Repository.UserRepository;
import plaque.mHelathBackEnd.Security.AuthenticatedUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Szymon on 2016-10-14.
 */
@Service("AuthenticatedAccountService")
public class AuthenticatedAccountService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Inject
    public AuthenticatedAccountService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("No user with email " + username);
        }
        else {
            List<String> userRoles = roleRepository.findRoleByUserEmail(username);
            return new AuthenticatedUser(user, userRoles);
        }
    }

}
