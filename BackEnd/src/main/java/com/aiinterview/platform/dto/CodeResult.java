package com.aiinterview.platform.dto;

public record CodeResult(
        String status,
        int passedTests,
        int totalTests,
        String message,
        String sampleOutput
) {}
