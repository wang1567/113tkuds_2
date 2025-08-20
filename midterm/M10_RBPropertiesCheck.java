package midterm;

import java.util.*;

public class M10_RBPropertiesCheck {
    static class TreeNode {
        int val;
        char color;
        TreeNode left, right;

        TreeNode(int val, char color) {
            this.val = val;
            this.color = color;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();

        TreeNode[] nodes = new TreeNode[n];

        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine().trim();
            String[] parts = line.split(" ");
            int val = Integer.parseInt(parts[0]);
            char color = parts[1].charAt(0);

            if (val == -1) {
                nodes[i] = null;
            } else {
                nodes[i] = new TreeNode(val, color);
            }
        }

        TreeNode root = buildTree(nodes);
        String result = checkRBProperties(root, nodes);
        System.out.println(result);

        scanner.close();
    }

    private static TreeNode buildTree(TreeNode[] nodes) {
        if (nodes.length == 0 || nodes[0] == null)
            return null;

        TreeNode root = nodes[0];
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;
        while (!queue.isEmpty() && i < nodes.length) {
            TreeNode node = queue.poll();

            if (i < nodes.length && nodes[i] != null) {
                node.left = nodes[i];
                queue.offer(nodes[i]);
            }
            i++;

            if (i < nodes.length && nodes[i] != null) {
                node.right = nodes[i];
                queue.offer(nodes[i]);
            }
            i++;
        }

        return root;
    }

    private static String checkRBProperties(TreeNode root, TreeNode[] nodes) {
        if (root == null)
            return "RB Valid";

        if (root.color != 'B') {
            return "RootNotBlack";
        }

        String redRedCheck = checkRedRedViolation(root, nodes, 0);
        if (!redRedCheck.equals("OK")) {
            return redRedCheck;
        }

        if (checkBlackHeight(root) == -1) {
            return "BlackHeightMismatch";
        }

        return "RB Valid";
    }

    private static String checkRedRedViolation(TreeNode node, TreeNode[] nodes, int index) {
        if (node == null)
            return "OK";

        int leftIndex = 2 * index + 1;
        int rightIndex = 2 * index + 2;

        if (leftIndex < nodes.length && nodes[leftIndex] != null) {
            if (node.color == 'R' && nodes[leftIndex].color == 'R') {
                return "RedRedViolation at index " + leftIndex;
            }
            String leftResult = checkRedRedViolation(nodes[leftIndex], nodes, leftIndex);
            if (!leftResult.equals("OK"))
                return leftResult;
        }

        if (rightIndex < nodes.length && nodes[rightIndex] != null) {
            if (node.color == 'R' && nodes[rightIndex].color == 'R') {
                return "RedRedViolation at index " + rightIndex;
            }
            String rightResult = checkRedRedViolation(nodes[rightIndex], nodes, rightIndex);
            if (!rightResult.equals("OK"))
                return rightResult;
        }

        return "OK";
    }

    private static int checkBlackHeight(TreeNode node) {
        if (node == null)
            return 1;

        int leftHeight = checkBlackHeight(node.left);
        int rightHeight = checkBlackHeight(node.right);

        if (leftHeight == -1 || rightHeight == -1 || leftHeight != rightHeight) {
            return -1;
        }

        return leftHeight + (node.color == 'B' ? 1 : 0);
    }
}
