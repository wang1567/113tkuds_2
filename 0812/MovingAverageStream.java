import java.util.*;

class MovingAverage {
    private final int size;
    private final Deque<Integer> window = new ArrayDeque<>();
    private long sum = 0;
    private final PriorityQueue<Integer> lo = new PriorityQueue<>(Collections.reverseOrder());
    private final PriorityQueue<Integer> hi = new PriorityQueue<>();
    private final Map<Integer, Integer> delayed = new HashMap<>();
    private int loSize = 0, hiSize = 0;
    private final TreeMap<Integer, Integer> multiset = new TreeMap<>();

    public MovingAverage(int size) {
        if (size <= 0)
            throw new IllegalArgumentException("size must be > 0");
        this.size = size;
    }

    public double next(int val) {
        if (window.size() == size) {
            int out = window.removeFirst();
            sum -= out;
            removeFromMedianHeaps(out);
            dec(multiset, out);
        }
        window.addLast(val);
        sum += val;
        addToMedianHeaps(val);
        inc(multiset, val);
        return sum / (double) window.size();
    }

    public double getMedian() {
        if (window.isEmpty())
            throw new NoSuchElementException("window is empty");
        int k = window.size();
        if ((k & 1) == 1)
            return lo.peek();
        return (((long) lo.peek() + (long) hi.peek()) / 2.0);
    }

    public int getMin() {
        if (window.isEmpty())
            throw new NoSuchElementException("window is empty");
        return multiset.firstKey();
    }

    public int getMax() {
        if (window.isEmpty())
            throw new NoSuchElementException("window is empty");
        return multiset.lastKey();
    }

    private void addToMedianHeaps(int num) {
        if (lo.isEmpty() || num <= lo.peek()) {
            lo.offer(num);
            loSize++;
        } else {
            hi.offer(num);
            hiSize++;
        }
        balance();
    }

    private void removeFromMedianHeaps(int num) {
        delayed.put(num, delayed.getOrDefault(num, 0) + 1);
        if (!lo.isEmpty() && num <= lo.peek()) {
            loSize--;
            if (!lo.isEmpty() && Objects.equals(lo.peek(), num))
                prune(lo);
        } else {
            hiSize--;
            if (!hi.isEmpty() && Objects.equals(hi.peek(), num))
                prune(hi);
        }
        balance();
    }

    private void balance() {
        if (loSize > hiSize + 1) {
            hi.offer(lo.poll());
            loSize--;
            hiSize++;
            prune(lo);
        } else if (loSize < hiSize) {
            lo.offer(hi.poll());
            loSize++;
            hiSize--;
            prune(hi);
        }
    }

    private void prune(PriorityQueue<Integer> heap) {
        while (!heap.isEmpty()) {
            int x = heap.peek();
            Integer cnt = delayed.get(x);
            if (cnt == null || cnt == 0)
                break;
            heap.poll();
            if (cnt == 1)
                delayed.remove(x);
            else
                delayed.put(x, cnt - 1);
        }
    }

    private static void inc(TreeMap<Integer, Integer> map, int x) {
        map.put(x, map.getOrDefault(x, 0) + 1);
    }

    private static void dec(TreeMap<Integer, Integer> map, int x) {
        int c = map.getOrDefault(x, 0);
        if (c <= 1)
            map.remove(x);
        else
            map.put(x, c - 1);
    }
}

public class MovingAverageStream {
    private static String fmt(double v) {
        return String.format(java.util.Locale.US, "%.2f", v);
    }

    public static void main(String[] args) {
        MovingAverage ma = new MovingAverage(3);
        System.out.println("next(1) = " + fmt(ma.next(1)));
        System.out.println("next(10) = " + fmt(ma.next(10)));
        System.out.println("next(3) = " + fmt(ma.next(3)));
        System.out.println("next(5) = " + fmt(ma.next(5)));
        System.out.println("getMedian() = " + fmt(ma.getMedian()));
        System.out.println("getMin() = " + ma.getMin());
        System.out.println("getMax() = " + ma.getMax());
    }
}
