public class RecursiveMathCalculator {

    // 計算組合數 C(n, k)
    public static int combination(int n, int k) {
        if (k == 0 || k == n)
            return 1;
        return combination(n - 1, k - 1) + combination(n - 1, k);
    }

    // 計算卡塔蘭數 Catalan(n)
    public static int catalan(int n) {
        if (n <= 1)
            return 1;
        int result = 0;
        for (int i = 0; i < n; i++) {
            result += catalan(i) * catalan(n - 1 - i);
        }
        return result;
    }

    // 計算漢諾塔移動步數
    public static int hanoiMoves(int n) {
        if (n == 1)
            return 1;
        return 2 * hanoiMoves(n - 1) + 1;
    }

    // 判斷是否為回文數
    public static boolean isPalindrome(int number) {
        return number == reverse(number);
    }

    private static int reverse(int num) {
        int reversed = 0;
        while (num > 0) {
            reversed = reversed * 10 + num % 10;
            num /= 10;
        }
        return reversed;
    }

    public static void main(String[] args) {
        int n = 5, k = 2;
        System.out.println("C(" + n + ", " + k + ") = " + combination(n, k));

        int catalanIndex = 4;
        System.out.println("Catalan(" + catalanIndex + ") = " + catalan(catalanIndex));

        int hanoiDisks = 3;
        System.out.println("Hanoi moves for " + hanoiDisks + " disks = " + hanoiMoves(hanoiDisks));

        int number = 12321;
        System.out.println(number + " 是否為回文數？" + (isPalindrome(number) ? "是" : "否"));
    }
}
