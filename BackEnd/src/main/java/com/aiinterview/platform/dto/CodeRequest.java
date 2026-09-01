package com.aiinterview.platform.dto;

import jakarta.validation.constraints.NotBlank;

public record CodeRequest(
        @NotBlank String code,
        @NotBlank String language
) {}
