import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int val) {
        this.val = val;
    }
}

public class BinaryTreeBasicOperations {

    // 計算節點總和
    public static int sum(TreeNode root) {
        if (root == null)
            return 0;
        return root.val + sum(root.left) + sum(root.right);
    }

    // 計算節點數量
    public static int count(TreeNode root) {
        if (root == null)
            return 0;
        return 1 + count(root.left) + count(root.right);
    }

    // 找最大值
    public static int findMax(TreeNode root) {
        if (root == null)
            return Integer.MIN_VALUE;
        return Math.max(root.val, Math.max(findMax(root.left), findMax(root.right)));
    }

    // 找最小值
    public static int findMin(TreeNode root) {
        if (root == null)
            return Integer.MAX_VALUE;
        return Math.min(root.val, Math.min(findMin(root.left), findMin(root.right)));
    }

    // 計算樹的最大寬度（最大層節點數）
    public static int maxWidth(TreeNode root) {
        if (root == null)
            return 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int maxWidth = 0;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            maxWidth = Math.max(maxWidth, levelSize);
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
        }
        return maxWidth;
    }

    // 判斷是否為完全二元樹
    public static boolean isComplete(TreeNode root) {
        if (root == null)
            return true;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        boolean end = false;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                end = true;
            } else {
                if (end)
                    return false;
                queue.add(node.left);
                queue.add(node.right);
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // 建立測試樹
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(15);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(7);
        root.right.right = new TreeNode(18);

        // 呼叫方法並將結果儲存到變數
        int total = sum(root);
        int nodeCount = count(root);

        // 輸出結果
        System.out.println("節點總和：" + total);
        System.out.println("平均值：" + (total / (double) nodeCount));
        System.out.println("最大值：" + findMax(root));
        System.out.println("最小值：" + findMin(root));
        System.out.println("樹的最大寬度：" + maxWidth(root));
        System.out.println("是否為完全二元樹：" + (isComplete(root) ? "是" : "否"));
    }
}