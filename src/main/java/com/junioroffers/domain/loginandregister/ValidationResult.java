package com.junioroffers.domain.loginandregister;

enum ValidationResult {
    USERNAME_TAKEN("CHOOSE DIFFERENT USERNAME TO REGISTER"),
    PASSWORD_NOT_EQUAL_TO_CONFIRM_PASSWORD("CONFIRM PASSWORD MUST MATCH PASSWORD"),
    FIELDS_MUST_NOT_BE_NULL("AT LEAST ONE FIELD IS NULL"),
    FIELDS_MUST_NOT_BE_EMPTY("AT LEAST ONE FIELD IS EMPTY"),
    INPUT_SUCCESS("SUCCESS");

    final String info;

    ValidationResult(String info) {
        this.info = info;
    }
}
