import java.util.*;

public class AVLRangeQueryExercise {
    static class Node {
        int key, height;
        Node left, right;

        Node(int k) {
            key = k;
            height = 1;
        }
    }

    private Node root;

    private static int h(Node n) {
        return n == null ? 0 : n.height;
    }

    private static void up(Node n) {
        n.height = Math.max(h(n.left), h(n.right)) + 1;
    }

    private static int b(Node n) {
        return h(n.left) - h(n.right);
    }

    private static Node rr(Node y) {
        Node x = y.left, t2 = x.right;
        x.right = y;
        y.left = t2;
        up(y);
        up(x);
        return x;
    }

    private static Node lr(Node x) {
        Node y = x.right, t2 = y.left;
        y.left = x;
        x.right = t2;
        up(x);
        up(y);
        return y;
    }

    private static Node reb(Node n) {
        int bal = b(n);
        if (bal > 1) {
            if (b(n.left) < 0)
                n.left = lr(n.left);
            return rr(n);
        }
        if (bal < -1) {
            if (b(n.right) > 0)
                n.right = rr(n.right);
            return lr(n);
        }
        return n;
    }

    public void insert(int k) {
        root = ins(root, k);
    }

    private Node ins(Node n, int k) {
        if (n == null)
            return new Node(k);
        if (k < n.key)
            n.left = ins(n.left, k);
        else if (k > n.key)
            n.right = ins(n.right, k);
        else
            return n;
        up(n);
        return reb(n);
    }

    public List<Integer> rangeQuery(int min, int max) {
        List<Integer> ans = new ArrayList<>();
        rq(root, min, max, ans);
        return ans;
    }

    private void rq(Node n, int min, int max, List<Integer> out) {
        if (n == null)
            return;
        if (n.key > min)
            rq(n.left, min, max, out);
        if (n.key >= min && n.key <= max)
            out.add(n.key);
        if (n.key < max)
            rq(n.right, min, max, out);
    }

    public static void main(String[] args) {
        AVLRangeQueryExercise t = new AVLRangeQueryExercise();
        int[] a = { 20, 10, 30, 5, 15, 25, 35, 3, 7, 13, 17 };
        for (int v : a)
            t.insert(v);
        System.out.println(t.rangeQuery(7, 25)); // 7,10,13,15,17,20,25
        System.out.println(t.rangeQuery(1, 4)); // 3
        System.out.println(t.rangeQuery(33, 40)); // 35
    }
}
