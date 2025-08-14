import java.util.*;

public class AVLBasicExercise {
    static class Node {
        int key, height;
        Node left, right;

        Node(int k) {
            key = k;
            height = 1;
        }
    }

    private Node root;

    public void insert(int key) {
        root = insert(root, key);
    }

    private Node insert(Node n, int key) {
        if (n == null)
            return new Node(key);
        if (key < n.key)
            n.left = insert(n.left, key);
        else if (key > n.key)
            n.right = insert(n.right, key);
        else
            return n; // ignore dup
        update(n);
        return rebalance(n);
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

    private int check(Node n) {
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

    private static int bal(Node n) {
        return h(n.left) - h(n.right);
    }

    private static void update(Node n) {
        n.height = Math.max(h(n.left), h(n.right)) + 1;
    }

    private static Node rotateRight(Node y) {
        Node x = y.left, t2 = x.right;
        x.right = y;
        y.left = t2;
        update(y);
        update(x);
        return x;
    }

    private static Node rotateLeft(Node x) {
        Node y = x.right, t2 = y.left;
        y.left = x;
        x.right = t2;
        update(x);
        update(y);
        return y;
    }

    private static Node rebalance(Node n) {
        int b = bal(n);
        if (b > 1) {
            if (bal(n.left) < 0)
                n.left = rotateLeft(n.left); // LR
            return rotateRight(n); // LL
        }
        if (b < -1) {
            if (bal(n.right) > 0)
                n.right = rotateRight(n.right); // RL
            return rotateLeft(n); // RR
        }
        return n;
    }

    private void inorder(Node n, StringBuilder sb) {
        if (n == null)
            return;
        inorder(n.left, sb);
        sb.append(n.key).append("(").append(bal(n)).append(") ");
        inorder(n.right, sb);
    }

    public void print() {
        StringBuilder sb = new StringBuilder();
        inorder(root, sb);
        System.out.println(sb.toString().trim());
    }

    public static void main(String[] args) {
        AVLBasicExercise t = new AVLBasicExercise();
        int[] a = { 10, 20, 30, 40, 50, 25 };
        for (int v : a)
            t.insert(v);
        System.out.print("InOrder: ");
        t.print();
        System.out.println("Height: " + t.height());
        System.out.println("Valid AVL: " + t.isValidAVL());
        System.out.println("Search 25: " + t.search(25));
        System.out.println("Search 35: " + t.search(35));
    }
}
