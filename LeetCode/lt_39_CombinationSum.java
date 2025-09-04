import java.util.*;

public class lt_39_CombinationSum {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int[] candidates, int target, int start, List<Integer> current, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(current)); // Found a valid combination
            return;
        }
        if (target < 0) {
            return; // Sum exceeds target, stop this branch
        }

        for (int i = start; i < candidates.length; i++) {
            current.add(candidates[i]); // Include current candidate
            backtrack(candidates, target - candidates[i], i, current, result); // Recurse with reduced target
            current.remove(current.size() - 1); // Backtrack by removing the last candidate
        }
    }
}

/*
 * 解題邏輯與思路：
 * 
 * 這道題目要求在給定的整數陣列 candidates 中，找到所有和為目標值 target 的唯一組合。candidates
 * 中的數字可以無限次重複使用，且組合之間必須在至少一個數字的頻率上不同。所有數字均為不同，且結果數量少於 150 個組合。
 * 
 * 我們使用回溯法（Backtracking）來解決此問題，具體邏輯如下：
 * 
 * 1. **初始化結果**：
 * - 創建一個 List<List<Integer>> result 用於存儲所有有效組合。
 * - 調用回溯函數 backtrack，傳入 candidates、target、起始索引 start、當前組合 current 和結果列表 result。
 * 
 * 2. **回溯函數 backtrack**：
 * - **終止條件**：
 * - 如果 target == 0，表示當前組合的和等於目標值，將 current 複製並加入 result。
 * - 如果 target < 0，表示當前組合的和超過目標值，終止該分支。
 * - **遍歷候選數字**：
 * - 從索引 start 開始遍歷 candidates（使用 start 避免重複組合，允許當前數字重複使用）。
 * - 將當前數字 candidates[i] 加入 current。
 * - 遞迴調用 backtrack，目標值減去 candidates[i]（target - candidates[i]），並保持起始索引
 * i（允許重複使用當前數字）。
 * - 回溯：移除 current 中最後添加的數字，恢復狀態以嘗試下一個候選數字。
 * 
 * 3. **避免重複組合**：
 * - 通過參數 start 控制每次只考慮當前及之後的數字，避免生成重複的組合（例如，[2,2,3] 和 [2,3,2] 視為同一組合）。
 * - 允許同一數字重複使用（通過保持 start = i），符合題目要求。
 * 
 * 4. **返回結果**：
 * - 回溯完成後，result 包含所有和為 target 的唯一組合，返回該列表。
 ** 
 * 時間複雜度**：O(N^(T/M))，其中 N 是 candidates 的長度，T 是 target，M 是 candidates
 * 中的最小數字。回溯樹的深度最多為 T/M，每次選擇有 N 種可能。
 ** 空間複雜度**：O(T/M)，用於遞迴棧和當前組合 current 的空間，最大深度為 T/M。
 ** 
 * 示例解釋**：
 * - 對於 candidates = [2,3,6,7], target = 7：
 * - 從 2 開始：可形成 [2,2,3]（2+2+3=7）。
 * - 從 7 開始：可形成 [7]（7=7）。
 * - 結果：[[2,2,3], [7]]。
 * - 對於 candidates = [2,3,5], target = 8：
 * - 形成 [2,2,2,2]、[2,3,3]、[3,5]。
 * - 結果：[[2,2,2,2], [2,3,3], [3,5]]。
 * - 對於 candidates = [2], target = 1：
 * - 無法形成和為 1 的組合，返回 []。
 * 
 * 這種方法通過回溯法系統地探索所有可能組合，高效且正確地滿足題目要求。
 */