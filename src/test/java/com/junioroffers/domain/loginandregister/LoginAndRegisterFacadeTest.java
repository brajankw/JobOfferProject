package com.junioroffers.domain.loginandregister;

import com.junioroffers.domain.loginandregister.dto.UserDto;
import com.junioroffers.domain.loginandregister.dto.UserRegisterDto;
import com.junioroffers.domain.loginandregister.dto.UserRegisterResponseDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginAndRegisterFacadeTest {

    private final UserRepository userRepository = new InMemoryUserRepositoryTestImpl();
    private final String testUsername = "testUsername";
    private final String testPassword = "testPassword";

    @Test
    public void should_return_correct_response_when_user_provided_correct_credentials_to_register() {
        // given
        LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterConfiguration().createForTest(userRepository);

        UserRegisterDto userRegisterDto = UserRegisterDto.builder()
                .username(testUsername)
                .password(testPassword)
                .confirmPassword(testPassword)
                .build();
        // when
        UserRegisterResponseDto response = loginAndRegisterFacade.registerUser(userRegisterDto);

        // then
        UserRegisterResponseDto expectedResponse = new UserRegisterResponseDto(ValidationResult.INPUT_SUCCESS.info);
        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void should_return_failed_message_when_user_provided_existing_username_to_register() {
        // given
        User existingUser = User.builder()
                .username(testUsername)
                .password(testPassword)
                .build();
        userRepository.save(existingUser);
        LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterConfiguration().createForTest(userRepository);

        UserRegisterDto userRegisterDto = UserRegisterDto.builder()
                .username(testUsername)
                .password(testPassword)
                .confirmPassword(testPassword)
                .build();
        // when
        UserRegisterResponseDto response = loginAndRegisterFacade.registerUser(userRegisterDto);

        // then
        UserRegisterResponseDto expectedResponse = new UserRegisterResponseDto(ValidationResult.USERNAME_TAKEN.info);
        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void should_return_failed_message_when_user_provided_not_matching_passwords_to_register() {
        // given
        LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterConfiguration().createForTest(userRepository);

        UserRegisterDto userRegisterDto = UserRegisterDto.builder()
                .username(testUsername)
                .password(testPassword)
                .confirmPassword("invalidPassword")
                .build();
        // when
        UserRegisterResponseDto response = loginAndRegisterFacade.registerUser(userRegisterDto);

        // then
        UserRegisterResponseDto expectedResponse = new UserRegisterResponseDto(ValidationResult.PASSWORD_NOT_EQUAL_TO_CONFIRM_PASSWORD.info);
        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void should_return_failed_message_when_user_provided_null_in_any_field_to_register() {
        // given
        LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterConfiguration().createForTest(userRepository);

        UserRegisterDto userRegisterDto = UserRegisterDto.builder()
                .username(null)
                .password(testPassword)
                .confirmPassword(testPassword)
                .build();
        // when
        UserRegisterResponseDto response = loginAndRegisterFacade.registerUser(userRegisterDto);

        // then
        UserRegisterResponseDto expectedResponse = new UserRegisterResponseDto(ValidationResult.FIELDS_MUST_NOT_BE_NULL.info);
        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void should_return_failed_message_when_user_provided_empty_string_in_any_field_to_register() {
        // given
        LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterConfiguration().createForTest(userRepository);

        UserRegisterDto userRegisterDto = UserRegisterDto.builder()
                .username("")
                .password(testPassword)
                .confirmPassword(testPassword)
                .build();
        // when
        UserRegisterResponseDto response = loginAndRegisterFacade.registerUser(userRegisterDto);

        // then
        UserRegisterResponseDto expectedResponse = new UserRegisterResponseDto(ValidationResult.FIELDS_MUST_NOT_BE_EMPTY.info);
        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void should_find_user_by_username() {
        //given
        Long id = 1L;
        LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterConfiguration().createForTest(userRepository);
        User toSave = User.builder()
                .id(id)
                .username(testUsername)
                .password(testPassword)
                .build();
        userRepository.save(toSave);
        UserDto expectedUser = new UserDto(id, testUsername, testPassword);
        //when
        UserDto user = loginAndRegisterFacade.findByUsername(testUsername);
        //then
        assertThat(expectedUser).isEqualTo(user);
    }

    @Test
    public void should_throw_exception_when_user_not_found() {
        //given
        String invalidUsername = "INVALID";
        LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterConfiguration().createForTest(userRepository);
        //when
        //then
        assertThrows(UserNotFoundException.class,
                () -> loginAndRegisterFacade.findByUsername(invalidUsername),
                String.format("User with username: %s is not found", invalidUsername));
    }
}