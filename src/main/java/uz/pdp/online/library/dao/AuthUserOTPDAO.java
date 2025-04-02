package uz.pdp.online.library.dao;

import lombok.NonNull;
import uz.pdp.online.library.entity.AuthUserOTP;

import java.util.List;

public class AuthUserOTPDAO extends BaseDAO<AuthUserOTP, String> {
    // ThreadLocal instance
    private static final ThreadLocal<AuthUserOTPDAO> authUserOTPDAOThreadLocal = ThreadLocal.withInitial(AuthUserOTPDAO::new);

    // Singleton access method
    public static AuthUserOTPDAO getInstance() {
        return authUserOTPDAOThreadLocal.get();
    }

    // Method to find AuthUserOTP by userID
    public AuthUserOTP findByUserID(@NonNull String userID) {
        String jpql = "from AuthUserOTP t where t.userID = ?1 and not t.deleted";
        List<AuthUserOTP> result = findByQuery(jpql, userID);

        if (result.isEmpty()) {
            return null; // If no result found, return null
        }

        return result.getFirst(); // Return the first result
    }
}
