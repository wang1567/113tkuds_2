package midterm;

import java.util.*;

public class M06_PalindromeClean {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        String s = scanner.nextLine();

        StringBuilder cleaned = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                cleaned.append(Character.toLowerCase(c));
            }
        }

        String cleanedStr = cleaned.toString();

        if (isPalindrome(cleanedStr, 0, cleanedStr.length() - 1)) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }

        scanner.close();
    }

    private static boolean isPalindrome(String s, int left, int right) {
        if (left >= right) {
            return true;
        }

        if (s.charAt(left) != s.charAt(right)) {
            return false;
        }

        return isPalindrome(s, left + 1, right - 1);
    }
}
