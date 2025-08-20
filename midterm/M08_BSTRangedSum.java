package midterm;

import java.util.*;

public class M08_BSTRangedSum {
    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        Integer[] values = new Integer[n];
        for (int i = 0; i < n; i++) {
            int val = scanner.nextInt();
            values[i] = (val == -1) ? null : val;
        }

        int L = scanner.nextInt();
        int R = scanner.nextInt();

        TreeNode root = buildTree(values);
        int sum = rangeSum(root, L, R);

        System.out.println("Sum: " + sum);

        scanner.close();
    }

    private static TreeNode buildTree(Integer[] values) {
        if (values.length == 0 || values[0] == null)
            return null;

        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode node = queue.poll();

            if (i < values.length && values[i] != null) {
                node.left = new TreeNode(values[i]);
                queue.offer(node.left);
            }
            i++;

            if (i < values.length && values[i] != null) {
                node.right = new TreeNode(values[i]);
                queue.offer(node.right);
            }
            i++;
        }

        return root;
    }

    private static int rangeSum(TreeNode root, int L, int R) {
        if (root == null)
            return 0;

        int sum = 0;

        if (root.val >= L && root.val <= R) {
            sum += root.val;
        }

        if (root.val > L) {
            sum += rangeSum(root.left, L, R);
        }

        if (root.val < R) {
            sum += rangeSum(root.right, L, R);
        }

        return sum;
    }
}
