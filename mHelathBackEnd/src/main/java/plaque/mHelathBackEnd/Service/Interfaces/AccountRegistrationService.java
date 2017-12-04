package plaque.mHelathBackEnd.Service.Interfaces;

import plaque.mHelathBackEnd.Security.VerificationToken;

/**
 * Created by Szymon on 2016-10-20.
 */
public interface AccountRegistrationService<T> {
        T registerNewAccount(T account);
        T getAccount(String verificationToken);
        void hashAccountPassword(T account);
        void saveRegisteredAccount(T account);
        void createVerificationToken(T account, String token);
        VerificationToken getVerificationToken(String verificationToken);
}
