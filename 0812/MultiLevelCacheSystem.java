import java.util.*;

public class MultiLevelCacheSystem {
    private static final int LEVELS = 3;
    private final int[] cap = new int[] { 2, 5, 10 };
    private final int[] accessCost = new int[] { 1, 3, 10 };
    private final long W_FREQ = 1_000_000L;
    private final long W_TIME = 1_000L;
    private final long W_COST = 1L;

    private static class Node {
        int key;
        String val;
        int freq;
        long lastAccess;
        int level;
        long version;
    }

    private static class Entry {
        long priority;
        int key;
        long version;

        Entry(long p, int k, long v) {
            priority = p;
            key = k;
            version = v;
        }
    }

    private final Map<Integer, Node> map = new HashMap<>();
    private final List<Set<Integer>> levelMembers = new ArrayList<>(LEVELS);
    private final List<PriorityQueue<Entry>> levelHeaps = new ArrayList<>(LEVELS);
    private long clock = 0;

    public MultiLevelCacheSystem() {
        for (int i = 0; i < LEVELS; i++) {
            levelMembers.add(new HashSet<>());
            levelHeaps.add(new PriorityQueue<>(Comparator.comparingLong(e -> e.priority)));
        }
    }

    public MultiLevelCacheSystem(int[] capacities, int[] costs) {
        if (capacities.length != LEVELS || costs.length != LEVELS)
            throw new IllegalArgumentException();
        System.arraycopy(capacities, 0, cap, 0, LEVELS);
        System.arraycopy(costs, 0, accessCost, 0, LEVELS);
        for (int i = 0; i < LEVELS; i++) {
            levelMembers.add(new HashSet<>());
            levelHeaps.add(new PriorityQueue<>(Comparator.comparingLong(e -> e.priority)));
        }
    }

    public String get(int key) {
        Node n = map.get(key);
        if (n == null)
            return null;
        touch(n);
        if (n.level > 0)
            moveToLevel(n, 0);
        rebalance();
        return n.val;
    }

    public void put(int key, String value) {
        Node n = map.get(key);
        if (n == null) {
            n = new Node();
            n.key = key;
            n.val = value;
            n.freq = 1;
            n.lastAccess = ++clock;
            n.version = 1;
            n.level = 0;
            map.put(key, n);
            levelMembers.get(0).add(key);
            pushToHeap(n);
        } else {
            n.val = value;
            touch(n);
            if (n.level > 0)
                moveToLevel(n, 0);
        }
        rebalance();
    }

    private void touch(Node n) {
        n.freq++;
        n.lastAccess = ++clock;
        n.version++;
        pushToHeap(n);
    }

    private long score(Node n) {
        long s = W_FREQ * (long) n.freq + W_TIME * n.lastAccess - W_COST * accessCost[n.level];
        return s;
    }

    private void pushToHeap(Node n) {
        long pr = score(n);
        levelHeaps.get(n.level).offer(new Entry(pr, n.key, n.version));
    }

    private void moveToLevel(Node n, int newLevel) {
        if (newLevel == n.level) {
            pushToHeap(n);
            return;
        }
        levelMembers.get(n.level).remove(n.key);
        n.level = newLevel;
        n.version++;
        levelMembers.get(n.level).add(n.key);
        pushToHeap(n);
    }

    private Node popVictim(int level) {
        PriorityQueue<Entry> pq = levelHeaps.get(level);
        while (!pq.isEmpty()) {
            Entry e = pq.poll();
            Node n = map.get(e.key);
            if (n == null)
                continue;
            if (n.level != level)
                continue;
            if (n.version != e.version)
                continue;
            return n;
        }
        return null;
    }

    private void rebalance() {
        for (int lv = 0; lv < LEVELS; lv++) {
            while (levelMembers.get(lv).size() > cap[lv]) {
                Node victim = popVictim(lv);
                if (victim == null)
                    break;
                levelMembers.get(lv).remove(victim.key);
                if (lv + 1 < LEVELS) {
                    victim.level = lv + 1;
                    victim.version++;
                    levelMembers.get(victim.level).add(victim.key);
                    pushToHeap(victim);
                } else {
                    map.remove(victim.key);
                }
            }
        }
    }

    public String debugLevels() {
        StringBuilder sb = new StringBuilder();
        for (int lv = 0; lv < LEVELS; lv++) {
            List<Integer> keys = new ArrayList<>(levelMembers.get(lv));
            Collections.sort(keys);
            sb.append("L").append(lv + 1).append(": ").append(keys).append(lv + 1 < LEVELS ? " | " : "");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        MultiLevelCacheSystem cache = new MultiLevelCacheSystem();
        System.out.println("初始化：" + cache.debugLevels());
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        System.out.println("put(1,2,3) → " + cache.debugLevels());
        cache.get(1);
        cache.get(1);
        cache.get(2);
        System.out.println("get(1),get(1),get(2) → " + cache.debugLevels());
        cache.put(4, "D");
        cache.put(5, "E");
        cache.put(6, "F");
        System.out.println("put(4,5,6) → " + cache.debugLevels());
        cache.get(3);
        cache.get(3);
        cache.get(3);
        cache.get(6);
        cache.get(6);
        System.out.println("更多存取後 → " + cache.debugLevels());
    }
}
