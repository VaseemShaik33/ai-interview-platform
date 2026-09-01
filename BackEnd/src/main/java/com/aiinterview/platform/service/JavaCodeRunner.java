package com.aiinterview.platform.service;

import com.aiinterview.platform.dto.CodeResult;
import com.aiinterview.platform.entity.CodingProblem;
import com.aiinterview.platform.entity.CodingTestCase;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class JavaCodeRunner {

    /*
     * DEVELOPMENT/MVP RUNNER:
     * User code is compiled and executed as a local Java process.
     * This is suitable for a local demo only.
     * Before public deployment, replace this with an isolated sandbox/container.
     */
    public CodeResult run(CodingProblem problem, String userCode, boolean hiddenTests) {
        if (userCode == null || userCode.isBlank()) {
            return new CodeResult("COMPILATION_ERROR", 0, problem.getTestCases().size(),
                    "Code cannot be empty.", "");
        }

        List<CodingTestCase> cases = problem.getTestCases().stream()
                .filter(tc -> hiddenTests || !tc.isHidden())
                .toList();

        if (cases.isEmpty()) {
            return new CodeResult("WRONG_ANSWER", 0, 0, "No test cases configured.", "");
        }

        Path workDir = null;
        try {
            workDir = Files.createTempDirectory("ai-interview-code-");
            String source = normalizeSource(userCode);
            Path sourceFile = workDir.resolve("Solution.java");
            Files.writeString(sourceFile, source, StandardCharsets.UTF_8);

            Process compile = new ProcessBuilder(
                    "javac", "-encoding", "UTF-8", "Solution.java")
                    .directory(workDir.toFile())
                    .redirectErrorStream(true)
                    .start();

            if (!compile.waitFor(8, TimeUnit.SECONDS)) {
                compile.destroyForcibly();
                return new CodeResult("COMPILATION_ERROR", 0, cases.size(),
                        "Compilation timed out.", "");
            }

            String compileOutput = readAll(compile.getInputStream());
            if (compile.exitValue() != 0) {
                return new CodeResult("COMPILATION_ERROR", 0, cases.size(),
                        compileOutput, "");
            }

            int passed = 0;
            String firstOutput = "";

            for (CodingTestCase tc : cases) {
                Process process = new ProcessBuilder(
                        "java", "-Xmx128m", "-Xss256k", "Solution")
                        .directory(workDir.toFile())
                        .redirectErrorStream(true)
                        .start();

                try (OutputStream os = process.getOutputStream()) {
                    os.write(tc.getInput().getBytes(StandardCharsets.UTF_8));
                }

                if (!process.waitFor(3, TimeUnit.SECONDS)) {
                    process.destroyForcibly();
                    return new CodeResult("TIME_LIMIT_EXCEEDED", passed, cases.size(),
                            "Time limit exceeded.", firstOutput);
                }

                String output = readAll(process.getInputStream()).trim();
                if (firstOutput.isEmpty())
                    firstOutput = output;

                if (process.exitValue() != 0) {
                    return new CodeResult("RUNTIME_ERROR", passed, cases.size(),
                            output, firstOutput);
                }

                if (normalize(output).equals(normalize(tc.getExpectedOutput()))) {
                    passed++;
                } else {
                    return new CodeResult("WRONG_ANSWER", passed, cases.size(),
                            "Expected: " + tc.getExpectedOutput() + "\nGot: " + output,
                            firstOutput);
                }
            }

            return new CodeResult("ACCEPTED", passed, cases.size(),
                    "All test cases passed.", firstOutput);

        } catch (Exception e) {
            return new CodeResult("RUNTIME_ERROR", 0, cases.size(),
                    e.getMessage() == null ? "Execution failed." : e.getMessage(), "");
        } finally {
            if (workDir != null)
                deleteRecursively(workDir);
        }
    }

    private String normalizeSource(String code) {
        String trimmed = code.trim();
        if (trimmed.contains("public class Solution"))
            return trimmed;
        if (trimmed.contains("class Solution"))
            return trimmed.replace("class Solution", "public class Solution");
        return """
                import java.util.*;
                public class Solution {
                    public static void main(String[] args) throws Exception {
                        %s
                    }
                }
                """.formatted(trimmed);
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim().replaceAll("\\s+", " ");
    }

    private String readAll(InputStream input) throws IOException {
        return new String(input.readAllBytes(), StandardCharsets.UTF_8);
    }

    private void deleteRecursively(Path root) {
        try {
            if (!Files.exists(root))
                return;
            Files.walk(root)
                    .sorted(Comparator.reverseOrder())
                    .forEach(p -> {
                        try {
                            Files.deleteIfExists(p);
                        } catch (IOException ignored) {
                        }
                    });
        } catch (IOException ignored) {
        }
    }
}
