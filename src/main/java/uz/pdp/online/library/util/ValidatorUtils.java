package uz.pdp.online.library.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.Data;
import lombok.Getter;

import java.util.Set;

@Data
public class ValidatorUtils {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Getter
    private static final Validator validator = factory.getValidator();

    public static <T> void validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validatsiya xatolari:\n");
            for (ConstraintViolation<T> violation : violations) {
                errorMessage.append("- ").append(violation.getMessage()).append("\n");
            }
            throw new IllegalArgumentException(errorMessage.toString());
        }
    }
}
