
import java.util.*;

public class lt_06_ZigzagConversion {
    /**
     * 方法一：模拟法（推荐）
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     * 思路：模拟Z字形的移动过程，使用StringBuilder数组存储每行字符
     */
    public String convert(String s, int numRows) {
        if (numRows == 1 || s.length() <= numRows) {
            return s;
        }

        // 为每一行创建一个StringBuilder
        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = new StringBuilder();
        }

        int currentRow = 0;
        boolean goingDown = false; // 方向标志

        // 遍历字符串中的每个字符
        for (char c : s.toCharArray()) {
            rows[currentRow].append(c);

            // 当到达第一行或最后一行时，改变方向
            if (currentRow == 0 || currentRow == numRows - 1) {
                goingDown = !goingDown;
            }

            // 根据方向移动到下一行
            currentRow += goingDown ? 1 : -1;
        }

        // 拼接所有行的结果
        StringBuilder result = new StringBuilder();
        for (StringBuilder row : rows) {
            result.append(row);
        }

        return result.toString();
    }

    /**
     * 方法二：数学规律法
     * 时间复杂度: O(n)
     * 空间复杂度: O(1) (除了结果字符串)
     * 通过数学公式直接计算每行应该包含哪些字符
     */
    public String convertMath(String s, int numRows) {
        if (numRows == 1 || s.length() <= numRows) {
            return s;
        }

        StringBuilder result = new StringBuilder();
        int n = s.length();
        int cycleLen = 2 * numRows - 2; // 一个完整周期的长度

        for (int row = 0; row < numRows; row++) {
            for (int j = 0; j + row < n; j += cycleLen) {
                // 每个周期中该行的第一个字符
                result.append(s.charAt(j + row));

                // 中间行的第二个字符（如果存在）
                if (row != 0 && row != numRows - 1 && j + cycleLen - row < n) {
                    result.append(s.charAt(j + cycleLen - row));
                }
            }
        }

        return result.toString();
    }

    /**
     * 方法三：二维数组模拟（直观但空间复杂度高）
     * 时间复杂度: O(n)
     * 空间复杂度: O(numRows * numCols)
     */
    public String convertMatrix(String s, int numRows) {
        if (numRows == 1 || s.length() <= numRows) {
            return s;
        }

        int n = s.length();
        int cycleLen = 2 * numRows - 2;
        int numCols = (n / cycleLen) * (numRows - 1) + numRows;

        char[][] matrix = new char[numRows][numCols];

        int row = 0, col = 0;
        boolean goingDown = true;

        // 填充矩阵
        for (int i = 0; i < n; i++) {
            matrix[row][col] = s.charAt(i);

            if (goingDown) {
                row++;
                if (row == numRows) {
                    row = numRows - 2;
                    col++;
                    goingDown = false;
                }
            } else {
                row--;
                col++;
                if (row == 0) {
                    goingDown = true;
                }
            }
        }

        // 按行读取结果
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (matrix[i][j] != '\0') {
                    result.append(matrix[i][j]);
                }
            }
        }

        return result.toString();
    }

    /**
     * 辅助方法：可视化显示Z字形排列
     */
    public void visualizeZigzag(String s, int numRows) {
        if (numRows == 1) {
            System.out.println(s);
            return;
        }

        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = new StringBuilder();
        }

        int currentRow = 0;
        boolean goingDown = false;
        int position = 0;

        for (char c : s.toCharArray()) {
            // 添加适当的空格来对齐
            while (rows[currentRow].length() < position) {
                rows[currentRow].append(' ');
            }
            rows[currentRow].append(c);

            if (currentRow == 0 || currentRow == numRows - 1) {
                goingDown = !goingDown;
            }
            currentRow += goingDown ? 1 : -1;

            if (!goingDown && currentRow != numRows - 1) {
                position++;
            }
        }

        System.out.println("Z字形排列可视化:");
        for (StringBuilder row : rows) {
            System.out.println(row.toString());
        }
    }

    // 测试方法
    public static void main(String[] args) {
        lt_06_ZigzagConversion solution = new lt_06_ZigzagConversion();

        // 测试用例
        String[] testStrings = {
                "PAYPALISHIRING",
                "PAYPALISHIRING",
                "A",
                "AB",
                "ABCDEFGHIJKLMN"
        };

        int[] testRows = { 3, 4, 1, 2, 5 };

        System.out.println("=== 模拟法（推荐）===");
        for (int i = 0; i < testStrings.length; i++) {
            String result = solution.convert(testStrings[i], testRows[i]);
            System.out.printf("输入: s=\"%s\", numRows=%d%n", testStrings[i], testRows[i]);
            System.out.printf("输出: \"%s\"%n", result);

            // 显示可视化
            if (testStrings[i].length() <= 20) {
                solution.visualizeZigzag(testStrings[i], testRows[i]);
            }
            System.out.println();
        }

        System.out.println("=== 数学规律法验证 ===");
        for (int i = 0; i < testStrings.length; i++) {
            String result = solution.convertMath(testStrings[i], testRows[i]);
            System.out.printf("输入: s=\"%s\", numRows=%d -> 输出: \"%s\"%n",
                    testStrings[i], testRows[i], result);
        }

        // 性能比较
        System.out.println("\n=== 性能对比 ===");
        String longStr = "PAYPALISHIRING".repeat(100);
        int rows = 10;

        long start = System.nanoTime();
        solution.convert(longStr, rows);
        long time1 = System.nanoTime() - start;

        start = System.nanoTime();
        solution.convertMath(longStr, rows);
        long time2 = System.nanoTime() - start;

        System.out.printf("模拟法耗时: %d ns%n", time1);
        System.out.printf("数学法耗时: %d ns%n", time2);
    }
}