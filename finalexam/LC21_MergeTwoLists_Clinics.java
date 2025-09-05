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

public class LC21_MergeTwoLists_Clinics {
    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }

        // 掛上剩餘部分
        if (list1 != null) {
            current.next = list1;
        } else {
            current.next = list2;
        }

        return dummy.next;
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
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        int[] values1 = new int[n];
        for (int i = 0; i < n; i++) {
            values1[i] = scanner.nextInt();
        }

        int[] values2 = new int[m];
        for (int i = 0; i < m; i++) {
            values2[i] = scanner.nextInt();
        }

        ListNode list1 = buildList(values1);
        ListNode list2 = buildList(values2);
        ListNode result = mergeTwoLists(list1, list2);
        printList(result);

        scanner.close();
    }
}
