import java.util.*;

// 多路樹節點
class MultiWayNode {
    int val;
    List<MultiWayNode> children;

    MultiWayNode(int val) {
        this.val = val;
        this.children = new ArrayList<>();
    }
}

// 多路樹
class MultiWayTree {
    MultiWayNode root;

    public MultiWayTree(int val) {
        this.root = new MultiWayNode(val);
    }

    // 深度優先走訪 (DFS)
    public void DFS() {
        System.out.print("深度優先走訪 (DFS): ");
        dfsHelper(root);
        System.out.println();
    }

    private void dfsHelper(MultiWayNode node) {
        if (node == null)
            return;
        System.out.print(node.val + " ");
        for (MultiWayNode child : node.children) {
            dfsHelper(child);
        }
    }

    // 廣度優先走訪 (BFS)
    public void BFS() {
        System.out.print("廣度優先走訪 (BFS): ");
        if (root == null) {
            System.out.println();
            return;
        }
        Queue<MultiWayNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            MultiWayNode node = queue.poll();
            System.out.print(node.val + " ");
            queue.addAll(node.children);
        }
        System.out.println();
    }

    // 計算樹的高度
    public int getHeight() {
        return getHeightHelper(root);
    }

    private int getHeightHelper(MultiWayNode node) {
        if (node == null)
            return 0;
        int maxChildHeight = 0;
        for (MultiWayNode child : node.children) {
            maxChildHeight = Math.max(maxChildHeight, getHeightHelper(child));
        }
        return 1 + maxChildHeight;
    }

    // 顯示每個節點的度數
    public void printDegrees() {
        System.out.println("每個節點的度數：");
        printDegreesHelper(root);
    }

    private void printDegreesHelper(MultiWayNode node) {
        if (node == null)
            return;
        System.out.println("節點 " + node.val + " 的度數為 " + node.children.size());
        for (MultiWayNode child : node.children) {
            printDegreesHelper(child);
        }
    }
}

// 決策樹節點
class DecisionTreeNode {
    String question;
    String answer;
    List<DecisionTreeNode> children;

    DecisionTreeNode(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.children = new ArrayList<>();
    }
}

public class MultiWayTreeAndDecisionTree {

    public static void main(String[] args) {
        // 多路樹實作
        System.out.println("--- 多路樹操作 ---");
        MultiWayTree tree = new MultiWayTree(1);
        MultiWayNode node2 = new MultiWayNode(2);
        MultiWayNode node3 = new MultiWayNode(3);
        MultiWayNode node4 = new MultiWayNode(4);
        tree.root.children.add(node2);
        tree.root.children.add(node3);
        tree.root.children.add(node4);
        node2.children.add(new MultiWayNode(5));
        node2.children.add(new MultiWayNode(6));
        node4.children.add(new MultiWayNode(7));

        tree.DFS();
        tree.BFS();
        System.out.println("樹的高度：" + tree.getHeight());
        tree.printDegrees();
        System.out.println("--------------------");

        // 決策樹實作 (猜數字遊戲)
        System.out.println("--- 決策樹模擬：猜數字遊戲 ---");
        DecisionTreeNode rootNode = new DecisionTreeNode("你的數字大於 50 嗎？", "");
        DecisionTreeNode leftNode = new DecisionTreeNode("你的數字大於 25 嗎？", "");
        DecisionTreeNode rightNode = new DecisionTreeNode("你的數字大於 75 嗎？", "");
        DecisionTreeNode leaf1 = new DecisionTreeNode("", "你的數字可能是 10！");
        DecisionTreeNode leaf2 = new DecisionTreeNode("", "你的數字可能是 30！");
        DecisionTreeNode leaf3 = new DecisionTreeNode("", "你的數字可能是 60！");
        DecisionTreeNode leaf4 = new DecisionTreeNode("", "你的數字可能是 80！");

        rootNode.children.add(leftNode);
        rootNode.children.add(rightNode);
        leftNode.children.add(leaf1);
        leftNode.children.add(leaf2);
        rightNode.children.add(leaf3);
        rightNode.children.add(leaf4);

        guessGame(rootNode, new Scanner(System.in));
    }

    public static void guessGame(DecisionTreeNode node, Scanner scanner) {
        if (node.answer != null && !node.answer.isEmpty()) {
            System.out.println(node.answer);
            return;
        }

        System.out.println(node.question);
        System.out.print("請輸入「是/yes」或「否/no」：");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("是") || input.equalsIgnoreCase("yes")) {
            // 「是」或「yes」導向右子節點 (index 1)
            if (node.children.size() > 1) {
                guessGame(node.children.get(1), scanner);
            }
        } else if (input.equalsIgnoreCase("否") || input.equalsIgnoreCase("no")) {
            // 「否」或「no」導向左子節點 (index 0)
            if (node.children.size() > 0) {
                guessGame(node.children.get(0), scanner);
            }
        } else {
            System.out.println("輸入無效，請重新輸入。");
            guessGame(node, scanner);
        }
    }
}