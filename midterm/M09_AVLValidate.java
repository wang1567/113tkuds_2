package midterm;

import java.util.*;

public class M09_AVLValidate {
    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    static class ValidationResult {
        boolean isValid;
        int height;

        ValidationResult(boolean isValid, int height) {
            this.isValid = isValid;
            this.height = height;
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

        TreeNode root = buildTree(values);

        if (!isBST(root, Long.MIN_VALUE, Long.MAX_VALUE)) {
            System.out.println("Invalid BST");
            scanner.close();
            return;
        }

        ValidationResult result = isAVL(root);
        if (!result.isValid) {
            System.out.println("Invalid AVL");
        } else {
            System.out.println("Valid");
        }

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

    private static boolean isBST(TreeNode root, long min, long max) {
        if (root == null)
            return true;

        if (root.val <= min || root.val >= max)
            return false;

        return isBST(root.left, min, root.val) && isBST(root.right, root.val, max);
    }

    private static ValidationResult isAVL(TreeNode root) {
        if (root == null) {
            return new ValidationResult(true, 0);
        }

        ValidationResult leftResult = isAVL(root.left);
        if (!leftResult.isValid) {
            return new ValidationResult(false, -1);
        }

        ValidationResult rightResult = isAVL(root.right);
        if (!rightResult.isValid) {
            return new ValidationResult(false, -1);
        }

        int balanceFactor = leftResult.height - rightResult.height;
        if (Math.abs(balanceFactor) > 1) {
            return new ValidationResult(false, -1);
        }

        int height = Math.max(leftResult.height, rightResult.height) + 1;
        return new ValidationResult(true, height);
    }
}

/*
 * Time Complexity: O(n)
 * 說明：需要訪問每個節點一次檢查BST性質，再訪問每個節點一次檢查AVL性質
 * 每個節點的檢查操作都是O(1)，總共2n次訪問，所以總複雜度為O(n)
 */
