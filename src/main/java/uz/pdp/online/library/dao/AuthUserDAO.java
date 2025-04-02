package uz.pdp.online.library.dao;

import lombok.NonNull;
import uz.pdp.online.library.entity.AuthUser;

import java.util.List;
import java.util.Optional;

public class AuthUserDAO extends BaseDAO<AuthUser, String> {
    public Optional<AuthUser> findByEmail(@NonNull String email) {
        String query = "SELECT u FROM AuthUser u WHERE u.email = ?1";
        List<AuthUser> users = findByQuery(query, email);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.getFirst());
    }
}
