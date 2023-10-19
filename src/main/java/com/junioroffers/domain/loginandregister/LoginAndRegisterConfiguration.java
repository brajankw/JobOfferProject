package com.junioroffers.domain.loginandregister;

public class LoginAndRegisterConfiguration {

    LoginAndRegisterFacade createForTest(UserRepository userRepository) {
        UserValidator userValidator = new UserValidator(userRepository);
        return new LoginAndRegisterFacade(userRepository, userValidator);
    }
}
