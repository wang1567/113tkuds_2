import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int val) {
        this.val = val;
    }
}

public class BSTKthElement {

    // 插入節點
    public static TreeNode insert(TreeNode root, int val) {
        if (root == null)
            return new TreeNode(val);
        if (val < root.val)
            root.left = insert(root.left, val);
        else
            root.right = insert(root.right, val);
        return root;
    }

    // 中序走訪收集節點值（遞增排序）
    public static void inorder(TreeNode root, List<Integer> result) {
        if (root == null)
            return;
        inorder(root.left, result);
        result.add(root.val);
        inorder(root.right, result);
    }

    // 找第 k 小元素
    public static int kthSmallest(TreeNode root, int k) {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result.get(k - 1);
    }

    // 找第 k 大元素
    public static int kthLargest(TreeNode root, int k) {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result.get(result.size() - k);
    }

    // 找第 k 到第 j 小元素
    public static List<Integer> rangeKtoJ(TreeNode root, int k, int j) {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        // subList 的第二個參數是不包含在內的索引
        return result.subList(k - 1, j);
    }

    // 動態插入與刪除後查詢第 k 小元素
    public static TreeNode delete(TreeNode root, int key) {
        if (root == null)
            return null;
        if (key < root.val)
            root.left = delete(root.left, key);
        else if (key > root.val)
            root.right = delete(root.right, key);
        else {
            if (root.left == null)
                return root.right;
            if (root.right == null)
                return root.left;
            TreeNode minNode = findMin(root.right);
            root.val = minNode.val;
            root.right = delete(root.right, minNode.val);
        }
        return root;
    }

    private static TreeNode findMin(TreeNode root) {
        while (root.left != null)
            root = root.left;
        return root;
    }

    public static void main(String[] args) {
        int[] values = { 20, 10, 30, 5, 15, 25, 35 };
        TreeNode root = null;

        System.out.println("原始陣列：" + Arrays.toString(values));

        for (int val : values) {
            root = insert(root, val);
        }

        System.out.println("--------------------------------------");
        System.out.println("第 3 小元素：" + kthSmallest(root, 3));
        System.out.println("第 2 大元素：" + kthLargest(root, 2));
        System.out.println("第 2 到第 5 小元素：" + rangeKtoJ(root, 2, 5));

        System.out.println("--------------------------------------");
        // 動態更新 BST
        root = insert(root, 12);
        root = delete(root, 25);

        // 顯示更新後的陣列狀態
        List<Integer> updatedList = new ArrayList<>();
        inorder(root, updatedList);
        System.out.println("動態更新後樹的節點（中序走訪）：" + updatedList);

        System.out.println("動態更新後第 4 小元素：" + kthSmallest(root, 4));
    }
}