package com.aiinterview.platform.dto;

public record ErrorResponse(
    String message,
    int status
) {
    
}
