public class AVLRotationExercise {
    static class Node {
        int key, height;
        Node left, right;

        Node(int k) {
            key = k;
            height = 1;
        }
    }

    private static int h(Node n) {
        return n == null ? 0 : n.height;
    }

    private static void up(Node n) {
        n.height = Math.max(h(n.left), h(n.right)) + 1;
    }

    private static int b(Node n) {
        return h(n.left) - h(n.right);
    }

    public static Node leftRotate(Node x) {
        Node y = x.right, t2 = y.left;
        y.left = x;
        x.right = t2;
        up(x);
        up(y);
        return y;
    }

    public static Node rightRotate(Node y) {
        Node x = y.left, t2 = x.right;
        x.right = y;
        y.left = t2;
        up(y);
        up(x);
        return x;
    }

    public static Node rebalance(Node n) {
        int bal = b(n);
        if (bal > 1) {
            if (b(n.left) < 0)
                n.left = leftRotate(n.left);
            return rightRotate(n);
        }
        if (bal < -1) {
            if (b(n.right) > 0)
                n.right = rightRotate(n.right);
            return leftRotate(n);
        }
        return n;
    }

    private static void pre(Node n, StringBuilder sb) {
        if (n == null)
            return;
        sb.append(n.key).append("(").append(b(n)).append(") ");
        pre(n.left, sb);
        pre(n.right, sb);
    }

    public static void main(String[] args) {
        // LL -> 右旋
        Node a = new Node(30);
        a.left = new Node(20);
        a.left.left = new Node(10);
        up(a.left.left);
        up(a.left);
        up(a);
        System.out.print("LL before: ");
        StringBuilder s1 = new StringBuilder();
        pre(a, s1);
        System.out.println(s1.toString().trim());
        a = rebalance(a);
        System.out.print("LL after:  ");
        s1.setLength(0);
        pre(a, s1);
        System.out.println(s1.toString().trim());

        // RR -> 左旋
        Node b0 = new Node(10);
        b0.right = new Node(20);
        b0.right.right = new Node(30);
        up(b0.right.right);
        up(b0.right);
        up(b0);
        System.out.print("RR before: ");
        StringBuilder s2 = new StringBuilder();
        pre(b0, s2);
        System.out.println(s2.toString().trim());
        b0 = rebalance(b0);
        System.out.print("RR after:  ");
        s2.setLength(0);
        pre(b0, s2);
        System.out.println(s2.toString().trim());

        // LR -> 左旋(左子) + 右旋
        Node c = new Node(30);
        c.left = new Node(10);
        c.left.right = new Node(20);
        up(c.left.right);
        up(c.left);
        up(c);
        System.out.print("LR before: ");
        StringBuilder s3 = new StringBuilder();
        pre(c, s3);
        System.out.println(s3.toString().trim());
        c = rebalance(c);
        System.out.print("LR after:  ");
        s3.setLength(0);
        pre(c, s3);
        System.out.println(s3.toString().trim());

        // RL -> 右旋(右子) + 左旋
        Node d = new Node(10);
        d.right = new Node(30);
        d.right.left = new Node(20);
        up(d.right.left);
        up(d.right);
        up(d);
        System.out.print("RL before: ");
        StringBuilder s4 = new StringBuilder();
        pre(d, s4);
        System.out.println(s4.toString().trim());
        d = rebalance(d);
        System.out.print("RL after:  ");
        s4.setLength(0);
        pre(d, s4);
        System.out.println(s4.toString().trim());
    }
}
