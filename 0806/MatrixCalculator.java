public class MatrixCalculator {
    public static void main(String[] args) {
        int[][] A = { { 1, 2 }, { 3, 4 } };
        int[][] B = { { 5, 6 }, { 7, 8 } };

        System.out.println("矩陣加法：");
        printMatrix(add(A, B));

        System.out.println("矩陣乘法：");
        printMatrix(multiply(A, B));

        System.out.println("矩陣 A 轉置：");
        printMatrix(transpose(A));

        System.out.println("矩陣 B 轉置：");
        printMatrix(transpose(B));

        // 呼叫輔助方法計算並列印矩陣 A 的最大值與最小值
        System.out.println("--- 矩陣 A 的最大值與最小值 ---");
        findAndPrintMinMax(A);

        // 呼叫輔助方法計算並列印矩陣 B 的最大值與最小值
        System.out.println("--- 矩陣 B 的最大值與最小值 ---");
        findAndPrintMinMax(B);
    }

    static int[][] add(int[][] a, int[][] b) {
        int[][] result = new int[a.length][a[0].length];
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a[0].length; j++)
                result[i][j] = a[i][j] + b[i][j];
        return result;
    }

    static int[][] multiply(int[][] a, int[][] b) {
        int[][] result = new int[a.length][b[0].length];
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < b[0].length; j++)
                for (int k = 0; k < a[0].length; k++)
                    result[i][j] += a[i][k] * b[k][j];
        return result;
    }

    static int[][] transpose(int[][] a) {
        int[][] result = new int[a[0].length][a.length];
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a[0].length; j++)
                result[j][i] = a[i][j];
        return result;
    }

    static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row)
                System.out.print(val + "\t");
            System.out.println();
        }
    }

    // 新增：一個輔助方法來尋找並列印矩陣的最大值與最小值
    static void findAndPrintMinMax(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            System.out.println("矩陣為空。");
            return;
        }

        int max = matrix[0][0];
        int min = matrix[0][0];
        for (int[] row : matrix) {
            for (int val : row) {
                if (val > max) {
                    max = val;
                }
                if (val < min) {
                    min = val;
                }
            }
        }
        System.out.println("最大值：" + max + "，最小值：" + min);
    }
}