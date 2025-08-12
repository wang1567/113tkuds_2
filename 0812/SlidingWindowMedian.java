import java.util.*;

public class SlidingWindowMedian {
    private PriorityQueue<Integer> lo = new PriorityQueue<>(Collections.reverseOrder());
    private PriorityQueue<Integer> hi = new PriorityQueue<>();
    private Map<Integer, Integer> delayed = new HashMap<>();
    private int loSize = 0, hiSize = 0;

    private void add(int num) {
        if (lo.isEmpty() || num <= lo.peek()) {
            lo.offer(num);
            loSize++;
        } else {
            hi.offer(num);
            hiSize++;
        }
        balance();
    }

    private void remove(int num) {
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

    private double getMedian(int k) {
        if ((k & 1) == 1)
            return lo.peek();
        return (((long) lo.peek() + (long) hi.peek()) / 2.0);
    }

    public double[] medianSlidingWindow(int[] nums, int k) {
        if (nums == null || k <= 0 || nums.length == 0 || k > nums.length)
            return new double[0];
        int n = nums.length;
        double[] ans = new double[n - k + 1];
        for (int i = 0; i < k; i++)
            add(nums[i]);
        ans[0] = getMedian(k);
        for (int i = k; i < n; i++) {
            add(nums[i]);
            remove(nums[i - k]);
            ans[i - k + 1] = getMedian(k);
        }
        return ans;
    }

    private static String format(double[] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            double v = arr[i];
            if (Math.floor(v) == v)
                sb.append((long) v);
            else
                sb.append(v);
            if (i + 1 < arr.length)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        SlidingWindowMedian swm = new SlidingWindowMedian();
        int[] a1 = { 1, 3, -1, -3, 5, 3, 6, 7 };
        int k1 = 3;
        System.out.println("輸入：" + Arrays.toString(a1) + ", K=" + k1);
        System.out.println("輸出：" + format(swm.medianSlidingWindow(a1, k1)));
        System.out.println();
        int[] a2 = { 1, 2, 3, 4 };
        int k2 = 2;
        System.out.println("輸入：" + Arrays.toString(a2) + ", K=" + k2);
        System.out.println("輸出：" + format(swm.medianSlidingWindow(a2, k2)));
    }
}
