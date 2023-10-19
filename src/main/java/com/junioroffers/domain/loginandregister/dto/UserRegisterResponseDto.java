package com.junioroffers.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record UserRegisterResponseDto(
        String message) {
}
