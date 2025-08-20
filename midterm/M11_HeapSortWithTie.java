package midterm;

import java.util.*;

public class M11_HeapSortWithTie {
    static class ScoreIndex {
        int score;
        int index;

        ScoreIndex(int score, int index) {
            this.score = score;
            this.index = index;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        ScoreIndex[] arr = new ScoreIndex[n];
        for (int i = 0; i < n; i++) {
            int score = scanner.nextInt();
            arr[i] = new ScoreIndex(score, i);
        }

        heapSort(arr);

        for (int i = 0; i < n; i++) {
            System.out.print(arr[i].score);
            if (i < n - 1)
                System.out.print(" ");
        }
        System.out.println();

        scanner.close();
    }

    private static void heapSort(ScoreIndex[] arr) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapifyDown(arr, n, i);
        }

        for (int i = n - 1; i > 0; i--) {

            ScoreIndex temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapifyDown(arr, i, 0);
        }
    }

    private static void heapifyDown(ScoreIndex[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && compare(arr[left], arr[largest]) > 0) {
            largest = left;
        }

        if (right < n && compare(arr[right], arr[largest]) > 0) {
            largest = right;
        }

        if (largest != i) {
            ScoreIndex temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            heapifyDown(arr, n, largest);
        }
    }

    private static int compare(ScoreIndex a, ScoreIndex b) {
        if (a.score != b.score) {
            return Integer.compare(a.score, b.score);
        }
        return Integer.compare(b.index, a.index);
    }
}

/*
 * Time Complexity: O(n log n)
 * 說明：建堆階段為O(n)，排序階段需要n-1次取出最大值，每次重建堆為O(log n)
 * 總複雜度為O(n) + O(n log n) = O(n log n)
 */
