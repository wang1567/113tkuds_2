import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int val) {
        this.val = val;
    }
}

class DLLNode {
    int val;
    DLLNode prev, next;

    DLLNode(int val) {
        this.val = val;
    }
}

public class BSTConversionAndBalance {

    // 將 BST 轉換為排序的雙向鏈結串列
    static DLLNode head = null, prev = null;

    public static void bstToDLL(TreeNode root) {
        if (root == null)
            return;
        bstToDLL(root.left);
        DLLNode node = new DLLNode(root.val);
        if (prev == null)
            head = node;
        else {
            prev.next = node;
            node.prev = prev;
        }
        prev = node;
        bstToDLL(root.right);
    }

    // 將排序陣列轉換為平衡 BST
    public static TreeNode sortedArrayToBST(int[] arr, int start, int end) {
        if (start > end)
            return null;
        int mid = (start + end) / 2;
        TreeNode root = new TreeNode(arr[mid]);
        root.left = sortedArrayToBST(arr, start, mid - 1);
        root.right = sortedArrayToBST(arr, mid + 1, end);
        return root;
    }

    // 檢查 BST 是否平衡，並返回其高度
    public static int getHeight(TreeNode node) {
        if (node == null)
            return 0;
        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    // 檢查 BST 是否平衡
    public static boolean isBalanced(TreeNode root) {
        return checkHeight(root) != -1;
    }

    private static int checkHeight(TreeNode node) {
        if (node == null)
            return 0;
        int left = checkHeight(node.left);
        int right = checkHeight(node.right);
        if (left == -1 || right == -1 || Math.abs(left - right) > 1)
            return -1;
        return Math.max(left, right) + 1;
    }

    // 將每個節點值改為所有大於等於該節點值的總和
    static int sum = 0;

    public static void convertToGreaterSumTree(TreeNode root) {
        if (root == null)
            return;
        convertToGreaterSumTree(root.right);
        sum += root.val;
        root.val = sum;
        convertToGreaterSumTree(root.left);
    }

    // 中序走訪列印 BST
    public static void inorder(TreeNode root) {
        if (root == null)
            return;
        inorder(root.left);
        System.out.print(root.val + " ");
        inorder(root.right);
    }

    public static void main(String[] args) {
        // 建立一個不平衡的 BST
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(15);
        root.right.right = new TreeNode(20);

        System.out.println("--- BST 轉雙向鏈結串列 ---");
        // 重設全域變數以確保正確執行
        head = null;
        prev = null;
        bstToDLL(root);
        System.out.print("雙向鏈結串列：");
        DLLNode curr = head;
        while (curr != null) {
            System.out.print(curr.val + " ");
            curr = curr.next;
        }
        System.out.println("\n");

        System.out.println("--- 排序陣列轉平衡 BST ---");
        int[] sorted = { 1, 2, 3, 4, 5, 6, 7 };
        TreeNode balancedRoot = sortedArrayToBST(sorted, 0, sorted.length - 1);
        System.out.print("平衡 BST 中序走訪：");
        inorder(balancedRoot);
        System.out.println("\n");

        // 檢查是否平衡，並顯示平衡因子
        System.out.println("--- 檢查平衡性與平衡因子 ---");
        int originalLeftHeight = getHeight(root.left);
        int originalRightHeight = getHeight(root.right);
        System.out.println("原始 BST 是否平衡：" + (isBalanced(root) ? "是" : "否"));
        System.out.println("原始 BST 平衡因子：" + (originalLeftHeight - originalRightHeight));

        int balancedLeftHeight = getHeight(balancedRoot.left);
        int balancedRightHeight = getHeight(balancedRoot.right);
        System.out.println("平衡 BST 是否平衡：" + (isBalanced(balancedRoot) ? "是" : "否"));
        System.out.println("平衡 BST 平衡因子：" + (balancedLeftHeight - balancedRightHeight));
        System.out.println();

        System.out.println("--- 轉換為所有大於等於總和的 BST ---");
        System.out.print("轉換前 BST 中序走訪：");
        // 重新建構原始 BST 以便轉換
        TreeNode originalForSum = new TreeNode(10);
        originalForSum.left = new TreeNode(5);
        originalForSum.right = new TreeNode(15);
        originalForSum.right.right = new TreeNode(20);
        inorder(originalForSum);
        System.out.println();

        // 重設全域變數以確保正確計算
        sum = 0;
        convertToGreaterSumTree(originalForSum);
        System.out.print("轉換後 BST 中序走訪：");
        inorder(originalForSum);
        System.out.println();
    }
}