package uz.pdp.online.library.util;

import lombok.NonNull;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
    public static String encode(@NonNull String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public static boolean check(@NonNull String rawPassword, @NonNull String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }
}
