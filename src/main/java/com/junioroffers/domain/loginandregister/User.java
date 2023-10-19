package com.junioroffers.domain.loginandregister;

import lombok.Builder;

@Builder
record User(
        Long id,
        String username,
        String password) {
}
