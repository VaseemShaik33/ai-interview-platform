package com.aiinterview.platform.dto;

public record RegisterResponse (
    String message,
    Long userId,
    String email
){
    
}
