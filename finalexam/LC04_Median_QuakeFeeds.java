package finalexam;

import java.util.*;

public class LC04_Median_QuakeFeeds {
    public static double findMedianSortedArrays(double[] nums1, double[] nums2) {
        // 確保 nums1 是較短的陣列
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int n = nums1.length;
        int m = nums2.length;
        int totalLeft = (n + m + 1) / 2;

        int left = 0, right = n;

        while (left <= right) {
            int i = (left + right) / 2;
            int j = totalLeft - i;

            double nums1LeftMax = (i == 0) ? Double.NEGATIVE_INFINITY : nums1[i - 1];
            double nums1RightMin = (i == n) ? Double.POSITIVE_INFINITY : nums1[i];

            double nums2LeftMax = (j == 0) ? Double.NEGATIVE_INFINITY : nums2[j - 1];
            double nums2RightMin = (j == m) ? Double.POSITIVE_INFINITY : nums2[j];

            if (nums1LeftMax <= nums2RightMin && nums2LeftMax <= nums1RightMin) {
                if ((n + m) % 2 == 1) {
                    return Math.max(nums1LeftMax, nums2LeftMax);
                } else {
                    return (Math.max(nums1LeftMax, nums2LeftMax) +
                            Math.min(nums1RightMin, nums2RightMin)) / 2.0;
                }
            } else if (nums1LeftMax > nums2RightMin) {
                right = i - 1;
            } else {
                left = i + 1;
            }
        }

        return 0.0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        double[] nums1 = new double[n];
        for (int i = 0; i < n; i++) {
            nums1[i] = scanner.nextDouble();
        }

        double[] nums2 = new double[m];
        for (int i = 0; i < m; i++) {
            nums2[i] = scanner.nextDouble();
        }

        double result = findMedianSortedArrays(nums1, nums2);
        System.out.printf("%.1f\n", result);

        scanner.close();
    }
}
