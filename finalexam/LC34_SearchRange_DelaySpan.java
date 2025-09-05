package finalexam;

import java.util.*;

public class LC34_SearchRange_DelaySpan {
    public static int[] searchRange(int[] nums, int target) {
        int[] result = { -1, -1 };

        // 找左邊界
        int left = findLeft(nums, target);
        if (left == nums.length || nums[left] != target) {
            return result;
        }

        // 找右邊界
        int right = findRight(nums, target);

        result[0] = left;
        result[1] = right;
        return result;
    }

    private static int findLeft(int[] nums, int target) {
        int left = 0, right = nums.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private static int findRight(int[] nums, int target) {
        int left = 0, right = nums.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left - 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int target = scanner.nextInt();

        if (n == 0) {
            System.out.println("-1 -1");
            scanner.close();
            return;
        }

        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }

        int[] result = searchRange(nums, target);
        System.out.println(result[0] + " " + result[1]);

        scanner.close();
    }
}
