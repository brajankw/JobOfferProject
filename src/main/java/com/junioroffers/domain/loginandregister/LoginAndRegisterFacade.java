package com.junioroffers.domain.loginandregister;

import com.junioroffers.domain.loginandregister.dto.UserDto;
import com.junioroffers.domain.loginandregister.dto.UserRegisterDto;
import com.junioroffers.domain.loginandregister.dto.UserRegisterResponseDto;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class LoginAndRegisterFacade {

    private final UserRepository userRepository;
    private final UserValidator userValidator;

    public UserRegisterResponseDto registerUser(UserRegisterDto userRegisterDto) {
        List<ValidationResult> validationResultList = userValidator.validate(userRegisterDto);
        if (!validationResultList.isEmpty()) {
            String resultMessage = userValidator.createResultMessage();
            return new UserRegisterResponseDto(resultMessage, null, null);
        }

        User userToSave = User.builder()
                .username(userRegisterDto.username())
                .password(userRegisterDto.password())
                .build();

        User savedUser = userRepository.save(userToSave);

        return new UserRegisterResponseDto(ValidationResult.INPUT_SUCCESS.info, savedUser.id(), savedUser.username());
    }

    public UserDto findByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .map(user -> new UserDto(user.id(), user.username(), user.password()))
                .orElseThrow(() -> new UserNotFoundException(String.format("User with username: %s is not found", username)));
    }
}
