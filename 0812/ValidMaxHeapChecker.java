import java.util.Arrays;

public class ValidMaxHeapChecker {
    public static boolean isValidMaxHeap(int[] arr) {
        if (arr == null || arr.length <= 1)
            return true;
        int n = arr.length;
        for (int i = 0; i <= (n - 2) / 2; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            if (left < n && arr[i] < arr[left])
                return false;
            if (right < n && arr[i] < arr[right])
                return false;
        }
        return true;
    }

    public static int firstViolationIndex(int[] arr) {
        if (arr == null || arr.length <= 1)
            return -1;
        int n = arr.length;
        for (int i = 0; i <= (n - 2) / 2; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            if (left < n && arr[left] > arr[i])
                return left;
            if (right < n && arr[right] > arr[i])
                return right;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[][] tests = new int[][] { new int[] { 100, 90, 80, 70, 60, 75, 65 },
                new int[] { 100, 90, 80, 95, 60, 75, 65 }, new int[] { 50 }, new int[] {} };

        for (int[] arr : tests) {
            boolean valid = isValidMaxHeap(arr);
            System.out.print(Arrays.toString(arr) + " → " + valid);
            if (!valid) {
                int idx = firstViolationIndex(arr);
                int p = (idx - 1) / 2;
                System.out.print(" (索引" + idx + "的" + arr[idx] + "大於父節點索引" + p + "的" + arr[p] + ")");
            }
            System.out.println();
        }
    }
}
