package midterm;

import java.util.*;

public class M05_GCD_LCM_Recursive {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long a = scanner.nextLong();
        long b = scanner.nextLong();

        long gcd = gcd(a, b);
        long lcm = a / gcd * b;

        System.out.println("GCD: " + gcd);
        System.out.println("LCM: " + lcm);

        scanner.close();
    }

    private static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
}

/*
 * Time Complexity: O(log(min(a, b)))
 * 說明：歐幾里得演算法每次遞迴至少讓較大數減少一半
 * 遞迴深度最多為log(min(a, b))，每次遞迴操作為O(1)
 */
