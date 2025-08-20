package midterm;

import java.util.*;

public class M12_MergeKTimeTables {
    static class TimeEntry {
        int time;
        int listIndex;
        int timeIndex;

        TimeEntry(int time, int listIndex, int timeIndex) {
            this.time = time;
            this.listIndex = listIndex;
            this.timeIndex = timeIndex;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();

        List<List<Integer>> timeLists = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            int len = scanner.nextInt();
            List<Integer> timeList = new ArrayList<>();
            for (int j = 0; j < len; j++) {
                timeList.add(scanner.nextInt());
            }
            timeLists.add(timeList);
        }

        List<Integer> merged = mergeKTimeTables(timeLists);

        for (int i = 0; i < merged.size(); i++) {
            System.out.print(merged.get(i));
            if (i < merged.size() - 1)
                System.out.print(" ");
        }
        System.out.println();

        scanner.close();
    }

    private static List<Integer> mergeKTimeTables(List<List<Integer>> timeLists) {
        List<Integer> result = new ArrayList<>();

        PriorityQueue<TimeEntry> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a.time, b.time));

        for (int i = 0; i < timeLists.size(); i++) {
            if (!timeLists.get(i).isEmpty()) {
                minHeap.offer(new TimeEntry(timeLists.get(i).get(0), i, 0));
            }
        }

        while (!minHeap.isEmpty()) {
            TimeEntry entry = minHeap.poll();
            result.add(entry.time);

            int nextIndex = entry.timeIndex + 1;
            if (nextIndex < timeLists.get(entry.listIndex).size()) {
                int nextTime = timeLists.get(entry.listIndex).get(nextIndex);
                minHeap.offer(new TimeEntry(nextTime, entry.listIndex, nextIndex));
            }
        }

        return result;
    }
}
