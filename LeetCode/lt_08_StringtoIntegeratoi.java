
public class lt_08_StringtoIntegeratoi {
    /**
     * 方法一：状态机方法（推荐）
     * 时间复杂度: O(n)
     * 空间复杂度: O(1)
     * 严格按照题目要求的4个步骤实现
     */
    public int myAtoi(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int index = 0;
        int n = s.length();
        int result = 0;
        int sign = 1;

        // 步骤1: 跳过前导空格
        while (index < n && s.charAt(index) == ' ') {
            index++;
        }

        // 如果字符串全是空格
        if (index == n) {
            return 0;
        }

        // 步骤2: 处理符号
        if (s.charAt(index) == '+') {
            sign = 1;
            index++;
        } else if (s.charAt(index) == '-') {
            sign = -1;
            index++;
        }

        // 步骤3: 转换数字
        while (index < n && Character.isDigit(s.charAt(index))) {
            int digit = s.charAt(index) - '0';

            // 步骤4: 溢出检测和处理
            // 检查正溢出
            if (result > Integer.MAX_VALUE / 10 ||
                    (result == Integer.MAX_VALUE / 10 && digit > Integer.MAX_VALUE % 10)) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }

            result = result * 10 + digit;
            index++;
        }

        return result * sign;
    }

    /**
     * 方法二：优化的状态机（处理边界情况更精确）
     */
    public int myAtoiOptimized(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int index = 0;
        int n = s.length();

        // 跳过前导空格
        while (index < n && s.charAt(index) == ' ') {
            index++;
        }

        if (index == n)
            return 0;

        // 处理符号
        int sign = 1;
        if (s.charAt(index) == '+' || s.charAt(index) == '-') {
            sign = s.charAt(index) == '+' ? 1 : -1;
            index++;
        }

        long result = 0; // 使用long暂存以简化溢出判断

        // 转换数字
        while (index < n && Character.isDigit(s.charAt(index))) {
            result = result * 10 + (s.charAt(index) - '0');

            // 早期溢出检测
            if (sign == 1 && result > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
            if (sign == -1 && -result < Integer.MIN_VALUE) {
                return Integer.MIN_VALUE;
            }

            index++;
        }

        return (int) (result * sign);
    }

    /**
     * 方法三：严格不使用long的实现
     * 完全符合题目"不允许64位整数"的要求
     */
    public int myAtoiStrict(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int index = 0;
        int n = s.length();
        int result = 0;
        int sign = 1;

        // 跳过前导空格
        while (index < n && s.charAt(index) == ' ') {
            index++;
        }

        if (index == n)
            return 0;

        // 处理符号
        if (s.charAt(index) == '+') {
            index++;
        } else if (s.charAt(index) == '-') {
            sign = -1;
            index++;
        }

        // 转换数字
        while (index < n && Character.isDigit(s.charAt(index))) {
            int digit = s.charAt(index) - '0';

            // 精确的溢出检测
            if (sign == 1) {
                if (result > (Integer.MAX_VALUE - digit) / 10) {
                    return Integer.MAX_VALUE;
                }
            } else {
                if (result > (Integer.MIN_VALUE + digit) / -10) {
                    return Integer.MIN_VALUE;
                }
            }

            result = result * 10 + digit;
            index++;
        }

        return result * sign;
    }

    /**
     * 辅助方法：详细展示atoi转换过程
     */
    public void demonstrateAtoi(String s) {
        System.out.printf("ATOI转换演示 - 输入: \"%s\"%n", s);

        if (s == null || s.length() == 0) {
            System.out.println("空字符串，返回 0");
            return;
        }

        int index = 0;
        int n = s.length();

        // 步骤1: 跳过空格
        System.out.print("步骤1 - 跳过前导空格: ");
        while (index < n && s.charAt(index) == ' ') {
            index++;
        }
        System.out.printf("跳过 %d 个空格，当前位置: %d%n", index, index);

        if (index == n) {
            System.out.println("全是空格，返回 0");
            return;
        }

        // 步骤2: 处理符号
        int sign = 1;
        System.out.print("步骤2 - 处理符号: ");
        if (s.charAt(index) == '+') {
            System.out.println("发现 '+'，符号为正");
            index++;
        } else if (s.charAt(index) == '-') {
            sign = -1;
            System.out.println("发现 '-'，符号为负");
            index++;
        } else {
            System.out.println("无符号，默认为正");
        }

        // 步骤3&4: 转换并检测溢出
        System.out.println("步骤3&4 - 转换数字并检测溢出:");
        int result = 0;

        while (index < n && Character.isDigit(s.charAt(index))) {
            int digit = s.charAt(index) - '0';
            System.out.printf("  读取数字: %d, 当前结果: %d", digit, result);

            // 溢出检测
            if (result > Integer.MAX_VALUE / 10 ||
                    (result == Integer.MAX_VALUE / 10 && digit > 7)) {
                int overflow = sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                System.out.printf(" -> 溢出！返回: %d%n", overflow);
                return;
            }

            result = result * 10 + digit;
            System.out.printf(" -> 更新结果: %d%n", result);
            index++;
        }

        int finalResult = result * sign;
        System.out.printf("最终结果: %d%n%n", finalResult);
    }

    // 测试方法
    public static void main(String[] args) {
        lt_08_StringtoIntegeratoi solution = new lt_08_StringtoIntegeratoi();

        // 测试用例
        String[] testCases = {
                "42", // 期望输出: 42
                "   -042", // 期望输出: -42
                "1337c0d3", // 期望输出: 1337
                "0-1", // 期望输出: 0
                "words and 987", // 期望输出: 0
                "", // 期望输出: 0
                "   ", // 期望输出: 0
                "+1", // 期望输出: 1
                "+-12", // 期望输出: 0
                "00000-42a1234", // 期望输出: 0
                "2147483646", // 期望输出: 2147483646
                "2147483648", // 期望输出: 2147483647 (溢出)
                "-2147483649", // 期望输出: -2147483648 (溢出)
                "  0000000000012345678", // 期望输出: 12345678
                "-91283472332" // 期望输出: -2147483648 (溢出)
        };

        System.out.println("=== 状态机方法（推荐）===");
        for (String testCase : testCases) {
            int result = solution.myAtoi(testCase);
            System.out.printf("输入: \"%s\" -> 输出: %d%n", testCase, result);
        }

        System.out.println("\n=== 优化方法验证 ===");
        for (String testCase : testCases) {
            int result = solution.myAtoiOptimized(testCase);
            System.out.printf("输入: \"%s\" -> 输出: %d%n", testCase, result);
        }

        System.out.println("\n=== 严格方法验证 ===");
        for (String testCase : testCases) {
            int result = solution.myAtoiStrict(testCase);
            System.out.printf("输入: \"%s\" -> 输出: %d%n", testCase, result);
        }

        // 详细演示关键案例
        System.out.println("\n=== 转换过程演示 ===");
        solution.demonstrateAtoi("   -042");
        solution.demonstrateAtoi("1337c0d3");
        solution.demonstrateAtoi("2147483648");
        solution.demonstrateAtoi("words and 987");
    }
}