package uz.pdp.online.library.util;

import jakarta.validation.*;
import lombok.Data;
import lombok.Getter;

import java.util.Set;

@Data
public class ValidatorUtils {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Getter
    private static final Validator validator = factory.getValidator();

    public static <T> void validate(T obj) throws ConstraintViolationException {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(obj);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Validatsiya xatolari:", violations);
        }
    }
}
