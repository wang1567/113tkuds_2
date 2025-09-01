package Leetcode;

public class lt_10_RegularExpressionMatching {
    /**
     * 方法一：动态规划（推荐）
     * 时间复杂度: O(m * n)
     * 空间复杂度: O(m * n)
     * dp[i][j] 表示 s[0..i-1] 和 p[0..j-1] 是否匹配
     */
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();

        // dp[i][j] 表示 s 的前 i 个字符和 p 的前 j 个字符是否匹配
        boolean[][] dp = new boolean[m + 1][n + 1];

        // 初始化：空字符串和空模式匹配
        dp[0][0] = true;

        // 处理空字符串和 "a*b*c*" 这种模式的情况
        for (int j = 2; j <= n; j += 2) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        // 填充dp表
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char sChar = s.charAt(i - 1);
                char pChar = p.charAt(j - 1);

                if (pChar == '*') {
                    // 当前模式字符是 '*'
                    char prevChar = p.charAt(j - 2); // '*' 前面的字符

                    // 情况1：'*' 匹配0次，忽略 "字符*" 模式
                    dp[i][j] = dp[i][j - 2];

                    // 情况2：'*' 匹配1次或多次
                    if (matches(sChar, prevChar)) {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                } else {
                    // 当前模式字符不是 '*'
                    if (matches(sChar, pChar)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                }
            }
        }

        return dp[m][n];
    }

    /**
     * 辅助方法：检查字符是否匹配
     */
    private boolean matches(char sChar, char pChar) {
        return pChar == '.' || sChar == pChar;
    }

    /**
     * 方法二：递归 + 记忆化（自顶向下）
     * 时间复杂度: O(m * n)
     * 空间复杂度: O(m * n)
     */
    public boolean isMatchRecursive(String s, String p) {
        Boolean[][] memo = new Boolean[s.length() + 1][p.length() + 1];
        return helper(s, p, 0, 0, memo);
    }

    private boolean helper(String s, String p, int i, int j, Boolean[][] memo) {
        // 记忆化检查
        if (memo[i][j] != null) {
            return memo[i][j];
        }

        boolean result;

        // 模式串结束
        if (j == p.length()) {
            result = (i == s.length());
        } else {
            // 检查当前字符是否匹配
            boolean firstMatch = (i < s.length() &&
                    (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.'));

            // 检查下一个字符是否是 '*'
            if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
                // 有 '*'：两种选择
                // 1. '*' 匹配0次：跳过 "字符*"
                // 2. '*' 匹配1+次：当前字符匹配且继续匹配
                result = helper(s, p, i, j + 2, memo) ||
                        (firstMatch && helper(s, p, i + 1, j, memo));
            } else {
                // 没有 '*'：当前字符必须匹配，继续下一个
                result = firstMatch && helper(s, p, i + 1, j + 1, memo);
            }
        }

        memo[i][j] = result;
        return result;
    }

    /**
     * 方法三：递归方法（无记忆化，便于理解）
     * 时间复杂度: O(2^(m+n)) - 可能超时
     * 空间复杂度: O(m + n)
     */
    public boolean isMatchSimpleRecursive(String s, String p) {
        return matchHelper(s, p, 0, 0);
    }

    private boolean matchHelper(String s, String p, int i, int j) {
        // 模式串结束
        if (j == p.length()) {
            return i == s.length();
        }

        // 检查当前字符是否匹配
        boolean firstMatch = (i < s.length() &&
                (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.'));

        // 检查是否有 '*'
        if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
            // 两种选择：
            // 1. '*' 匹配0次
            // 2. '*' 匹配1+次（需要当前字符匹配）
            return matchHelper(s, p, i, j + 2) ||
                    (firstMatch && matchHelper(s, p, i + 1, j));
        } else {
            // 没有 '*'，当前字符必须匹配
            return firstMatch && matchHelper(s, p, i + 1, j + 1);
        }
    }

    /**
     * 辅助方法：可视化展示匹配过程
     */
    public void demonstrateMatching(String s, String p) {
        System.out.printf("匹配演示 - 字符串: \"%s\", 模式: \"%s\"%n", s, p);

        boolean result = isMatch(s, p);
        System.out.printf("匹配结果: %s%n", result);

        // 显示DP表
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];

        dp[0][0] = true;

        // 初始化第一行
        for (int j = 2; j <= n; j += 2) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        // 填充DP表
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char sChar = s.charAt(i - 1);
                char pChar = p.charAt(j - 1);

                if (pChar == '*') {
                    char prevChar = p.charAt(j - 2);
                    dp[i][j] = dp[i][j - 2];
                    if (matches(sChar, prevChar)) {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                } else {
                    if (matches(sChar, pChar)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                }
            }
        }

        // 打印DP表
        System.out.println("DP表:");
        System.out.print("    ");
        for (int j = 0; j <= n; j++) {
            if (j == 0)
                System.out.print("ε ");
            else
                System.out.printf("%c ", p.charAt(j - 1));
        }
        System.out.println();

        for (int i = 0; i <= m; i++) {
            if (i == 0)
                System.out.print("ε ");
            else
                System.out.printf("%c ", s.charAt(i - 1));

            for (int j = 0; j <= n; j++) {
                System.out.printf("%s ", dp[i][j] ? "T" : "F");
            }
            System.out.println();
        }
        System.out.println();
    }

    // 测试方法
    public static void main(String[] args) {
        lt_10_RegularExpressionMatching solution = new lt_10_RegularExpressionMatching();

        // 测试用例
        String[][] testCases = {
                { "aa", "a" }, // false - 不完全匹配
                { "aa", "a*" }, // true - a*匹配两个a
                { "ab", ".*" }, // true - .*匹配任意字符串
                { "aab", "c*a*b" }, // true - c*匹配0个c，a*匹配2个a
                { "mississippi", "mis*is*p*." }, // false - 复杂模式
                { "", "" }, // true - 空字符串匹配
                { "", "a*" }, // true - a*可以匹配0个a
                { "a", "" }, // false - 非空字符串不匹配空模式
                { "ab", "a*b" }, // true - a*匹配1个a
                { "aaa", "a*a" }, // true - a*匹配2个a
                { "aaa", "ab*a*c*a" }, // true - 复杂匹配
                { "a", "ab*" }, // true - b*匹配0个b
                { "bbbba", ".*a*a" }, // true - .*匹配bbbb，a*匹配0个a，a匹配最后的a
                { "ab", ".*c" }, // false - 必须以c结尾
                { "aaca", "ab*a*c*a" } // true - 复杂嵌套匹配
        };

        System.out.println("=== 动态规划方法（推荐）===");
        for (String[] testCase : testCases) {
            boolean result = solution.isMatch(testCase[0], testCase[1]);
            System.out.printf("s=\"%s\", p=\"%s\" -> %s%n",
                    testCase[0], testCase[1], result);
        }

        System.out.println("\n=== 递归+记忆化验证 ===");
        for (String[] testCase : testCases) {
            boolean result = solution.isMatchRecursive(testCase[0], testCase[1]);
            System.out.printf("s=\"%s\", p=\"%s\" -> %s%n",
                    testCase[0], testCase[1], result);
        }

        // 详细演示几个关键案例
        System.out.println("\n=== 匹配过程详细演示 ===");
        solution.demonstrateMatching("aab", "c*a*b");
        solution.demonstrateMatching("aa", "a*");
        solution.demonstrateMatching("ab", ".*");
        solution.demonstrateMatching("mississippi", "mis*is*p*.");

        // 性能对比
        System.out.println("=== 性能对比 ===");
        String testS = "aaaaaaaaaaaaaab";
        String testP = "a*a*a*a*a*a*a*a*a*a*c";

        long start = System.nanoTime();
        boolean result1 = solution.isMatch(testS, testP);
        long time1 = System.nanoTime() - start;

        start = System.nanoTime();
        boolean result2 = solution.isMatchRecursive(testS, testP);
        long time2 = System.nanoTime() - start;

        System.out.printf("DP方法: %s, 耗时: %d ns%n", result1, time1);
        System.out.printf("递归+记忆化: %s, 耗时: %d ns%n", result2, time2);

        // 边界情况测试
        System.out.println("\n=== 边界情况测试 ===");
        System.out.printf("空字符串匹配空模式: %s%n", solution.isMatch("", ""));
        System.out.printf("空字符串匹配a*: %s%n", solution.isMatch("", "a*"));
        System.out.printf("单字符匹配.: %s%n", solution.isMatch("a", "."));
        System.out.printf("单字符匹配.*: %s%n", solution.isMatch("a", ".*"));
    }
}