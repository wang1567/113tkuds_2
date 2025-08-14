public class RBNode {
    int data;
    RBNode left, right, parent;
    boolean isRed; // true = 紅色, false = 黑色

    public RBNode(int data) {
        this.data = data;
        this.isRed = true; // 新節點預設為紅色
    }

    public RBNode(int data, boolean isRed) {
        this.data = data;
        this.isRed = isRed;
    }

    // 取得兄弟節點
    public RBNode getSibling() {
        if (parent == null)
            return null;
        if (this == parent.left)
            return parent.right;
        return parent.left;
    }

    // 取得叔叔節點
    public RBNode getUncle() {
        if (parent == null || parent.parent == null)
            return null;
        return parent.getSibling();
    }

    // 取得祖父節點
    public RBNode getGrandparent() {
        if (parent == null)
            return null;
        return parent.parent;
    }

    // 簡單字串描述
    private static String desc(RBNode n) {
        if (n == null)
            return "null";
        return n.data + (n.isRed ? "(R)" : "(B)");
    }

    // 簡單自測：建立一個祖父-父-叔-自己的結構並印出關係
    public static void main(String[] args) {
        // 建立節點
        RBNode g = new RBNode(20, false); // 祖父(黑)
        RBNode p = new RBNode(10, true); // 父(紅)
        RBNode u = new RBNode(30, true); // 叔(紅)
        RBNode x = new RBNode(5, true); // 自己(紅)

        // 建立指標關係
        g.left = p;
        g.right = u;
        p.parent = g;
        u.parent = g;
        p.left = x;
        x.parent = p;

        // 驗證 sibling/uncle/grandparent
        System.out.println("節點: " + desc(x));
        System.out.println("父節點: " + desc(x.parent));
        System.out.println("叔叔: " + desc(x.getUncle()));
        System.out.println("祖父: " + desc(x.getGrandparent()));
        System.out.println("父的兄弟(=叔): " + desc(p.getSibling()));

        // 邊界情況：root 的叔/祖為 null
        System.out.println("root(=g) 的叔: " + desc(g.getUncle()));
        System.out.println("root(=g) 的祖父: " + desc(g.getGrandparent()));
    }
}
