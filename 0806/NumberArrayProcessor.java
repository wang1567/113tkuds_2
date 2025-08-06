import java.util.*;

public class NumberArrayProcessor {

    public static void main(String[] args) {
        int[] array1 = { 1, 2, 2, 3, 4, 4, 5 };
        int[] array2 = { 2, 4, 6, 8 };

        // 移除重複元素
        System.out.println("--- 移除重複元素 ---");
        Set<Integer> uniqueSet1 = new LinkedHashSet<>();
        for (int num : array1) {
            uniqueSet1.add(num);
        }
        System.out.println("陣列1移除重複後：" + uniqueSet1);

        Set<Integer> uniqueSet2 = new LinkedHashSet<>();
        for (int num : array2) {
            uniqueSet2.add(num);
        }
        System.out.println("陣列2移除重複後：" + uniqueSet2);

        System.out.println(); // 換行

        // 合併兩個已排序陣列
        System.out.println("--- 合併兩個已排序陣列 ---");
        // 為了確保合併正確，array1 和 array2 應該先被排序
        Arrays.sort(array1);
        Arrays.sort(array2);
        int[] merged = mergeSortedArrays(array1, array2);
        System.out.println("合併後陣列：" + Arrays.toString(merged));

        System.out.println(); // 換行

        // 處理陣列1的最高頻率和分割
        processArray("陣列1", array1);

        // 處理陣列2的最高頻率和分割
        processArray("陣列2", array2);
    }

    // 輔助方法：處理單一陣列的最高頻率和分割
    public static void processArray(String name, int[] array) {
        System.out.println("--- 處理 " + name + " ---");

        // 找出出現頻率最高的元素
        int mostFrequent = findMostFrequent(array);
        System.out.println("最常出現的元素：" + mostFrequent);

        // 分割成兩個相等或近似相等的子陣列
        splitArray(array);

        System.out.println(); // 換行
    }

    // 合併兩個已排序陣列
    static int[] mergeSortedArrays(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length) {
            if (a[i] < b[j]) {
                result[k++] = a[i++];
            } else {
                result[k++] = b[j++];
            }
        }
        while (i < a.length) {
            result[k++] = a[i++];
        }
        while (j < b.length) {
            result[k++] = b[j++];
        }
        return result;
    }

    // 找出出現頻率最高的元素
    static int findMostFrequent(int[] array) {
        if (array == null || array.length == 0) {
            // 處理空陣列的邊界情況
            return -1;
        }

        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : array) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        int mostFrequent = array[0]; // 預設為第一個元素
        int maxCount = 0;
        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequent = entry.getKey();
            }
        }
        return mostFrequent;
    }

    // 分割成兩個相等或近似相等的子陣列
    static void splitArray(int[] array) {
        List<Integer> part1 = new ArrayList<>();
        List<Integer> part2 = new ArrayList<>();
        int sum1 = 0;
        int sum2 = 0;

        int[] sortedArray = Arrays.copyOf(array, array.length);
        Arrays.sort(sortedArray); // 為了讓分割更平均，先排序

        for (int i = sortedArray.length - 1; i >= 0; i--) {
            if (sum1 <= sum2) {
                part1.add(sortedArray[i]);
                sum1 += sortedArray[i];
            } else {
                part2.add(sortedArray[i]);
                sum2 += sortedArray[i];
            }
        }
        System.out.println("分割後子陣列1 (總和: " + sum1 + ")：" + part1);
        System.out.println("分割後子陣列2 (總和: " + sum2 + ")：" + part2);
    }
}