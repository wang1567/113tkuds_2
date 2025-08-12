import java.util.*;

public class BasicMinHeapPractice {
    private final List<Integer> heap;

    public BasicMinHeapPractice() {
        this.heap = new ArrayList<>();
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }

    private void heapifyUp(int i) {
        while (i > 0 && heap.get(i) < heap.get(parent(i))) {
            Collections.swap(heap, i, parent(i));
            i = parent(i);
        }
    }

    private void heapifyDown(int i) {
        int smallest = i;
        int left = leftChild(i);
        int right = rightChild(i);
        if (left < heap.size() && heap.get(left) < heap.get(smallest))
            smallest = left;
        if (right < heap.size() && heap.get(right) < heap.get(smallest))
            smallest = right;
        if (smallest != i) {
            Collections.swap(heap, i, smallest);
            heapifyDown(smallest);
        }
    }

    public void insert(int val) {
        heap.add(val);
        heapifyUp(heap.size() - 1);
    }

    public int extractMin() {
        if (heap.isEmpty())
            throw new NoSuchElementException("Heap is empty");
        int min = heap.get(0);
        int last = heap.get(heap.size() - 1);
        heap.set(0, last);
        heap.remove(heap.size() - 1);
        if (!heap.isEmpty())
            heapifyDown(0);
        return min;
    }

    public int getMin() {
        if (heap.isEmpty())
            throw new NoSuchElementException("Heap is empty");
        return heap.get(0);
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public void display() {
        System.out.println(heap);
    }

    public static void main(String[] args) {
        System.out.println("=== Basic Min Heap Practice ===");
        BasicMinHeapPractice heap = new BasicMinHeapPractice();
        int[] inputs = { 15, 10, 20, 8, 25, 5 };
        System.out.println("插入順序: " + Arrays.toString(inputs));
        for (int v : inputs) {
            heap.insert(v);
        }
        System.out.println("當前最小值(getMin): " + heap.getMin());
        System.out.print("extractMin 順序: ");
        List<Integer> extracted = new ArrayList<>();
        while (!heap.isEmpty())
            extracted.add(heap.extractMin());
        System.out.println(extracted);
        System.out.println("期望: [5, 8, 10, 15, 20, 25]");
    }
}
