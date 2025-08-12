import java.util.*;

public class MergeKSortedArrays {
    private static class Node {
        int val;
        int aIdx;
        int eIdx;

        Node(int val, int aIdx, int eIdx) {
            this.val = val;
            this.aIdx = aIdx;
            this.eIdx = eIdx;
        }
    }

    public static int[] mergeKSortedArrays(int[][] arrays) {
        if (arrays == null || arrays.length == 0)
            return new int[0];
        int total = 0;
        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(n -> n.val));
        for (int i = 0; i < arrays.length; i++) {
            int[] arr = arrays[i];
            if (arr != null && arr.length > 0) {
                total += arr.length;
                minHeap.offer(new Node(arr[0], i, 0));
            }
        }
        if (total == 0)
            return new int[0];
        int[] result = new int[total];
        int idx = 0;
        while (!minHeap.isEmpty()) {
            Node cur = minHeap.poll();
            result[idx++] = cur.val;
            int nextIdx = cur.eIdx + 1;
            int[] src = arrays[cur.aIdx];
            if (nextIdx < src.length)
                minHeap.offer(new Node(src[nextIdx], cur.aIdx, nextIdx));
        }
        return result;
    }

    private static void print(int[][] input) {
        System.out.print("輸入：[");
        for (int i = 0; i < input.length; i++) {
            System.out.print(Arrays.toString(input[i] == null ? new int[] {} : input[i]));
            if (i + 1 < input.length)
                System.out.print(", ");
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        int[][] t1 = { { 1, 4, 5 }, { 1, 3, 4 }, { 2, 6 } };
        int[][] t2 = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        int[][] t3 = { { 1 }, { 0 } };
        print(t1);
        System.out.println("輸出：" + Arrays.toString(mergeKSortedArrays(t1)));
        System.out.println();
        print(t2);
        System.out.println("輸出：" + Arrays.toString(mergeKSortedArrays(t2)));
        System.out.println();
        print(t3);
        System.out.println("輸出：" + Arrays.toString(mergeKSortedArrays(t3)));
    }
}
