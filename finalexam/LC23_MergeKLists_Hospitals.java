package finalexam;

import java.util.*;

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

public class LC23_MergeKLists_Hospitals {
    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0)
            return null;

        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);

        // 將所有非空的頭節點加入堆
        for (ListNode head : lists) {
            if (head != null) {
                pq.offer(head);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        while (!pq.isEmpty()) {
            ListNode node = pq.poll();
            current.next = node;
            current = current.next;

            if (node.next != null) {
                pq.offer(node.next);
            }
        }

        return dummy.next;
    }

    private static ListNode buildList(List<Integer> values) {
        if (values.isEmpty())
            return null;
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        for (int value : values) {
            current.next = new ListNode(value);
            current = current.next;
        }
        return dummy.next;
    }

    private static void printList(ListNode head) {
        List<Integer> result = new ArrayList<>();
        while (head != null) {
            result.add(head.val);
            head = head.next;
        }
        for (int i = 0; i < result.size(); i++) {
            if (i > 0)
                System.out.print(" ");
            System.out.print(result.get(i));
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        scanner.nextLine(); // 消耗換行符

        ListNode[] lists = new ListNode[k];

        for (int i = 0; i < k; i++) {
            String line = scanner.nextLine();
            String[] tokens = line.split(" ");
            List<Integer> values = new ArrayList<>();

            for (String token : tokens) {
                int val = Integer.parseInt(token);
                if (val == -1)
                    break;
                values.add(val);
            }

            lists[i] = buildList(values);
        }

        ListNode result = mergeKLists(lists);
        printList(result);

        scanner.close();
    }
}
