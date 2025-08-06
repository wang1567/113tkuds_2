import java.util.*;

class BSTNode {
    int val;
    BSTNode left, right;

    BSTNode(int val) {
        this.val = val;
    }
}

public class BSTRangeQuerySystem {

    // 插入節點
    public static BSTNode insert(BSTNode root, int val) {
        if (root == null)
            return new BSTNode(val);
        if (val < root.val)
            root.left = insert(root.left, val);
        else
            root.right = insert(root.right, val);
        return root;
    }

    // 範圍查詢：找出所有在 [min, max] 範圍內的節點
    public static void rangeQuery(BSTNode root, int min, int max, List<Integer> result) {
        if (root == null)
            return;
        if (root.val > min)
            rangeQuery(root.left, min, max, result);
        if (root.val >= min && root.val <= max)
            result.add(root.val);
        if (root.val < max)
            rangeQuery(root.right, min, max, result);
    }

    // 範圍計數
    public static int rangeCount(BSTNode root, int min, int max) {
        List<Integer> result = new ArrayList<>();
        rangeQuery(root, min, max, result);
        return result.size();
    }

    // 範圍總和
    public static int rangeSum(BSTNode root, int min, int max) {
        List<Integer> result = new ArrayList<>();
        rangeQuery(root, min, max, result);
        return result.stream().mapToInt(Integer::intValue).sum();
    }

    // 最接近查詢
    public static int closestValue(BSTNode root, int target) {
        int closest = root.val;
        while (root != null) {
            if (Math.abs(root.val - target) < Math.abs(closest - target)) {
                closest = root.val;
            }
            root = target < root.val ? root.left : root.right;
        }
        return closest;
    }

    public static void main(String[] args) {
        int[] values = { 20, 10, 30, 5, 15, 25, 35 };
        BSTNode root = null;

        System.out.println("用來建構 BST 的原始陣列：" + Arrays.toString(values));

        for (int val : values) {
            root = insert(root, val);
        }

        System.out.println("----------------------------------------");

        int min = 10, max = 30;
        List<Integer> rangeResult = new ArrayList<>();
        rangeQuery(root, min, max, rangeResult);
        System.out.println("範圍查詢 [" + min + ", " + max + "]： " + rangeResult);
        System.out.println("範圍計數：" + rangeCount(root, min, max));
        System.out.println("範圍總和：" + rangeSum(root, min, max));

        System.out.println("----------------------------------------");

        int target = 28;
        System.out.println("最接近 " + target + " 的節點值：" + closestValue(root, target));
    }
}