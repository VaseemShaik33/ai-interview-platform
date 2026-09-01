package com.aiinterview.platform.config;

import com.aiinterview.platform.entity.*;
import com.aiinterview.platform.repository.CodingProblemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CodingDataInitializer implements CommandLineRunner {
    private final CodingProblemRepository repository;

    public CodingDataInitializer(CodingProblemRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) return;

        CodingProblem p1 = new CodingProblem(
                "Two Sum",
                CodingDifficulty.EASY,
                "Given an array of integers nums and an integer target, print the indices of the two numbers that add up to target. Exactly one solution exists.",
                "2 <= nums.length <= 10^4",
                "Input: 2 7 11 15 and target 9\nOutput: 0 1",
                """
                import java.util.*;

                public class Solution {
                    public static void main(String[] args) {
                        Scanner sc = new Scanner(System.in);
                        int n = sc.nextInt();
                        int[] nums = new int[n];
                        for (int i = 0; i < n; i++) nums[i] = sc.nextInt();
                        int target = sc.nextInt();

                        // Write your solution here.
                        // Print the two indices separated by a space.
                    }
                }
                """
        );
        p1.addTestCase(new CodingTestCase("4\n2 7 11 15\n9\n", "0 1", false));
        p1.addTestCase(new CodingTestCase("3\n3 2 4\n6\n", "1 2", false));
        p1.addTestCase(new CodingTestCase("2\n3 3\n6\n", "0 1", true));

        CodingProblem p2 = new CodingProblem(
                "Valid Parentheses",
                CodingDifficulty.EASY,
                "Given a string containing only (), {}, and [], print true when the brackets are valid and false otherwise.",
                "1 <= s.length <= 10^4",
                "Input: ()[]{}\nOutput: true",
                """
                import java.util.*;

                public class Solution {
                    public static void main(String[] args) {
                        Scanner sc = new Scanner(System.in);
                        String s = sc.nextLine();

                        // Write your solution here.
                        // Print true or false.
                    }
                }
                """
        );
        p2.addTestCase(new CodingTestCase("()[]{}\n", "true", false));
        p2.addTestCase(new CodingTestCase("(]\n", "false", false));
        p2.addTestCase(new CodingTestCase("{[]}\n", "true", true));

        CodingProblem p3 = new CodingProblem(
                "Best Time to Buy and Sell Stock",
                CodingDifficulty.EASY,
                "Given an array of stock prices, print the maximum profit from one buy and one sell. If no profit is possible, print 0.",
                "1 <= prices.length <= 10^5",
                "Input: 7 1 5 3 6 4\nOutput: 5",
                """
                import java.util.*;

                public class Solution {
                    public static void main(String[] args) {
                        Scanner sc = new Scanner(System.in);
                        int n = sc.nextInt();
                        int[] prices = new int[n];
                        for (int i = 0; i < n; i++) prices[i] = sc.nextInt();

                        // Write your solution here.
                        // Print the maximum profit.
                    }
                }
                """
        );
        p3.addTestCase(new CodingTestCase("6\n7 1 5 3 6 4\n", "5", false));
        p3.addTestCase(new CodingTestCase("5\n7 6 4 3 1\n", "0", false));
        p3.addTestCase(new CodingTestCase("4\n2 4 1 7\n", "6", true));

        repository.save(p1);
        repository.save(p2);
        repository.save(p3);
    }
}
