package com.junioroffers.domain.loginandregister;

import com.junioroffers.domain.loginandregister.dto.UserRegisterDto;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class UserValidator {

    private final UserRepository userRepository;
    List<ValidationResult> errors = new LinkedList<>();

    List<ValidationResult> validate(UserRegisterDto userRegisterDto) {
        if (isAnyFieldNull(userRegisterDto)) {
            errors.add(ValidationResult.FIELDS_MUST_NOT_BE_NULL);
            return errors;
        }
        if (isAnyFieldEmpty(userRegisterDto)) {
            errors.add(ValidationResult.FIELDS_MUST_NOT_BE_EMPTY);
            return errors;
        }
        if (isUsernameTaken(userRegisterDto)) {
            errors.add(ValidationResult.USERNAME_TAKEN);
        }
        if (!isPasswordMatchingConfirmPassword(userRegisterDto)) {
            errors.add(ValidationResult.PASSWORD_NOT_EQUAL_TO_CONFIRM_PASSWORD);
        }
        return errors;
    }

    String createResultMessage() {
        return this.errors
                .stream()
                .map(validationResult -> validationResult.info)
                .collect(Collectors.joining(","));
    }

    private boolean isUsernameTaken(UserRegisterDto userRegisterDto) {
        return userRepository.existByUsername(userRegisterDto.username());
    }

    private boolean isPasswordMatchingConfirmPassword(UserRegisterDto userRegisterDto) {
        return userRegisterDto.password().equals(userRegisterDto.confirmPassword());
    }

    private boolean isAnyFieldNull(UserRegisterDto userRegisterDto) {
        return userRegisterDto.username() == null
                || userRegisterDto.password() == null
                || userRegisterDto.confirmPassword() == null;
    }

    private boolean isAnyFieldEmpty(UserRegisterDto userRegisterDto) {
        return userRegisterDto.username().isEmpty()
                || userRegisterDto.password().isEmpty()
                || userRegisterDto.confirmPassword().isEmpty();
    }
}
