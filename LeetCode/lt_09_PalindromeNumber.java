
public class lt_09_PalindromeNumber {
    /**
     * 方法一：反转一半数字（最优解）⭐
     * 时间复杂度: O(log n)
     * 空间复杂度: O(1)
     * 思路：只反转数字的后半部分，避免溢出问题
     */
    public boolean isPalindrome(int x) {
        // 负数不是回文数
        // 以0结尾的正数（除了0本身）不是回文数
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int reversedHalf = 0;

        // 当原数字小于等于反转数字时，说明已经处理了一半
        while (x > reversedHalf) {
            reversedHalf = reversedHalf * 10 + x % 10;
            x /= 10;
        }

        // 偶数长度：x == reversedHalf
        // 奇数长度：x == reversedHalf / 10 (去掉中间的数字)
        return x == reversedHalf || x == reversedHalf / 10;
    }

    /**
     * 方法二：完全反转数字
     * 时间复杂度: O(log n)
     * 空间复杂度: O(1)
     * 思路：反转整个数字，但需要处理溢出问题
     */
    public boolean isPalindromeReverse(int x) {
        if (x < 0) {
            return false;
        }

        if (x < 10) {
            return true;
        }

        int original = x;
        long reversed = 0; // 使用long防止溢出

        while (x > 0) {
            reversed = reversed * 10 + x % 10;
            x /= 10;
        }

        // 检查是否溢出或不相等
        return reversed <= Integer.MAX_VALUE && original == (int) reversed;
    }

    /**
     * 方法三：字符串方法（不推荐，但直观）
     * 时间复杂度: O(log n)
     * 空间复杂度: O(log n)
     */
    public boolean isPalindromeString(int x) {
        if (x < 0) {
            return false;
        }

        String str = String.valueOf(x);
        int left = 0, right = str.length() - 1;

        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    /**
     * 方法四：数学方法（获取数字位数）
     * 时间复杂度: O(log n)
     * 空间复杂度: O(1)
     */
    public boolean isPalindromeMath(int x) {
        if (x < 0) {
            return false;
        }

        if (x < 10) {
            return true;
        }

        // 计算数字长度
        int length = getLength(x);
        int original = x;

        for (int i = 0; i < length / 2; i++) {
            // 获取最高位数字
            int highDigit = x / (int) Math.pow(10, length - 1 - i);
            x %= (int) Math.pow(10, length - 1 - i);

            // 获取最低位数字
            int lowDigit = original % 10;
            original /= 10;

            if (highDigit != lowDigit) {
                return false;
            }
        }

        return true;
    }

    /**
     * 辅助方法：获取数字长度
     */
    private int getLength(int x) {
        int length = 0;
        while (x > 0) {
            length++;
            x /= 10;
        }
        return length;
    }

    /**
     * 辅助方法：可视化展示反转一半的过程
     */
    public void demonstrateHalfReverse(int x) {
        System.out.printf("反转一半数字演示 - 输入: %d%n", x);

        if (x < 0 || (x % 10 == 0 && x != 0)) {
            System.out.println("快速判断：不是回文数");
            return;
        }

        int original = x;
        int reversedHalf = 0;
        int step = 1;

        System.out.println("反转过程：");
        while (x > reversedHalf) {
            int digit = x % 10;
            reversedHalf = reversedHalf * 10 + digit;
            x /= 10;

            System.out.printf("步骤 %d: 取出 %d, 剩余 %d, 反转后半部分 %d%n",
                    step++, digit, x, reversedHalf);
        }

        boolean isEvenLength = (x == reversedHalf);
        boolean isOddLength = (x == reversedHalf / 10);

        System.out.printf("最终比较: x=%d, reversedHalf=%d%n", x, reversedHalf);
        System.out.printf("偶数长度判断: %d == %d ? %s%n", x, reversedHalf, isEvenLength);
        System.out.printf("奇数长度判断: %d == %d ? %s%n", x, reversedHalf / 10, isOddLength);
        System.out.printf("结果: %d %s回文数%n%n", original, (isEvenLength || isOddLength) ? "是" : "不是");
    }

    // 测试方法
    public static void main(String[] args) {
        lt_09_PalindromeNumber solution = new lt_09_PalindromeNumber();

        // 测试用例
        int[] testCases = {
                121, // true - 标准回文数
                -121, // false - 负数
                10, // false - 以0结尾
                0, // true - 单个数字
                12321, // true - 奇数长度回文
                1221, // true - 偶数长度回文
                123, // false - 非回文
                1, // true - 单个数字
                11, // true - 两位回文
                1001, // true - 中间有0的回文
                2147447412, // true - 大回文数
                1234567899 // false - 大非回文数
        };

        System.out.println("=== 反转一半数字（推荐）===");
        for (int testCase : testCases) {
            boolean result = solution.isPalindrome(testCase);
            System.out.printf("输入: %d -> 输出: %s%n", testCase, result);
        }

        System.out.println("\n=== 完全反转方法验证 ===");
        for (int testCase : testCases) {
            boolean result = solution.isPalindromeReverse(testCase);
            System.out.printf("输入: %d -> 输出: %s%n", testCase, result);
        }

        System.out.println("\n=== 字符串方法验证 ===");
        for (int testCase : testCases) {
            boolean result = solution.isPalindromeString(testCase);
            System.out.printf("输入: %d -> 输出: %s%n", testCase, result);
        }

        // 详细演示反转一半的过程
        System.out.println("\n=== 反转一半过程演示 ===");
        solution.demonstrateHalfReverse(12321); // 奇数长度
        solution.demonstrateHalfReverse(1221); // 偶数长度
        solution.demonstrateHalfReverse(123); // 非回文
        solution.demonstrateHalfReverse(-121); // 负数

        // 性能对比
        System.out.println("=== 性能对比 ===");
        int testNumber = 1234567899;

        long start = System.nanoTime();
        for (int i = 0; i < 1000000; i++) {
            solution.isPalindrome(testNumber);
        }
        long time1 = System.nanoTime() - start;

        start = System.nanoTime();
        for (int i = 0; i < 1000000; i++) {
            solution.isPalindromeReverse(testNumber);
        }
        long time2 = System.nanoTime() - start;

        start = System.nanoTime();
        for (int i = 0; i < 1000000; i++) {
            solution.isPalindromeString(testNumber);
        }
        long time3 = System.nanoTime() - start;

        System.out.printf("反转一半方法: %d ns%n", time1);
        System.out.printf("完全反转方法: %d ns%n", time2);
        System.out.printf("字符串方法: %d ns%n", time3);
    }
}