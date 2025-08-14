public class AVLDeleteExercise {
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

    public void delete(int k) {
        root = del(root, k);
    }

    private Node del(Node n, int k) {
        if (n == null)
            return null;
        if (k < n.key)
            n.left = del(n.left, k);
        else if (k > n.key)
            n.right = del(n.right, k);
        else {
            if (n.left == null || n.right == null) {
                n = (n.left != null) ? n.left : n.right;
            } else {
                Node s = min(n.right);
                n.key = s.key;
                n.right = del(n.right, s.key);
            }
        }
        if (n == null)
            return null;
        up(n);
        return reb(n);
    }

    private Node min(Node n) {
        while (n.left != null)
            n = n.left;
        return n;
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

    private void in(Node n, StringBuilder sb) {
        if (n == null)
            return;
        in(n.left, sb);
        sb.append(n.key).append("(").append(b(n)).append(") ");
        in(n.right, sb);
    }

    public void print() {
        StringBuilder sb = new StringBuilder();
        in(root, sb);
        System.out.println(sb.toString().trim());
    }

    public static void main(String[] args) {
        AVLDeleteExercise t = new AVLDeleteExercise();
        int[] a = { 10, 20, 30, 40, 50, 25 };
        for (int v : a)
            t.insert(v);
        System.out.print("InOrder: ");
        t.print();
        System.out.println("Valid: " + t.isValidAVL());
        int[] del = { 40, 25, 30, 10 };
        for (int d : del) {
            t.delete(d);
            System.out.print("After del " + d + ": ");
            t.print();
            System.out.println("Valid: " + t.isValidAVL());
        }
    }
}
