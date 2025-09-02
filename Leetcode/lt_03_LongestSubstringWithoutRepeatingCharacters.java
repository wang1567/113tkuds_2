
import java.util.*;

public class lt_03_LongestSubstringWithoutRepeatingCharacters {
    /**
     * 滑动窗口 + HashSet方法
     * 时间复杂度: O(n)
     * 空间复杂度: O(min(m,n)) 其中m是字符集大小
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        Set<Character> window = new HashSet<>();
        int left = 0, maxLength = 0;

        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);

            // 如果当前字符已存在，收缩左边界直到移除重复字符
            while (window.contains(currentChar)) {
                window.remove(s.charAt(left));
                left++;
            }

            // 添加当前字符到窗口
            window.add(currentChar);

            // 更新最大长度
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    /**
     * 优化版本：使用HashMap记录字符最后出现位置
     * 时间复杂度: O(n)
     * 空间复杂度: O(min(m,n))
     */
    public int lengthOfLongestSubstringOptimized(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        Map<Character, Integer> charIndexMap = new HashMap<>();
        int left = 0, maxLength = 0;

        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);

            // 如果字符已存在且在当前窗口内，移动左边界
            if (charIndexMap.containsKey(currentChar)) {
                left = Math.max(left, charIndexMap.get(currentChar) + 1);
            }

            // 更新字符的最新位置
            charIndexMap.put(currentChar, right);

            // 更新最大长度
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    // 测试方法
    public static void main(String[] args) {
        lt_03_LongestSubstringWithoutRepeatingCharacters solution = new lt_03_LongestSubstringWithoutRepeatingCharacters();

        // 测试用例
        String[] testCases = {
                "abcabcbb", // 期望输出: 3 ("abc")
                "bbbbb", // 期望输出: 1 ("b")
                "pwwkew", // 期望输出: 3 ("wke")
                "", // 期望输出: 0
                "au", // 期望输出: 2 ("au")
                "dvdf" // 期望输出: 3 ("vdf")
        };

        System.out.println("=== 滑动窗口 + HashSet方法 ===");
        for (String testCase : testCases) {
            int result = solution.lengthOfLongestSubstring(testCase);
            System.out.printf("输入: \"%s\" -> 输出: %d%n", testCase, result);
        }

        System.out.println("\n=== 优化版本 ===");
        for (String testCase : testCases) {
            int result = solution.lengthOfLongestSubstringOptimized(testCase);
            System.out.printf("输入: \"%s\" -> 输出: %d%n", testCase, result);
        }
    }
}