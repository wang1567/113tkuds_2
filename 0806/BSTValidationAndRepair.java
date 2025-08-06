import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int val) {
        this.val = val;
    }
}

public class BSTValidationAndRepair {

    // 驗證是否為有效 BST
    public static boolean isValidBST(TreeNode root) {
        return validate(root, null, null);
    }

    private static boolean validate(TreeNode node, Integer min, Integer max) {
        if (node == null)
            return true;
        if ((min != null && node.val <= min) || (max != null && node.val >= max))
            return false;
        return validate(node.left, min, node.val) && validate(node.right, node.val, max);
    }

    // 找出不符合規則的節點（中序走訪不遞增）
    public static List<TreeNode> findInvalidNodes(TreeNode root) {
        List<TreeNode> invalid = new ArrayList<>();
        TreeNode[] prev = new TreeNode[1];
        findInvalid(root, prev, invalid);
        return invalid;
    }

    private static void findInvalid(TreeNode node, TreeNode[] prev, List<TreeNode> invalid) {
        if (node == null)
            return;
        findInvalid(node.left, prev, invalid);
        if (prev[0] != null && node.val < prev[0].val) {
            // 只記錄一次交換的兩個節點
            if (invalid.size() < 2) {
                invalid.add(prev[0]);
                invalid.add(node);
            }
        }
        prev[0] = node;
        findInvalid(node.right, prev, invalid);
    }

    // 修復兩個節點錯誤的 BST
    public static void recoverTree(TreeNode root) {
        List<TreeNode> nodes = findInvalidNodes(root);
        if (nodes.size() == 2) {
            int temp = nodes.get(0).val;
            nodes.get(0).val = nodes.get(1).val;
            nodes.get(1).val = temp;
        }
    }

    // 計算需要移除多少節點才能變成有效 BST（簡化為中序遞增子序列長度）
    public static int minRemovalsToBST(TreeNode root) {
        List<Integer> values = new ArrayList<>();
        inorder(root, values);
        return values.size() - longestIncreasingSubsequence(values);
    }

    private static void inorder(TreeNode node, List<Integer> values) {
        if (node == null)
            return;
        inorder(node.left, values);
        values.add(node.val);
        inorder(node.right, values);
    }

    private static int longestIncreasingSubsequence(List<Integer> nums) {
        List<Integer> dp = new ArrayList<>();
        for (int num : nums) {
            int i = Collections.binarySearch(dp, num);
            if (i < 0)
                i = -(i + 1);
            if (i == dp.size())
                dp.add(num);
            else
                dp.set(i, num);
        }
        return dp.size();
    }

    // 新增：顯示二元樹的中序走訪結果
    public static void printTreeInorder(TreeNode root) {
        List<Integer> values = new ArrayList<>();
        inorder(root, values);
        System.out.println("樹的中序走訪序列：" + values);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(1);
        root.right = new TreeNode(4);
        root.right.left = new TreeNode(2); // 錯誤節點

        System.out.println("--- 初始二元樹狀態 ---");
        printTreeInorder(root);
        System.out.println("是否為有效 BST：" + (isValidBST(root) ? "是" : "否"));
        System.out.println("================================");

        List<TreeNode> invalidNodes = findInvalidNodes(root);
        System.out.println("不符合規則的節點數：" + invalidNodes.size());

        System.out.println("--- 執行修復 ---");
        recoverTree(root);

        System.out.println("--- 修復後二元樹狀態 ---");
        printTreeInorder(root);
        System.out.println("修復後是否為有效 BST：" + (isValidBST(root) ? "是" : "否"));
        System.out.println("================================");

        System.out.println("最少需移除節點數：" + minRemovalsToBST(root));
    }
}