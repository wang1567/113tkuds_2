import java.util.*;

public class PersistentAVLExercise {
    // Immutable node for persistence
    static class Node {
        final int key, height;
        final Node left, right;

        Node(int key, Node left, Node right) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.height = 1 + Math.max(h(left), h(right));
        }
    }

    private final Node root;

    public PersistentAVLExercise() {
        this.root = null;
    }

    private PersistentAVLExercise(Node root) {
        this.root = root;
    }

    public PersistentAVLExercise insert(int key) {
        return new PersistentAVLExercise(ins(root, key));
    }

    private Node ins(Node n, int k) {
        if (n == null)
            return new Node(k, null, null);
        if (k < n.key) {
            n = new Node(n.key, ins(n.left, k), n.right);
        } else if (k > n.key) {
            n = new Node(n.key, n.left, ins(n.right, k));
        } else
            return n;
        return reb(n);
    }

    public boolean search(int key) {
        Node n = root;
        while (n != null) {
            if (key == n.key)
                return true;
            n = key < n.key ? n.left : n.right;
        }
        return false;
    }

    public int height() {
        return h(root);
    }

    public boolean isValidAVL() {
        return check(root) != -1;
    }

    private static int check(Node n) {
        if (n == null)
            return 0;
        int lh = check(n.left);
        if (lh == -1)
            return -1;
        int rh = check(n.right);
        if (rh == -1)
            return -1;
        if (Math.abs(lh - rh) > 1)
            return -1;
        return Math.max(lh, rh) + 1;
    }

    private static int h(Node n) {
        return n == null ? 0 : n.height;
    }

    private static int b(Node n) {
        return h(n.left) - h(n.right);
    }

    private static Node rr(Node y) {
        Node x = y.left, t2 = x.right;
        x = new Node(x.key, x.left, new Node(y.key, t2, y.right));
        return fix(x);
    }

    private static Node lr(Node x) {
        Node y = x.right, t2 = y.left;
        y = new Node(y.key, new Node(x.key, x.left, t2), y.right);
        return fix(y);
    }

    private static Node fix(Node n) { // recompute height by rebuilding nodes upward
        // height is computed in constructor; ensure leaves used are intact
        return new Node(n.key, n.left, n.right);
    }

    private static Node reb(Node n) {
        int bal = b(n);
        if (bal > 1) { // LL or LR
            if (b(n.left) < 0)
                n = new Node(n.key, lr(n.left), n.right); // LR
            n = rr(n); // LL
        } else if (bal < -1) { // RR or RL
            if (b(n.right) > 0)
                n = new Node(n.key, n.left, rr(n.right)); // RL
            n = lr(n); // RR
        }
        return n;
    }

    private static void in(Node n, List<Integer> out) {
        if (n == null)
            return;
        in(n.left, out);
        out.add(n.key);
        in(n.right, out);
    }

    public List<Integer> inorder() {
        List<Integer> out = new ArrayList<>();
        in(root, out);
        return out;
    }

    public static void main(String[] args) {
        // 版本 0
        PersistentAVLExercise v0 = new PersistentAVLExercise();
        // 版本 1..n
        PersistentAVLExercise v1 = v0.insert(10);
        PersistentAVLExercise v2 = v1.insert(20);
        PersistentAVLExercise v3 = v2.insert(30); // 會觸發旋轉
        PersistentAVLExercise v4 = v3.insert(25);
        System.out.println("v2 inorder=" + v2.inorder());
        System.out.println("v4 inorder=" + v4.inorder());
        System.out.println("v4 height=" + v4.height() + ", valid=" + v4.isValidAVL());
        System.out.println("v2 search 25=" + v2.search(25));
        System.out.println("v4 search 25=" + v4.search(25));
    }
}
