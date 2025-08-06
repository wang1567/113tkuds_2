import java.util.*;

public class AdvancedStringRecursion {

    // 遞迴產生字串的所有排列組合
    public static void permute(String str, String result) {
        if (str.isEmpty()) {
            System.out.println(result);
            return;
        }
        for (int i = 0; i < str.length(); i++) {
            permute(str.substring(0, i) + str.substring(i + 1), result + str.charAt(i));
        }
    }

    // 遞迴實作字串匹配演算法（簡單版）
    public static boolean match(String text, String pattern) {
        if (pattern.isEmpty())
            return text.isEmpty();
        boolean firstMatch = (!text.isEmpty() &&
                (pattern.charAt(0) == text.charAt(0) || pattern.charAt(0) == '.'));
        if (pattern.length() >= 2 && pattern.charAt(1) == '*') {
            return match(text, pattern.substring(2)) ||
                    (firstMatch && match(text.substring(1), pattern));
        } else {
            return firstMatch && match(text.substring(1), pattern.substring(1));
        }
    }

    // 遞迴移除字串中的重複字符
    public static String removeDuplicates(String str, Set<Character> seen) {
        if (str.isEmpty())
            return "";
        char first = str.charAt(0);
        if (seen.contains(first)) {
            return removeDuplicates(str.substring(1), seen);
        } else {
            seen.add(first);
            return first + removeDuplicates(str.substring(1), seen);
        }
    }

    // 遞迴計算字串的所有子字串組合
    public static void generateSubstrings(String str, String current) {
        if (str.isEmpty()) {
            if (!current.isEmpty())
                System.out.println(current);
            return;
        }
        generateSubstrings(str.substring(1), current); // 不選
        generateSubstrings(str.substring(1), current + str.charAt(0)); // 選
    }

    public static void main(String[] args) {
        System.out.println("所有排列組合：");
        permute("abc", "");

        System.out.println("\n字串匹配測試：");
        System.out.println("match(\"aab\", \"c*a*b\") = " + match("aab", "c*a*b"));

        System.out.println("\n移除重複字符：");
        String input = "banana";
        System.out.println("原始：" + input);
        System.out.println("移除後：" + removeDuplicates(input, new HashSet<>()));

        System.out.println("\n所有子字串組合：");
        generateSubstrings("abc", "");
    }
}
