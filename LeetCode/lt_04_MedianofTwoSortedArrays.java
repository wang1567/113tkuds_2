
import java.util.Arrays;

public class lt_04_MedianofTwoSortedArrays {
    /**
     * 二分查找方法 - O(log(min(m,n)))
     * 核心思想：在较短的数组上进行二分查找，找到合适的分割点
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // 确保nums1是较短的数组，优化性能
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int m = nums1.length;
        int n = nums2.length;
        int left = 0, right = m;

        while (left <= right) {
            // 在nums1中的分割点
            int partitionX = (left + right) / 2;
            // 在nums2中的分割点，确保左半部分总数 = (m+n+1)/2
            int partitionY = (m + n + 1) / 2 - partitionX;

            // 计算分割点左右的边界值
            int maxLeftX = (partitionX == 0) ? Integer.MIN_VALUE : nums1[partitionX - 1];
            int minRightX = (partitionX == m) ? Integer.MAX_VALUE : nums1[partitionX];

            int maxLeftY = (partitionY == 0) ? Integer.MIN_VALUE : nums2[partitionY - 1];
            int minRightY = (partitionY == n) ? Integer.MAX_VALUE : nums2[partitionY];

            // 检查是否找到了正确的分割点
            if (maxLeftX <= minRightY && maxLeftY <= minRightX) {
                // 找到了正确的分割点
                if ((m + n) % 2 == 0) {
                    // 总长度为偶数，返回中间两个数的平均值
                    return (Math.max(maxLeftX, maxLeftY) + Math.min(minRightX, minRightY)) / 2.0;
                } else {
                    // 总长度为奇数，返回左半部分的最大值
                    return Math.max(maxLeftX, maxLeftY);
                }
            } else if (maxLeftX > minRightY) {
                // partitionX太大，需要向左移动
                right = partitionX - 1;
            } else {
                // partitionX太小，需要向右移动
                left = partitionX + 1;
            }
        }

        // 理论上不会到达这里
        throw new IllegalArgumentException("输入数组不是有序的");
    }

    /**
     * 暴力方法 - O(m+n) 仅供对比
     * 合并两个数组然后找中位数
     */
    public double findMedianSortedArraysBruteForce(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int[] merged = new int[m + n];
        int i = 0, j = 0, k = 0;

        // 合并两个有序数组
        while (i < m && j < n) {
            if (nums1[i] <= nums2[j]) {
                merged[k++] = nums1[i++];
            } else {
                merged[k++] = nums2[j++];
            }
        }

        // 添加剩余元素
        while (i < m)
            merged[k++] = nums1[i++];
        while (j < n)
            merged[k++] = nums2[j++];

        // 计算中位数
        int totalLength = m + n;
        if (totalLength % 2 == 0) {
            return (merged[totalLength / 2 - 1] + merged[totalLength / 2]) / 2.0;
        } else {
            return merged[totalLength / 2];
        }
    }

    /**
     * 优化的O(log(m+n))方法
     * 使用递归的二分查找找第k小的元素
     */
    public double findMedianSortedArraysRecursive(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int totalLength = m + n;

        if (totalLength % 2 == 1) {
            // 奇数长度，找第(totalLength/2 + 1)小的元素
            return findKthElement(nums1, 0, nums2, 0, totalLength / 2 + 1);
        } else {
            // 偶数长度，找第(totalLength/2)和第(totalLength/2 + 1)小的元素
            double left = findKthElement(nums1, 0, nums2, 0, totalLength / 2);
            double right = findKthElement(nums1, 0, nums2, 0, totalLength / 2 + 1);
            return (left + right) / 2.0;
        }
    }

    private double findKthElement(int[] nums1, int start1, int[] nums2, int start2, int k) {
        // 确保nums1是较短的数组
        if (nums1.length - start1 > nums2.length - start2) {
            return findKthElement(nums2, start2, nums1, start1, k);
        }

        // 如果nums1为空，直接从nums2中取第k个元素
        if (start1 >= nums1.length) {
            return nums2[start2 + k - 1];
        }

        // 如果k=1，返回两个数组开头的较小值
        if (k == 1) {
            return Math.min(nums1[start1], nums2[start2]);
        }

        // 在nums1中取前min(k/2, nums1剩余长度)个元素
        int i = Math.min(start1 + k / 2 - 1, nums1.length - 1);
        int j = start2 + k / 2 - 1;

        if (nums1[i] < nums2[j]) {
            // 丢弃nums1的前(i-start1+1)个元素
            return findKthElement(nums1, i + 1, nums2, start2, k - (i - start1 + 1));
        } else {
            // 丢弃nums2的前(j-start2+1)个元素
            return findKthElement(nums1, start1, nums2, j + 1, k - (j - start2 + 1));
        }
    }

    // 测试方法
    public static void main(String[] args) {
        lt_04_MedianofTwoSortedArrays solution = new lt_04_MedianofTwoSortedArrays();

        // 测试用例
        int[][] testCases1 = {
                { 1, 3 },
                { 1, 2 },
                {},
                { 1 },
                { 1, 3, 5, 7, 9 }
        };

        int[][] testCases2 = {
                { 2 },
                { 3, 4 },
                { 2, 4, 6 },
                { 2, 3, 4 },
                { 2, 4, 6, 8, 10, 12 }
        };

        System.out.println("=== 二分查找方法（推荐） ===");
        for (int i = 0; i < testCases1.length; i++) {
            double result = solution.findMedianSortedArrays(testCases1[i], testCases2[i]);
            System.out.printf("nums1=%s, nums2=%s -> 中位数: %.5f%n",
                    Arrays.toString(testCases1[i]), Arrays.toString(testCases2[i]), result);
        }

        System.out.println("\n=== 递归方法验证 ===");
        for (int i = 0; i < testCases1.length; i++) {
            double result = solution.findMedianSortedArraysRecursive(testCases1[i], testCases2[i]);
            System.out.printf("nums1=%s, nums2=%s -> 中位数: %.5f%n",
                    Arrays.toString(testCases1[i]), Arrays.toString(testCases2[i]), result);
        }

        System.out.println("\n=== 暴力方法对比 ===");
        for (int i = 0; i < testCases1.length; i++) {
            double result = solution.findMedianSortedArraysBruteForce(testCases1[i], testCases2[i]);
            System.out.printf("nums1=%s, nums2=%s -> 中位数: %.5f%n",
                    Arrays.toString(testCases1[i]), Arrays.toString(testCases2[i]), result);
        }
    }
}