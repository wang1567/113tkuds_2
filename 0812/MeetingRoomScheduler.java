import java.util.*;

public class MeetingRoomScheduler {

    public static int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0)
            return 0;
        Arrays.sort(intervals, (a, b) -> a[0] == b[0] ? Integer.compare(a[1], b[1]) : Integer.compare(a[0], b[0]));
        PriorityQueue<Integer> endHeap = new PriorityQueue<>();
        for (int[] itv : intervals) {
            int start = itv[0], end = itv[1];
            if (!endHeap.isEmpty() && endHeap.peek() <= start)
                endHeap.poll();
            endHeap.offer(end);
        }
        return endHeap.size();
    }

    public static List<int[]> bestScheduleSingleRoom(int[][] intervals) {
        if (intervals == null || intervals.length == 0)
            return Collections.emptyList();
        int n = intervals.length;
        int[][] arr = new int[n][3];
        for (int i = 0; i < n; i++) {
            int s = intervals[i][0], e = intervals[i][1];
            arr[i][0] = s;
            arr[i][1] = e;
            arr[i][2] = Math.max(0, e - s);
        }
        Arrays.sort(arr, Comparator.comparingInt(a -> a[1]));
        int[] ends = new int[n];
        for (int i = 0; i < n; i++)
            ends[i] = arr[i][1];
        int[] p = new int[n];
        for (int i = 0; i < n; i++) {
            int s = arr[i][0];
            int j = upperBound(ends, s) - 1;
            p[i] = j;
        }
        long[] dp = new long[n];
        boolean[] take = new boolean[n];
        for (int i = 0; i < n; i++) {
            long notTake = i == 0 ? 0 : dp[i - 1];
            long takeVal = arr[i][2] + (p[i] >= 0 ? dp[p[i]] : 0);
            if (takeVal > notTake) {
                dp[i] = takeVal;
                take[i] = true;
            } else {
                dp[i] = notTake;
                take[i] = false;
            }
        }
        List<int[]> res = new ArrayList<>();
        for (int i = n - 1; i >= 0;) {
            if (take[i]) {
                res.add(new int[] { arr[i][0], arr[i][1] });
                i = p[i];
            } else
                i--;
        }
        Collections.reverse(res);
        return res;
    }

    public static long maximizeTotalTimeWithRoomsGreedy(int[][] intervals, int rooms) {
        if (intervals == null || intervals.length == 0 || rooms <= 0)
            return 0;
        int n = intervals.length;
        class I {
            int s, e, d, id;
        }
        I[] arr = new I[n];
        for (int i = 0; i < n; i++) {
            I x = new I();
            x.s = intervals[i][0];
            x.e = intervals[i][1];
            x.d = Math.max(0, x.e - x.s);
            x.id = i;
            arr[i] = x;
        }
        Arrays.sort(arr, (a, b) -> a.s == b.s ? Integer.compare(a.e, b.e) : Integer.compare(a.s, b.s));
        PriorityQueue<I> endHeap = new PriorityQueue<>(Comparator.comparingInt(o -> o.e));
        TreeSet<I> active = new TreeSet<>((a, b) -> {
            if (a.d != b.d)
                return Integer.compare(a.d, b.d);
            if (a.e != b.e)
                return Integer.compare(a.e, b.e);
            return Integer.compare(a.id, b.id);
        });
        boolean[] chosen = new boolean[n];
        for (I cur : arr) {
            while (!endHeap.isEmpty() && endHeap.peek().e <= cur.s) {
                I fin = endHeap.poll();
                active.remove(fin);
            }
            active.add(cur);
            endHeap.offer(cur);
            chosen[cur.id] = true;
            while (active.size() > rooms) {
                I drop = active.first();
                active.remove(drop);
                chosen[drop.id] = false;
            }
        }
        long total = 0;
        for (int i = 0; i < n; i++)
            if (chosen[i])
                total += Math.max(0, intervals[i][1] - intervals[i][0]);
        return total;
    }

    private static int upperBound(int[] a, int key) {
        int l = 0, r = a.length;
        while (l < r) {
            int m = (l + r) >>> 1;
            if (a[m] <= key)
                l = m + 1;
            else
                r = m;
        }
        return l;
    }

    private static String intervalsToString(List<int[]> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            int[] it = list.get(i);
            sb.append("[").append(it[0]).append(",").append(it[1]).append("]");
            if (i + 1 < list.size())
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        int[][] m1 = { { 0, 30 }, { 5, 10 }, { 15, 20 } };
        int[][] m2 = { { 9, 10 }, { 4, 9 }, { 4, 17 } };
        int[][] m3 = { { 1, 5 }, { 8, 9 }, { 8, 9 } };
        System.out.println("最少會議室：");
        System.out.println(Arrays.deepToString(m1) + " → " + minMeetingRooms(m1));
        System.out.println(Arrays.deepToString(m2) + " → " + minMeetingRooms(m2));
        System.out.println(Arrays.deepToString(m3) + " → " + minMeetingRooms(m3));
        System.out.println();
        int[][] single = { { 1, 4 }, { 2, 3 }, { 4, 6 } };
        List<int[]> plan = bestScheduleSingleRoom(single);
        long total = 0;
        for (int[] it : plan)
            total += (it[1] - it[0]);
        System.out.println("單一會議室最佳安排: " + intervalsToString(plan) + ", 總時間=" + total);
        int rooms = 2;
        long bestApprox = maximizeTotalTimeWithRoomsGreedy(m2, rooms);
        System.out.println("N=" + rooms + " 間貪心近似最大總時間（示範）: " + bestApprox);
    }
}
