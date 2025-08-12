import java.util.*;

public class PriorityQueueWithHeap {
    private static class Task {
        final String name;
        final int priority;
        final long seq;

        Task(String name, int priority, long seq) {
            this.name = name;
            this.priority = priority;
            this.seq = seq;
        }

        @Override
        public String toString() {
            return name + "(" + priority + ")";
        }
    }

    private final PriorityQueue<Task> pq;
    private final Map<String, Task> current;
    private long seqGen = 0;

    public PriorityQueueWithHeap() {
        this.current = new HashMap<>();
        this.pq = new PriorityQueue<>((a, b) -> {
            int c = Integer.compare(b.priority, a.priority);
            if (c != 0)
                return c;
            return Long.compare(a.seq, b.seq);
        });
    }

    public void addTask(String name, int priority) {
        if (current.containsKey(name))
            throw new IllegalArgumentException("Task already exists: " + name);
        Task t = new Task(name, priority, seqGen++);
        current.put(name, t);
        pq.offer(t);
    }

    private Task cleanTop() {
        while (!pq.isEmpty()) {
            Task t = pq.peek();
            Task live = current.get(t.name);
            if (t == live)
                return t;
            pq.poll();
        }
        return null;
    }

    public String peek() {
        Task t = cleanTop();
        if (t == null)
            throw new NoSuchElementException("Queue is empty");
        return t.name;
    }

    public String executeNext() {
        Task t = cleanTop();
        if (t == null)
            throw new NoSuchElementException("Queue is empty");
        pq.poll();
        current.remove(t.name);
        return t.name;
    }

    public void changePriority(String name, int newPriority) {
        Task old = current.get(name);
        if (old == null)
            throw new NoSuchElementException("Task not found: " + name);
        Task newer = new Task(name, newPriority, seqGen++);
        current.put(name, newer);
        pq.offer(newer);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return current.size();
    }

    public static void main(String[] args) {
        PriorityQueueWithHeap q = new PriorityQueueWithHeap();
        q.addTask("備份", 1);
        q.addTask("緊急修復", 5);
        q.addTask("更新", 3);
        System.out.println("下一個: " + q.peek());
        List<String> order = new ArrayList<>();
        while (!q.isEmpty()) {
            order.add(q.executeNext());
        }
        System.out.println("執行順序: " + order);
        q.addTask("清理", 2);
        q.addTask("報表", 2);
        q.changePriority("清理", 10);
        System.out.println("下一個: " + q.peek());
    }
}
