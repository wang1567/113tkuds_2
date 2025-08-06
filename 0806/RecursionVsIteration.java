public class RecursionVsIteration {

    // 遞迴計算二項式係數 C(n, k)
    public static int combinationRecursive(int n, int k) {
        if (k == 0 || k == n)
            return 1;
        return combinationRecursive(n - 1, k - 1) + combinationRecursive(n - 1, k);
    }

    // 迭代計算二項式係數 C(n, k)
    public static int combinationIterative(int n, int k) {
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= Math.min(i, k); j++) {
                if (j == 0 || j == i)
                    dp[i][j] = 1;
                else
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
            }
        }
        return dp[n][k];
    }

    // 遞迴尋找陣列所有元素乘積
    public static int productRecursive(int[] arr, int index) {
        if (index == arr.length)
            return 1;
        return arr[index] * productRecursive(arr, index + 1);
    }

    // 迭代尋找陣列所有元素乘積
    public static int productIterative(int[] arr) {
        int product = 1;
        for (int num : arr) {
            product *= num;
        }
        return product;
    }

    // 遞迴計算字串中元音字母數量
    public static int countVowelsRecursive(String str, int index) {
        if (index == str.length())
            return 0;
        char c = Character.toLowerCase(str.charAt(index));
        int count = "aeiou".indexOf(c) >= 0 ? 1 : 0;
        return count + countVowelsRecursive(str, index + 1);
    }

    // 迭代計算元音字母數量
    public static int countVowelsIterative(String str) {
        int count = 0;
        for (char c : str.toLowerCase().toCharArray()) {
            if ("aeiou".indexOf(c) >= 0)
                count++;
        }
        return count;
    }

    // 遞迴檢查括號是否配對正確
    public static boolean isBalancedRecursive(String str, int index, int balance) {
        if (balance < 0)
            return false;
        if (index == str.length())
            return balance == 0;
        char c = str.charAt(index);
        if (c == '(')
            return isBalancedRecursive(str, index + 1, balance + 1);
        else if (c == ')')
            return isBalancedRecursive(str, index + 1, balance - 1);
        else
            return isBalancedRecursive(str, index + 1, balance);
    }

    // 迭代檢查括號是否配對正確
    public static boolean isBalancedIterative(String str) {
        int balance = 0;
        for (char c : str.toCharArray()) {
            if (c == '(')
                balance++;
            else if (c == ')')
                balance--;
            if (balance < 0)
                return false;
        }
        return balance == 0;
    }

    public static void main(String[] args) {
        System.out.println("C(5, 2) 遞迴：" + combinationRecursive(5, 2));
        System.out.println("C(5, 2) 迭代：" + combinationIterative(5, 2));

        int[] arr = { 2, 3, 4 };
        System.out.println("乘積 遞迴：" + productRecursive(arr, 0));
        System.out.println("乘積 迭代：" + productIterative(arr));

        String text = "Recursion and Iteration";
        System.out.println("元音數 遞迴：" + countVowelsRecursive(text, 0));
        System.out.println("元音數 迭代：" + countVowelsIterative(text));

        String brackets = "((())())";
        System.out.println("括號配對 遞迴：" + isBalancedRecursive(brackets, 0, 0));
        System.out.println("括號配對 迭代：" + isBalancedIterative(brackets));
    }
}