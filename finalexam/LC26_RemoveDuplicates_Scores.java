package finalexam;

import java.util.*;

public class LC26_RemoveDuplicates_Scores {
    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0)
            return 0;

        int write = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                nums[write] = nums[i];
                write++;
            }
        }

        return write;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        if (n == 0) {
            System.out.println(0);
            scanner.close();
            return;
        }

        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }

        int newLength = removeDuplicates(nums);
        System.out.println(newLength);

        for (int i = 0; i < newLength; i++) {
            if (i > 0)
                System.out.print(" ");
            System.out.print(nums[i]);
        }
        if (newLength > 0)
            System.out.println();

        scanner.close();
    }
}
