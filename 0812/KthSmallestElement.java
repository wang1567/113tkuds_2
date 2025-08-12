import java.util.*;

public class KthSmallestElement {
    public static int kthSmallestMaxHeap(int[] arr, int k) {
        if (arr == null || k < 1 || k > arr.length)
            throw new IllegalArgumentException("Invalid k or array");
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int v : arr) {
            maxHeap.offer(v);
            if (maxHeap.size() > k)
                maxHeap.poll();
        }
        return maxHeap.peek();
    }

    public static int kthSmallestMinHeap(int[] arr, int k) {
        if (arr == null || k < 1 || k > arr.length)
            throw new IllegalArgumentException("Invalid k or array");
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int v : arr)
            minHeap.offer(v);
        for (int i = 1; i < k; i++)
            minHeap.poll();
        return minHeap.peek();
    }

    private static void runCase(int[] arr, int k) {
        int a = kthSmallestMaxHeap(arr, k);
        int b = kthSmallestMinHeap(arr, k);
        System.out.println("陣列: " + Arrays.toString(arr) + ", K=" + k);
        System.out.println("方法1(MaxHeap, O(n log k), 空間O(k)) → " + a);
        System.out.println("方法2(MinHeap, 插入O(n log n)，或 heapify O(n)+移除O(k log n)) → " + b);
        System.out.println();
    }

    public static void main(String[] args) {
        runCase(new int[] { 7, 10, 4, 3, 20, 15 }, 3);
        runCase(new int[] { 1 }, 1);
        runCase(new int[] { 3, 1, 4, 1, 5, 9, 2, 6 }, 4);
    }
}
