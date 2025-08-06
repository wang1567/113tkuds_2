import java.util.Arrays;

public class SelectionSortImplementation {

    public static void main(String[] args) {
        int[] originalArray = { 64, 25, 12, 22, 11 };

        System.out.println("原始陣列：" + Arrays.toString(originalArray));
        System.out.println("======================================");

        // 選擇排序
        int[] selectionArray = Arrays.copyOf(originalArray, originalArray.length);
        System.out.println("--- 選擇排序 (Selection Sort) ---");
        int[] selectionResults = selectionSort(selectionArray);
        System.out.println("排序後陣列：" + Arrays.toString(selectionArray));
        System.out.println("比較次數：" + selectionResults[0]);
        System.out.println("交換次數：" + selectionResults[1]);
        System.out.println("======================================");

        // 氣泡排序
        int[] bubbleArray = Arrays.copyOf(originalArray, originalArray.length);
        System.out.println("--- 氣泡排序 (Bubble Sort) ---");
        int[] bubbleResults = bubbleSort(bubbleArray);
        System.out.println("排序後陣列：" + Arrays.toString(bubbleArray));
        System.out.println("比較次數：" + bubbleResults[0]);
        System.out.println("交換次數：" + bubbleResults[1]);
        System.out.println("======================================");

        // 效能比較
        comparePerformance(selectionResults[0], selectionResults[1], bubbleResults[0], bubbleResults[1]);
    }

    /**
     * 選擇排序
     * 
     * @param arr 要排序的陣列
     * @return 一個包含 [比較次數, 交換次數] 的陣列
     */
    public static int[] selectionSort(int[] arr) {
        int comparisons = 0;
        int swaps = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < arr.length; j++) {
                comparisons++;
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                int temp = arr[i];
                arr[i] = arr[minIdx];
                arr[minIdx] = temp;
                swaps++;
            }
            System.out.println("第 " + (i + 1) + " 輪排序結果：" + Arrays.toString(arr));
        }
        return new int[] { comparisons, swaps };
    }

    /**
     * 氣泡排序
     * 
     * @param arr 要排序的陣列
     * @return 一個包含 [比較次數, 交換次數] 的陣列
     */
    public static int[] bubbleSort(int[] arr) {
        int comparisons = 0;
        int swaps = 0;
        boolean swapped;
        for (int i = 0; i < arr.length - 1; i++) {
            swapped = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                comparisons++;
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swaps++;
                    swapped = true;
                }
            }
            System.out.println("第 " + (i + 1) + " 輪排序結果：" + Arrays.toString(arr));
            if (!swapped)
                break;
        }
        return new int[] { comparisons, swaps };
    }

    /**
     * 比較兩種排序演算法的效能
     */
    public static void comparePerformance(int selComp, int selSwap, int bubComp, int bubSwap) {
        System.out.println("--- 效能比較 ---");
        System.out.println("選擇排序：\n- 比較次數：" + selComp + "\n- 交換次數：" + selSwap);
        System.out.println("氣泡排序：\n- 比較次數：" + bubComp + "\n- 交換次數：" + bubSwap);
        System.out.println();

        System.out.println("效能結論：");
        System.out.println("--------------------------------------");
        System.out.println("比較次數：");
        if (selComp < bubComp) {
            System.out.println("選擇排序的比較次數更少，效能較好。");
        } else if (bubComp < selComp) {
            System.out.println("氣泡排序的比較次數更少，效能較好。");
        } else {
            System.out.println("兩者的比較次數相同。");
        }
        System.out.println("--------------------------------------");
        System.out.println("交換次數：");
        if (selSwap < bubSwap) {
            System.out.println("選擇排序的交換次數更少，效能較好。");
        } else if (bubSwap < selSwap) {
            System.out.println("氣泡排序的交換次數更少，效能較好。");
        } else {
            System.out.println("兩者的交換次數相同。");
        }
        System.out.println("--------------------------------------");

        if (selComp <= bubComp && selSwap < bubSwap) {
            System.out.println("綜合來看，**選擇排序**在本次測試中表現更好，因為它的交換次數更少。");
        } else if (bubComp < selComp && bubSwap <= selSwap) {
            System.out.println("綜合來看，**氣泡排序**在本次測試中表現更好。");
        } else {
            System.out.println("綜合來看，兩種排序各有優劣，需要根據具體情況分析。");
        }
    }
}