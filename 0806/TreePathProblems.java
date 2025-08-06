import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int val) {
        this.val = val;
    }
}

public class TreePathProblems {

    // 找出所有根到葉節點的路徑
    public static void findAllPaths(TreeNode root, List<Integer> path, List<List<Integer>> result) {
        if (root == null)
            return;
        path.add(root.val);
        if (root.left == null && root.right == null) {
            result.add(new ArrayList<>(path));
        } else {
            findAllPaths(root.left, path, result);
            findAllPaths(root.right, path, result);
        }
        path.remove(path.size() - 1);
    }

    // 判斷是否存在和為目標值的根到葉路徑
    public static boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null)
            return false;
        if (root.left == null && root.right == null)
            return root.val == targetSum;
        return hasPathSum(root.left, targetSum - root.val) ||
                hasPathSum(root.right, targetSum - root.val);
    }

    // 找出和最大的根到葉路徑
    public static int maxRootToLeafSum(TreeNode root) {
        if (root == null)
            return 0;
        return root.val + Math.max(maxRootToLeafSum(root.left), maxRootToLeafSum(root.right));
    }

    // 計算樹的直徑（任意兩節點間最大路徑和）
    static int maxDiameter = 0;

    public static int treeDiameter(TreeNode root) {
        maxDiameter = 0;
        depth(root);
        return maxDiameter;
    }

    private static int depth(TreeNode node) {
        if (node == null)
            return 0;
        int left = depth(node.left);
        int right = depth(node.right);
        maxDiameter = Math.max(maxDiameter, left + right);
        return Math.max(left, right) + 1;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.right.right.right = new TreeNode(1);

        List<List<Integer>> allPaths = new ArrayList<>();
        findAllPaths(root, new ArrayList<>(), allPaths);
        System.out.println("所有根到葉路徑：" + allPaths);

        int targetSum = 22;
        System.out.println("是否存在和為 " + targetSum + " 的路徑：" + (hasPathSum(root, targetSum) ? "是" : "否"));

        System.out.println("最大根到葉路徑和：" + maxRootToLeafSum(root));
        System.out.println("樹的直徑（最大路徑長度）：" + treeDiameter(root));
    }
}