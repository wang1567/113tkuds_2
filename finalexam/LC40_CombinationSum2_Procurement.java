package finalexam;

import java.util.*;

public class LC40_CombinationSum2_Procurement {
    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        backtrack(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(int[] candidates, int remaining, int start,
            List<Integer> current, List<List<Integer>> result) {
        if (remaining == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > remaining)
                break;
            if (i > start && candidates[i] == candidates[i - 1])
                continue; // 跳過重複

            current.add(candidates[i]);
            backtrack(candidates, remaining - candidates[i], i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int target = scanner.nextInt();

        int[] candidates = new int[n];
        for (int i = 0; i < n; i++) {
            candidates[i] = scanner.nextInt();
        }

        List<List<Integer>> result = combinationSum2(candidates, target);
        for (List<Integer> combination : result) {
            for (int i = 0; i < combination.size(); i++) {
                if (i > 0)
                    System.out.print(" ");
                System.out.print(combination.get(i));
            }
            System.out.println();
        }

        scanner.close();
    }
}
