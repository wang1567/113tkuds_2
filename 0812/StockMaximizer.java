import java.util.*;

public class StockMaximizer {
    public static int maxProfitHeap(int[] prices, int K) {
        if (prices == null || prices.length < 2 || K <= 0)
            return 0;
        int n = prices.length;
        if (K >= n / 2)
            return unlimitedTransactions(prices);
        List<Integer> profits = generateProfits(prices);
        if (profits.isEmpty())
            return 0;
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int p : profits)
            maxHeap.offer(p);
        int ans = 0;
        for (int i = 0; i < K && !maxHeap.isEmpty(); i++)
            ans += maxHeap.poll();
        return ans;
    }

    private static List<Integer> generateProfits(int[] prices) {
        int n = prices.length;
        Deque<Integer> buys = new ArrayDeque<>();
        Deque<Integer> sells = new ArrayDeque<>();
        List<Integer> profits = new ArrayList<>();
        int i = 0;
        while (i < n - 1) {
            while (i < n - 1 && prices[i] >= prices[i + 1])
                i++;
            int buy = i;
            while (i < n - 1 && prices[i] <= prices[i + 1])
                i++;
            int sell = i;
            if (sell <= buy)
                continue;
            while (!buys.isEmpty() && prices[buy] <= prices[buys.peekLast()]) {
                int b = buys.pollLast();
                int s = sells.pollLast();
                profits.add(prices[s] - prices[b]);
            }
            while (!buys.isEmpty() && prices[sell] >= prices[sells.peekLast()]) {
                int bPrev = buys.pollLast();
                int sPrev = sells.pollLast();
                profits.add(prices[sPrev] - prices[buy]);
                buy = bPrev;
            }
            buys.addLast(buy);
            sells.addLast(sell);
        }
        while (!buys.isEmpty()) {
            int b = buys.pollLast();
            int s = sells.pollLast();
            profits.add(prices[s] - prices[b]);
        }
        return profits;
    }

    private static int unlimitedTransactions(int[] prices) {
        int sum = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1])
                sum += prices[i] - prices[i - 1];
        }
        return sum;
    }

    public static int maxProfitDP(int[] prices, int K) {
        if (prices == null || prices.length < 2 || K <= 0)
            return 0;
        int n = prices.length;
        if (K >= n / 2)
            return unlimitedTransactions(prices);
        int[] hold = new int[K + 1];
        int[] cash = new int[K + 1];
        Arrays.fill(hold, Integer.MIN_VALUE / 4);
        Arrays.fill(cash, 0);
        for (int price : prices) {
            for (int k = 1; k <= K; k++) {
                hold[k] = Math.max(hold[k], cash[k - 1] - price);
                cash[k] = Math.max(cash[k], hold[k] + price);
            }
        }
        return cash[K];
    }

    public static void main(String[] args) {
        int[][] tests = new int[][] { { 2, 4, 1 }, { 3, 2, 6, 5, 0, 3 }, { 1, 2, 3, 4, 5 } };
        int[] ks = { 2, 2, 2 };
        int[] expected = { 2, 7, 4 };
        for (int t = 0; t < tests.length; t++) {
            int[] prices = tests[t];
            int K = ks[t];
            int heapAns = maxProfitHeap(prices, K);
            int dpAns = maxProfitDP(prices, K);
            System.out.println("價格: " + Arrays.toString(prices) + ", K=" + K);
            System.out.println("Heap 輔助: " + heapAns + ", DP: " + dpAns + ", 期望: " + expected[t]);
            System.out.println();
        }
    }
}
