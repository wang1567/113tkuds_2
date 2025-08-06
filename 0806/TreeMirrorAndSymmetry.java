import java.util.Arrays;

class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int val) {
        this.val = val;
    }
}

public class TreeMirrorAndSymmetry {

    // 判斷是否為對稱樹
    public static boolean isSymmetric(TreeNode root) {
        if (root == null)
            return true;
        return isMirror(root.left, root.right);
    }

    private static boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null)
            return true;
        if (t1 == null || t2 == null)
            return false;
        return (t1.val == t2.val) &&
                isMirror(t1.left, t2.right) &&
                isMirror(t1.right, t2.left);
    }

    // 將樹轉換為鏡像
    public static TreeNode mirror(TreeNode root) {
        if (root == null)
            return null;
        TreeNode temp = root.left;
        root.left = mirror(root.right);
        root.right = mirror(temp);
        return root;
    }

    // 判斷兩棵樹是否互為鏡像
    public static boolean areMirrors(TreeNode a, TreeNode b) {
        if (a == null && b == null)
            return true;
        if (a == null || b == null)
            return false;
        return (a.val == b.val) &&
                areMirrors(a.left, b.right) &&
                areMirrors(a.right, b.left);
    }

    // 判斷是否為子樹
    public static boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null)
            return false;
        if (isSameTree(root, subRoot))
            return true;
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    private static boolean isSameTree(TreeNode a, TreeNode b) {
        if (a == null && b == null)
            return true;
        if (a == null || b == null || a.val != b.val)
            return false;
        return isSameTree(a.left, b.left) && isSameTree(a.right, b.right);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(3);
        // 修正此行，將新節點賦值給 root.left.right
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(3);

        System.out.println("是否為對稱樹：" + (isSymmetric(root) ? "是" : "否"));

        // 建立一個新的樹來進行鏡像測試
        TreeNode originalTree = new TreeNode(1);
        originalTree.left = new TreeNode(2);
        originalTree.right = new TreeNode(3);
        TreeNode mirroredTree = mirror(originalTree);
        System.out.println("鏡像轉換完成");

        // 為了展示效果，需要一個方法來列印樹，這裡省略，但邏輯已修正

        TreeNode a = new TreeNode(1);
        a.left = new TreeNode(2);
        a.right = new TreeNode(3);
        TreeNode b = new TreeNode(1);
        b.left = new TreeNode(3);
        b.right = new TreeNode(2);
        System.out.println("是否互為鏡像：" + (areMirrors(a, b) ? "是" : "否"));

        TreeNode sub = new TreeNode(2);
        sub.left = new TreeNode(4);
        sub.right = new TreeNode(3);
        System.out.println("是否為子樹：" + (isSubtree(root, sub) ? "是" : "否"));
    }
}