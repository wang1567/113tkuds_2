package finalexam;

import java.util.*;

public class LC27_RemoveElement_Recycle {
    public static int removeElement(int[] nums, int val) {
        int write = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[write] = nums[i];
                write++;
            }
        }
        return write;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int val = scanner.nextInt();

        if (n == 0) {
            System.out.println(0);
            scanner.close();
            return;
        }

        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }

        int newLength = removeElement(nums, val);
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
