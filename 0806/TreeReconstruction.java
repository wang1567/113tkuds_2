import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int val) {
        this.val = val;
    }
}

public class TreeReconstruction {

    // 根據前序與中序重建二元樹
    public static TreeNode buildTreePreIn(int[] preorder, int[] inorder) {
        Map<Integer, Integer> inMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++)
            inMap.put(inorder[i], i);
        return buildPreIn(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, inMap);
    }

    private static TreeNode buildPreIn(int[] pre, int ps, int pe, int[] in, int is, int ie,
            Map<Integer, Integer> inMap) {
        if (ps > pe || is > ie)
            return null;
        TreeNode root = new TreeNode(pre[ps]);
        int inRoot = inMap.get(pre[ps]);
        int numsLeft = inRoot - is;
        root.left = buildPreIn(pre, ps + 1, ps + numsLeft, in, is, inRoot - 1, inMap);
        root.right = buildPreIn(pre, ps + numsLeft + 1, pe, in, inRoot + 1, ie, inMap);
        return root;
    }

    // 根據後序與中序重建二元樹
    public static TreeNode buildTreePostIn(int[] postorder, int[] inorder) {
        Map<Integer, Integer> inMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++)
            inMap.put(inorder[i], i);
        return buildPostIn(postorder, 0, postorder.length - 1, inorder, 0, inorder.length - 1, inMap);
    }

    private static TreeNode buildPostIn(int[] post, int ps, int pe, int[] in, int is, int ie,
            Map<Integer, Integer> inMap) {
        if (ps > pe || is > ie)
            return null;
        TreeNode root = new TreeNode(post[pe]);
        int inRoot = inMap.get(post[pe]);
        int numsLeft = inRoot - is;
        root.left = buildPostIn(post, ps, ps + numsLeft - 1, in, is, inRoot - 1, inMap);
        root.right = buildPostIn(post, ps + numsLeft, pe - 1, in, inRoot + 1, ie, inMap);
        return root;
    }

    // 根據層序重建完全二元樹
    public static TreeNode buildCompleteTree(int[] levelOrder) {
        if (levelOrder.length == 0)
            return null;
        TreeNode root = new TreeNode(levelOrder[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int i = 1;
        while (i < levelOrder.length) {
            TreeNode current = queue.poll();
            current.left = new TreeNode(levelOrder[i++]);
            queue.add(current.left);
            if (i < levelOrder.length) {
                current.right = new TreeNode(levelOrder[i++]);
                queue.add(current.right);
            }
        }
        return root;
    }

    // 驗證中序走訪是否正確
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    private static void inorder(TreeNode node, List<Integer> result) {
        if (node == null)
            return;
        inorder(node.left, result);
        result.add(node.val);
        inorder(node.right, result);
    }

    public static void main(String[] args) {
        int[] preorder = { 3, 9, 20, 15, 7 };
        int[] inorder = { 9, 3, 15, 20, 7 };
        System.out.println("--- 前序+中序重建 ---");
        TreeNode root1 = buildTreePreIn(preorder, inorder);
        List<Integer> result1 = inorderTraversal(root1);
        System.out.println("重建後樹的中序走訪結果：" + result1);
        System.out.println("驗證結果是否正確：" + Arrays.equals(inorder, result1.stream().mapToInt(i -> i).toArray()));
        System.out.println("================================");

        int[] postorder = { 9, 15, 7, 20, 3 };
        System.out.println("--- 後序+中序重建 ---");
        TreeNode root2 = buildTreePostIn(postorder, inorder);
        List<Integer> result2 = inorderTraversal(root2);
        System.out.println("重建後樹的中序走訪結果：" + result2);
        System.out.println("驗證結果是否正確：" + Arrays.equals(inorder, result2.stream().mapToInt(i -> i).toArray()));
        System.out.println("================================");

        int[] levelOrder = { 1, 2, 3, 4, 5, 6, 7 };
        int[] expectedInorderForCompleteTree = { 4, 2, 5, 1, 6, 3, 7 }; // 預期的中序走訪結果
        System.out.println("--- 層序重建完全二元樹 ---");
        TreeNode root3 = buildCompleteTree(levelOrder);
        List<Integer> result3 = inorderTraversal(root3);
        System.out.println("重建後樹的中序走訪結果：" + result3);
        System.out.println("驗證結果是否正確："
                + Arrays.equals(expectedInorderForCompleteTree, result3.stream().mapToInt(i -> i).toArray()));
        System.out.println("================================");
    }
}