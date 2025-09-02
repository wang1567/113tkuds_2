
/**
 * Definition for singly-linked list.
 */
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
    }
}

class lt_02_addtwonumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 创建虚拟头节点
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        int carry = 0;
        // 遍历两个链表
        while (l1 != null || l2 != null || carry != 0) {
            // 获取当前节点的值，如果链表已结束则为0
            int x = (l1 != null) ? l1.val : 0;
            int y = (l2 != null) ? l2.val : 0;
            // 计算当前位的和
            int total = x + y + carry;
            carry = total / 10;
            current.next = new ListNode(total % 10);
            // 移动指针
            current = current.next;
            // 移动链表指针（如果还有）
            if (l1 != null)
                l1 = l1.next;
            if (l2 != null)
                l2 = l2.next;
        }
        return dummy.next;
    }
}