
public class lt_05_LongestPalindromicSubstring {
    /**
     * 方法一：中心扩散法（推荐）
     * 时间复杂度: O(n²)
     * 空间复杂度: O(1)
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }

        int start = 0, maxLength = 1;

        for (int i = 0; i < s.length(); i++) {
            // 检查奇数长度的回文（以i为中心）
            int len1 = expandAroundCenter(s, i, i);
            // 检查偶数长度的回文（以i和i+1为中心）
            int len2 = expandAroundCenter(s, i, i + 1);

            int currentMax = Math.max(len1, len2);
            if (currentMax > maxLength) {
                maxLength = currentMax;
                // 计算回文串的起始位置
                start = i - (currentMax - 1) / 2;
            }
        }

        return s.substring(start, start + maxLength);
    }

    /**
     * 从中心向外扩散，找到以left和right为中心的最长回文长度
     */
    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    /**
     * 方法二：动态规划
     * 时间复杂度: O(n²)
     * 空间复杂度: O(n²)
     */
    public String longestPalindromeDP(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }

        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int start = 0, maxLength = 1;

        // 单个字符都是回文
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        // 检查长度为2的子串
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                start = i;
                maxLength = 2;
            }
        }

        // 检查长度大于2的子串
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;

                if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                    dp[i][j] = true;
                    if (len > maxLength) {
                        start = i;
                        maxLength = len;
                    }
                }
            }
        }

        return s.substring(start, start + maxLength);
    }

    /**
     * 方法三：Manacher算法（最优解）
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     */
    public String longestPalindromeManacher(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }

        // 预处理字符串，插入特殊字符
        String processed = preprocess(s);
        int n = processed.length();
        int[] radius = new int[n]; // 存储每个位置的回文半径
        int center = 0, right = 0; // 当前回文的中心和右边界
        int maxLen = 0, maxCenter = 0;

        for (int i = 0; i < n; i++) {
            // 利用回文的对称性
            int mirror = 2 * center - i;

            if (i < right) {
                radius[i] = Math.min(right - i, radius[mirror]);
            }

            // 尝试扩展回文
            try {
                while (i + radius[i] + 1 < n && i - radius[i] - 1 >= 0 &&
                        processed.charAt(i + radius[i] + 1) == processed.charAt(i - radius[i] - 1)) {
                    radius[i]++;
                }
            } catch (Exception e) {
                // 边界处理
            }

            // 更新中心和右边界
            if (i + radius[i] > right) {
                center = i;
                right = i + radius[i];
            }

            // 更新最长回文
            if (radius[i] > maxLen) {
                maxLen = radius[i];
                maxCenter = i;
            }
        }

        // 从处理后的字符串中提取原始回文串
        int start = (maxCenter - maxLen) / 2;
        return s.substring(start, start + maxLen);
    }

    /**
     * 预处理字符串，在每个字符间插入#
     * 例如: "babad" -> "#b#a#b#a#d#"
     */
    private String preprocess(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            sb.append('#').append(s.charAt(i));
        }
        sb.append('#');
        return sb.toString();
    }

    // 测试方法
    public static void main(String[] args) {
        lt_05_LongestPalindromicSubstring solution = new lt_05_LongestPalindromicSubstring();

        String[] testCases = {
                "babad", // 期望输出: "bab" 或 "aba"
                "cbbd", // 期望输出: "bb"
                "a", // 期望输出: "a"
                "ac", // 期望输出: "a" 或 "c"
                "racecar", // 期望输出: "racecar"
                "noon", // 期望输出: "noon"
                "abcdef", // 期望输出: "a"（任意单个字符）
                "" // 期望输出: ""
        };

        System.out.println("=== 中心扩散法（推荐）===");
        for (String testCase : testCases) {
            if (testCase.isEmpty())
                continue;
            String result = solution.longestPalindrome(testCase);
            System.out.printf("输入: \"%s\" -> 输出: \"%s\" (长度: %d)%n",
                    testCase, result, result.length());
        }

        System.out.println("\n=== 动态规划方法 ===");
        for (String testCase : testCases) {
            if (testCase.isEmpty())
                continue;
            String result = solution.longestPalindromeDP(testCase);
            System.out.printf("输入: \"%s\" -> 输出: \"%s\" (长度: %d)%n",
                    testCase, result, result.length());
        }

        System.out.println("\n=== Manacher算法（最优）===");
        for (String testCase : testCases) {
            if (testCase.isEmpty())
                continue;
            String result = solution.longestPalindromeManacher(testCase);
            System.out.printf("输入: \"%s\" -> 输出: \"%s\" (长度: %d)%n",
                    testCase, result, result.length());
        }

        // 性能测试
        System.out.println("\n=== 性能对比测试 ===");
        String longString = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";

        long start = System.nanoTime();
        solution.longestPalindrome(longString);
        long time1 = System.nanoTime() - start;

        start = System.nanoTime();
        solution.longestPalindromeDP(longString);
        long time2 = System.nanoTime() - start;

        start = System.nanoTime();
        solution.longestPalindromeManacher(longString);
        long time3 = System.nanoTime() - start;

        System.out.printf("中心扩散法耗时: %d ns%n", time1);
        System.out.printf("动态规划耗时: %d ns%n", time2);
        System.out.printf("Manacher算法耗时: %d ns%n", time3);
    }
}