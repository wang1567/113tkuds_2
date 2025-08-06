import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int val) {
        this.val = val;
    }
}

public class LevelOrderTraversalVariations {

    // 每層分開儲存
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null)
            return result;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
            result.add(level);
        }
        return result;
    }

    // 之字形層序走訪
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null)
            return result;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        boolean leftToRight = true;
        while (!queue.isEmpty()) {
            int size = queue.size();
            LinkedList<Integer> level = new LinkedList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (leftToRight) {
                    level.addLast(node.val);
                } else {
                    level.addFirst(node.val);
                }
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
            result.add(level);
            leftToRight = !leftToRight;
        }
        return result;
    }

    // 每層最後一個節點
    public static List<Integer> rightmostNodes(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null)
            return result;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            TreeNode last = null;
            for (int i = 0; i < size; i++) {
                last = queue.poll();
                if (last.left != null)
                    queue.add(last.left);
                if (last.right != null)
                    queue.add(last.right);
            }
            result.add(last.val);
        }
        return result;
    }

    // 垂直層序走訪
    public static Map<Integer, List<Integer>> verticalOrder(TreeNode root) {
        Map<Integer, List<Integer>> map = new TreeMap<>();
        if (root == null)
            return map;
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<Integer> cols = new LinkedList<>();
        queue.add(root);
        cols.add(0);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            int col = cols.poll();
            map.computeIfAbsent(col, x -> new ArrayList<>()).add(node.val);
            if (node.left != null) {
                queue.add(node.left);
                cols.add(col - 1);
            }
            if (node.right != null) {
                queue.add(node.right);
                cols.add(col + 1);
            }
        }
        return map;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        // 原程式碼中 root.left.left.right = new TreeNode(5); 會導致 NullPointerException
        // 因為 root.left.left 為 null，無法存取其屬性
        // 修正為 root.left.left = new TreeNode(4); 和 root.left.left.right = new
        // TreeNode(5);
        root.left.left = new TreeNode(4);
        root.left.left.right = new TreeNode(5);
        root.right.right = new TreeNode(6);

        System.out.println("每層分開儲存：" + levelOrder(root));
        System.out.println("之字形層序走訪：" + zigzagLevelOrder(root));
        System.out.println("每層最後一個節點：" + rightmostNodes(root));
        System.out.println("垂直層序走訪：" + verticalOrder(root));
    }
}