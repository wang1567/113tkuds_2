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

public class LC25_ReverseKGroup_Shifts {
    public static ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1)
            return head;

        // 檢查是否有足夠的節點
        ListNode curr = head;
        int count = 0;
        while (curr != null && count < k) {
            curr = curr.next;
            count++;
        }

        if (count == k) {
            // 反轉這 k 個節點
            curr = reverseKGroup(curr, k);

            // 反轉當前 k 個節點
            while (count > 0) {
                ListNode tmp = head.next;
                head.next = curr;
                curr = head;
                head = tmp;
                count--;
            }
            head = curr;
        }

        return head;
    }

    private static ListNode buildList(int[] values) {
        if (values.length == 0)
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

        String line = scanner.nextLine();
        if (line.trim().isEmpty()) {
            System.out.println();
            scanner.close();
            return;
        }

        String[] tokens = line.split(" ");
        int[] values = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            values[i] = Integer.parseInt(tokens[i]);
        }

        ListNode head = buildList(values);
        ListNode result = reverseKGroup(head, k);
        printList(result);

        scanner.close();
    }
}
