
public class lt_07_ReverseInteger {
    /**
     * 方法一：数学方法 + 溢出检测（推荐）
     * 时间复杂度: O(log x) - 数字的位数
     * 空间复杂度: O(1)
     */
    public int reverse(int x) {
        int result = 0;

        while (x != 0) {
            int digit = x % 10;
            x /= 10;

            // 溢出检测 - 在实际计算前检查是否会溢出
            // Integer.MAX_VALUE = 2147483647
            // Integer.MIN_VALUE = -2147483648
            if (result > Integer.MAX_VALUE / 10 ||
                    (result == Integer.MAX_VALUE / 10 && digit > 7)) {
                return 0; // 正溢出
            }
            if (result < Integer.MIN_VALUE / 10 ||
                    (result == Integer.MIN_VALUE / 10 && digit < -8)) {
                return 0; // 负溢出
            }

            result = result * 10 + digit;
        }

        return result;
    }

    /**
     * 方法二：字符串转换法
     * 时间复杂度: O(log x)
     * 空间复杂度: O(log x)
     * 思路：转换为字符串处理，但仍需处理溢出
     */
    public int reverseString(int x) {
        boolean isNegative = x < 0;
        String str = String.valueOf(Math.abs(x));

        // 反转字符串
        StringBuilder sb = new StringBuilder(str);
        String reversed = sb.reverse().toString();

        try {
            // 尝试转换为整数
            long result = Long.parseLong(reversed);
            if (isNegative) {
                result = -result;
            }

            // 检查是否在32位整数范围内
            if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
                return 0;
            }

            return (int) result;
        } catch (NumberFormatException e) {
            return 0; // 溢出时返回0
        }
    }

    /**
     * 方法三：不使用long的纯数学方法
     * 通过更精确的溢出预判避免使用64位整数
     */
    public int reverseNoLong(int x) {
        int result = 0;

        while (x != 0) {
            int digit = x % 10;
            x /= 10;

            // 更详细的溢出检测
            if (result > 0) {
                // 正数溢出检测
                if (result > (Integer.MAX_VALUE - digit) / 10) {
                    return 0;
                }
            } else {
                // 负数溢出检测
                if (result < (Integer.MIN_VALUE - digit) / 10) {
                    return 0;
                }
            }

            result = result * 10 + digit;
        }

        return result;
    }

    /**
     * 辅助方法：可视化展示反转过程
     */
    public void demonstrateReverse(int x) {
        System.out.printf("反转过程演示 - 输入: %d%n", x);

        int original = x;
        int result = 0;
        int step = 1;

        while (x != 0) {
            int digit = x % 10;
            x /= 10;

            System.out.printf("步骤 %d: 取出数字 %d, 剩余 %d, 当前结果 %d%n",
                    step++, digit, x, result);

            // 检查溢出
            if (result > Integer.MAX_VALUE / 10 ||
                    (result == Integer.MAX_VALUE / 10 && digit > 7)) {
                System.out.println("检测到正溢出！");
                return;
            }
            if (result < Integer.MIN_VALUE / 10 ||
                    (result == Integer.MIN_VALUE / 10 && digit < -8)) {
                System.out.println("检测到负溢出！");
                return;
            }

            result = result * 10 + digit;
            System.out.printf("        更新结果: %d%n", result);
        }

        System.out.printf("最终结果: %d -> %d%n%n", original, result);
    }

    // 测试方法
    public static void main(String[] args) {
        lt_07_ReverseInteger solution = new lt_07_ReverseInteger();

        // 测试用例
        int[] testCases = {
                123, // 期望输出: 321
                -123, // 期望输出: -321
                120, // 期望输出: 21
                0, // 期望输出: 0
                1534236469, // 期望输出: 0 (溢出)
                -2147483648, // 期望输出: 0 (溢出)
                1463847412, // 期望输出: 2147483641
                -1463847412 // 期望输出: -2147483641
        };

        System.out.println("=== 数学方法（推荐）===");
        for (int testCase : testCases) {
            int result = solution.reverse(testCase);
            System.out.printf("输入: %d -> 输出: %d%n", testCase, result);
        }

        System.out.println("\n=== 字符串方法验证 ===");
        for (int testCase : testCases) {
            int result = solution.reverseString(testCase);
            System.out.printf("输入: %d -> 输出: %d%n", testCase, result);
        }

        System.out.println("\n=== 纯数学方法验证 ===");
        for (int testCase : testCases) {
            int result = solution.reverseNoLong(testCase);
            System.out.printf("输入: %d -> 输出: %d%n", testCase, result);
        }

        // 详细演示几个关键案例
        System.out.println("\n=== 反转过程演示 ===");
        solution.demonstrateReverse(123);
        solution.demonstrateReverse(-123);
        solution.demonstrateReverse(1534236469); // 溢出案例

        // 边界值测试
        System.out.println("=== 边界值测试 ===");
        System.out.printf("Integer.MAX_VALUE = %d%n", Integer.MAX_VALUE);
        System.out.printf("Integer.MIN_VALUE = %d%n", Integer.MIN_VALUE);
        System.out.printf("反转 Integer.MAX_VALUE: %d%n", solution.reverse(Integer.MAX_VALUE));
        System.out.printf("反转 Integer.MIN_VALUE: %d%n", solution.reverse(Integer.MIN_VALUE));
    }
}