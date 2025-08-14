import java.util.*;

public class AVLLeaderboardSystem {
    // Order-Statistic AVL Tree storing (score, playerId) with descending score
    // order
    static class Node {
        int score;
        String player;
        int height, size; // size = subtree node count
        Node left, right;

        Node(String player, int score) {
            this.player = player;
            this.score = score;
            this.height = 1;
            this.size = 1;
        }
    }

    private Node root;

    private static int h(Node n) {
        return n == null ? 0 : n.height;
    }

    private static int sz(Node n) {
        return n == null ? 0 : n.size;
    }

    private static void up(Node n) {
        n.height = Math.max(h(n.left), h(n.right)) + 1;
        n.size = sz(n.left) + sz(n.right) + 1;
    }

    private static int b(Node n) {
        return h(n.left) - h(n.right);
    }

    private static int cmp(String p, int s, Node n) { // order by score desc, then player asc
        if (s != n.score)
            return (s > n.score) ? -1 : 1;
        return p.compareTo(n.player);
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

    // insert or update by (player)
    public void upsert(String player, int score) {
        root = upsert(root, player, score);
    }

    private Node upsert(Node n, String p, int s) {
        if (n == null)
            return new Node(p, s);
        int c = cmp(p, s, n);
        if (c < 0)
            n.left = upsert(n.left, p, s);
        else if (c > 0)
            n.right = upsert(n.right, p, s);
        else {
            n.score = s;
        }
        up(n);
        return reb(n);
    }

    // get rank (1-based): number of nodes with higher score + 1
    public int rankOf(String player, int score) {
        return rankOf(root, player, score) + 1;
    }

    private int rankOf(Node n, String p, int s) {
        if (n == null)
            return 0;
        int c = cmp(p, s, n);
        if (c < 0)
            return rankOf(n.left, p, s);
        if (c > 0)
            return sz(n.left) + 1 + rankOf(n.right, p, s);
        return sz(n.left);
    }

    // select k-th (1-based) by score desc
    public String select(int k) {
        Node n = select(root, k);
        return n == null ? null : n.player + ":" + n.score;
    }

    private Node select(Node n, int k) {
        if (n == null || k <= 0 || k > sz(n))
            return null;
        int left = sz(n.left);
        if (k == left + 1)
            return n;
        if (k <= left)
            return select(n.left, k);
        return select(n.right, k - left - 1);
    }

    public List<String> topK(int k) {
        List<String> out = new ArrayList<>();
        for (int i = 1; i <= k && i <= sz(root); i++) {
            out.add(select(i));
        }
        return out;
    }

    public static void main(String[] args) {
        AVLLeaderboardSystem lb = new AVLLeaderboardSystem();
        lb.upsert("alice", 100);
        lb.upsert("bob", 120);
        lb.upsert("cathy", 90);
        lb.upsert("dave", 120); // 同分按名字
        System.out.println("Top3: " + lb.topK(3));
        System.out.println("rank(bob,120)=" + lb.rankOf("bob", 120));
        lb.upsert("alice", 130);
        System.out.println("Top3: " + lb.topK(3));
        System.out.println("rank(alice,130)=" + lb.rankOf("alice", 130));
    }
}
