package midterm;

import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class M03_TopKConvenience {
    static class Item {
        String name;
        int qty;
        int order;

        Item(String name, int qty, int order) {
            this.name = name;
            this.qty = qty;
            this.order = order;
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

        PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8), true);

        String[] firstLine = reader.readLine().split(" ");
        int n = Integer.parseInt(firstLine[0]);
        int k = Integer.parseInt(firstLine[1]);

        List<Item> items = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String line = reader.readLine().trim();
            String[] parts = line.split(" ");
            String name = parts[0];
            int qty = Integer.parseInt(parts[1]);
            items.add(new Item(name, qty, i));
        }

        PriorityQueue<Item> minHeap = new PriorityQueue<>((a, b) -> {
            if (a.qty != b.qty)
                return Integer.compare(a.qty, b.qty);
            return Integer.compare(b.order, a.order);
        });

        for (Item item : items) {
            if (minHeap.size() < k) {
                minHeap.offer(item);
            } else if (item.qty > minHeap.peek().qty) {
                minHeap.poll();
                minHeap.offer(item);
            }
        }

        List<Item> result = new ArrayList<>(minHeap);
        result.sort((a, b) -> {
            if (a.qty != b.qty)
                return Integer.compare(b.qty, a.qty);
            return Integer.compare(a.order, b.order);
        });

        for (Item item : result) {
            writer.println(item.name + " " + item.qty);
        }

        writer.flush();
        reader.close();
        writer.close();
    }
}

/*
 * Time Complexity: O(n log k)
 * 說明：維護大小為k的Min-Heap，每個元素最多進出heap一次，每次操作O(log k)
 * 總共n個元素，所以總複雜度為O(n log k)，當k遠小於n時效率很高
 */
