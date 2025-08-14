import java.util.*;

public class TreeComparisonDemo {
    public static void main(String[] args) {
        System.out.println("=== AVL 樹 vs 紅黑樹性能比較 ===");

        AVLTree avlTree = new AVLTree();
        RedBlackTree rbTree = new RedBlackTree();
        Random random = new Random(42); // 固定種子確保可重現

        int n = 50000;
        int[] testData = new int[n];
        for (int i = 0; i < n; i++) {
            testData[i] = random.nextInt(100000);
        }

        // AVL 樹插入測試
        long startTime = System.nanoTime();
        for (int value : testData) {
            avlTree.insert(value);
        }
        long avlInsertTime = System.nanoTime() - startTime;

        // 紅黑樹插入測試
        startTime = System.nanoTime();
        for (int value : testData) {
            rbTree.insert(value);
        }
        long rbInsertTime = System.nanoTime() - startTime;

        // 搜尋測試
        int searchCount = 10000;
        int[] searchData = new int[searchCount];
        for (int i = 0; i < searchCount; i++) {
            searchData[i] = random.nextInt(100000);
        }

        // AVL 樹搜尋測試
        startTime = System.nanoTime();
        for (int value : searchData) {
            avlTree.search(value);
        }
        long avlSearchTime = System.nanoTime() - startTime;

        // 紅黑樹搜尋測試
        startTime = System.nanoTime();
        for (int value : searchData) {
            rbTree.search(value);
        }
        long rbSearchTime = System.nanoTime() - startTime;

        System.out.println("插入 " + n + " 個元素：");
        System.out.println("AVL 樹耗時: " + avlInsertTime / 1_000_000 + " ms");
        System.out.println("紅黑樹耗時: " + rbInsertTime / 1_000_000 + " ms");
        System.out.println("插入性能比 (AVL/RB): " +
                String.format("%.2f", (double) avlInsertTime / rbInsertTime));

        System.out.println("\n搜尋 " + searchCount + " 次：");
        System.out.println("AVL 樹耗時: " + avlSearchTime / 1_000_000 + " ms");
        System.out.println("紅黑樹耗時: " + rbSearchTime / 1_000_000 + " ms");
        System.out.println("搜尋性能比 (AVL/RB): " +
                String.format("%.2f", (double) avlSearchTime / rbSearchTime));

        System.out.println("\n=== 特性比較 ===");
        System.out.println("AVL 樹：");
        System.out.println("  - 嚴格平衡，搜尋性能最佳");
        System.out.println("  - 插入/刪除需要更多旋轉操作");
        System.out.println("  - 適合搜尋密集的應用");

        System.out.println("紅黑樹：");
        System.out.println("  - 近似平衡，插入/刪除性能佳");
        System.out.println("  - 旋轉操作較少");
        System.out.println("  - 適合插入/刪除密集的應用");
    }
}
