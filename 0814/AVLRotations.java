public class AVLRotations {

    // 右旋操作
    // 時間複雜度: O(1), 空間複雜度: O(1)
    public static AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // 執行旋轉
        x.right = y;
        y.left = T2;

        // 更新高度
        y.updateHeight();
        x.updateHeight();

        return x; // 新的根節點
    }

    // 左旋操作
    // 時間複雜度: O(1), 空間複雜度: O(1)
    public static AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // 執行旋轉
        y.left = x;
        x.right = T2;

        // 更新高度
        x.updateHeight();
        y.updateHeight();

        return y; // 新的根節點
    }

    private static int h(AVLNode n) {
        return n == null ? 0 : n.height;
    }

    private static void updateAll(AVLNode n) {
        if (n == null)
            return;
        updateAll(n.left);
        updateAll(n.right);
        n.updateHeight();
    }

    private static void pre(AVLNode n, StringBuilder sb) {
        if (n == null)
            return;
        sb.append(n.data).append("(h=").append(n.height).append(") ");
        pre(n.left, sb);
        pre(n.right, sb);
    }

    public static void main(String[] args) {
        AVLNode y = new AVLNode(30);
        y.left = new AVLNode(20);
        y.left.left = new AVLNode(10);
        updateAll(y);
        System.out.println("Before RR: root=" + y.data + ", h=" + y.height + ", bal=" + y.getBalance());
        StringBuilder sb1 = new StringBuilder();
        pre(y, sb1);
        System.out.println("Pre: " + sb1.toString().trim());
        AVLNode newRoot1 = rightRotate(y);
        updateAll(newRoot1);
        System.out.println(
                "After RR: root=" + newRoot1.data + ", h=" + newRoot1.height + ", bal=" + newRoot1.getBalance());
        StringBuilder sb2 = new StringBuilder();
        pre(newRoot1, sb2);
        System.out.println("Pre: " + sb2.toString().trim());

        AVLNode x = new AVLNode(10);
        x.right = new AVLNode(20);
        x.right.right = new AVLNode(30);
        updateAll(x);
        System.out.println("Before LR: root=" + x.data + ", h=" + x.height + ", bal=" + x.getBalance());
        StringBuilder sb3 = new StringBuilder();
        pre(x, sb3);
        System.out.println("Pre: " + sb3.toString().trim());
        AVLNode newRoot2 = leftRotate(x);
        updateAll(newRoot2);
        System.out.println(
                "After LR: root=" + newRoot2.data + ", h=" + newRoot2.height + ", bal=" + newRoot2.getBalance());
        StringBuilder sb4 = new StringBuilder();
        pre(newRoot2, sb4);
        System.out.println("Pre: " + sb4.toString().trim());
    }
}
