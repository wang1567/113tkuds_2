package midterm;

import java.util.*;

public class M01_BuildHeap {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String type = scanner.nextLine().trim();
        int n = scanner.nextInt();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        buildHeap(arr, type.equals("max"));

        for (int i = 0; i < n; i++) {
            System.out.print(arr[i]);
            if (i < n - 1)
                System.out.print(" ");
        }
        System.out.println();

        scanner.close();
    }

    private static void buildHeap(int[] arr, boolean isMaxHeap) {
        int n = arr.length;
        for (int i = (n / 2) - 1; i >= 0; i--) {
            heapifyDown(arr, n, i, isMaxHeap);
        }
    }

    private static void heapifyDown(int[] arr, int n, int i, boolean isMaxHeap) {
        int target = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n) {
            if (isMaxHeap) {
                if (arr[left] > arr[target])
                    target = left;
            } else {
                if (arr[left] < arr[target])
                    target = left;
            }
        }

        if (right < n) {
            if (isMaxHeap) {
                if (arr[right] > arr[target])
                    target = right;
            } else {
                if (arr[right] < arr[target])
                    target = right;
            }
        }

        if (target != i) {
            int temp = arr[i];
            arr[i] = arr[target];
            arr[target] = temp;
            heapifyDown(arr, n, target, isMaxHeap);
        }
    }
}

/*
 * Time Complexity: O(n)
 * 說明：自底向上建堆，每層節點數為n/2^(h+1)，每個節點最多下移h層
 * 總複雜度為∑(h=0 to log n) n/2^(h+1) * h = O(n)，優於逐一插入的O(n log n)
 */
