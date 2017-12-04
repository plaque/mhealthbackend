package plaque.mHelathBackEnd.Service;

import plaque.mHelathBackEnd.Model.Role;
import plaque.mHelathBackEnd.Model.User;
import plaque.mHelathBackEnd.Repository.RoleRepository;
import plaque.mHelathBackEnd.Repository.UserRepository;
import plaque.mHelathBackEnd.Repository.VerificationTokenRepository;
import plaque.mHelathBackEnd.Security.VerificationToken;
import plaque.mHelathBackEnd.Service.Interfaces.AccountRegistrationService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by Szymon on 2016-10-20.
 */
@Service
public class UserRegistrationService implements AccountRegistrationService<User> {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private VerificationTokenRepository tokenRepository;

    @Inject
    public UserRegistrationService(UserRepository userRepository,
                                   RoleRepository roleRepository,
                                   VerificationTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public User registerNewAccount(User user) {

        hashAccountPassword(user);
        user.setEnabled(false);
        user = userRepository.save(user);
        roleRepository.save( new Role(user.getUserId(), "ROLE_USER") );

        return user;
    }

    @Override
    public User getAccount(String verificationToken) {
        User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }

    @Override
    public void hashAccountPassword(User account) {
        String password = account.getPassword();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        account.setPassword(hashedPassword);
    }

    @Override
    public void saveRegisteredAccount(User user) {
        userRepository.save(user);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String verificationToken) {
        return tokenRepository.findByToken(verificationToken);
    }
}
