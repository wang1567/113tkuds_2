import java.util.*;

public class AdvancedArrayRecursion {

    // 遞迴實作快速排序
    public static void quickSort(int[] arr, int low, int high, int round) {
        if (low < high) {
            int pi = partition(arr, low, high);
            System.out.println("第 " + round + " 輪分區結果：" + Arrays.toString(arr));
            quickSort(arr, low, pi - 1, round + 1);
            quickSort(arr, pi + 1, high, round + 1);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 遞迴合併兩個已排序陣列
    public static List<Integer> mergeSorted(int[] a, int[] b, int i, int j, List<Integer> result) {
        // 當 a 陣列處理完畢時，將 b 陣列中剩餘的元素加入
        if (i == a.length) {
            while (j < b.length) {
                result.add(b[j++]);
            }
            return result;
        }
        // 當 b 陣列處理完畢時，將 a 陣列中剩餘的元素加入
        if (j == b.length) {
            while (i < a.length) {
                result.add(a[i++]);
            }
            return result;
        }

        // 比較 a[i] 和 b[j]，將較小的元素加入結果清單，並遞迴呼叫
        if (a[i] < b[j]) {
            result.add(a[i]);
            return mergeSorted(a, b, i + 1, j, result);
        } else {
            result.add(b[j]);
            return mergeSorted(a, b, i, j + 1, result);
        }
    }

    // 遞迴尋找第 k 小元素（使用快速選擇）
    public static int kthSmallest(int[] arr, int k) {
        if (k > 0 && k <= arr.length) {
            return quickSelect(arr, 0, arr.length - 1, k - 1);
        }
        // 處理 k 值無效的情況
        throw new IllegalArgumentException("k 值無效");
    }

    private static int quickSelect(int[] arr, int low, int high, int k) {
        if (low == high)
            return arr[low];
        int pi = partition(arr, low, high);
        if (k == pi)
            return arr[pi];
        else if (k < pi)
            return quickSelect(arr, low, pi - 1, k);
        else
            return quickSelect(arr, pi + 1, high, k);
    }

    // 遞迴檢查是否存在子序列總和等於目標值
    public static boolean hasSubsetSum(int[] arr, int target, int index) {
        if (target == 0)
            return true;
        if (index == arr.length || target < 0)
            return false;
        return hasSubsetSum(arr, target - arr[index], index + 1) ||
                hasSubsetSum(arr, target, index + 1);
    }

    public static void main(String[] args) {
        int[] arr = { 3, 6, 8, 10, 1, 2, 1 };
        int[] arr2 = { 9, 5, 2, 7, 4 };

        // 快速排序陣列1
        System.out.println("--- 快速排序 陣列1 ---");
        int[] arrForSort1 = arr.clone(); // 複製陣列以保留原始資料
        quickSort(arrForSort1, 0, arrForSort1.length - 1, 1);
        System.out.println("快速排序結果 (陣列1)：" + Arrays.toString(arrForSort1));
        System.out.println();

        // 快速排序陣列2
        System.out.println("--- 快速排序 陣列2 ---");
        int[] arrForSort2 = arr2.clone();
        quickSort(arrForSort2, 0, arrForSort2.length - 1, 1);
        System.out.println("快速排序結果 (陣列2)：" + Arrays.toString(arrForSort2));
        System.out.println();

        // 遞迴合併兩個已排序陣列
        Arrays.sort(arr); // 確保陣列已排序
        Arrays.sort(arr2);
        System.out.println("--- 遞迴合併兩個已排序陣列 ---");
        List<Integer> merged = mergeSorted(arr, arr2, 0, 0, new ArrayList<>());
        System.out.println("合併後陣列：" + merged);
        System.out.println();

        // 遞迴尋找第 k 小元素
        int[] sample = { 7, 10, 4, 3, 20, 15 };
        int k = 3;
        System.out.println("--- 尋找第 k 小元素 ---");
        System.out.println("陣列 {7, 10, 4, 3, 20, 15} 的第 " + k + " 小元素：" + kthSmallest(sample.clone(), k));

        System.out.println("陣列 " + Arrays.toString(arr) + " 的第 " + k + " 小元素：" + kthSmallest(arr.clone(), k));

        System.out.println("陣列 " + Arrays.toString(arr2) + " 的第 " + k + " 小元素：" + kthSmallest(arr2.clone(), k));

        int[] mergedForKth = merged.stream().mapToInt(i -> i).toArray();
        System.out.println("合併後陣列 " + merged + " 的第 " + k + " 小元素：" + kthSmallest(mergedForKth, k));
        System.out.println();

        // 遞迴檢查是否存在子序列總和等於目標值
        int[] subsetArr = { 3, 34, 4, 12, 5, 2 };
        int target = 9;
        System.out.println("--- 檢查子序列總和 ---");
        System.out.println("陣列 " + Arrays.toString(subsetArr) + " 是否存在子序列總和為 " + target + "？"
                + (hasSubsetSum(subsetArr, target, 0) ? "是" : "否"));
    }
}