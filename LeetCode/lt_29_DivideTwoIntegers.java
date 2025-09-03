public class lt_29_DivideTwoIntegers {
    public int divide(int dividend, int divisor) {
        // Handle special cases
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE; // Overflow case
        }

        // Determine the sign of the result
        int sign = (dividend < 0) ^ (divisor < 0) ? -1 : 1;

        // Convert to long and take absolute values to avoid overflow
        long ldividend = Math.abs((long) dividend);
        long ldivisor = Math.abs((long) divisor);

        // Initialize quotient
        long quotient = 0;

        // Subtract divisor from dividend repeatedly
        while (ldividend >= ldivisor) {
            long temp = ldivisor;
            long multiple = 1;

            // Double the divisor until it's just smaller than dividend
            while (ldividend >= (temp << 1)) {
                temp <<= 1;
                multiple <<= 1;
            }

            ldividend -= temp;
            quotient += multiple;
        }

        // Apply sign and handle 32-bit integer overflow
        quotient = sign * quotient;
        if (quotient > Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        if (quotient < Integer.MIN_VALUE)
            return Integer.MIN_VALUE;

        return (int) quotient;
    }
}

/*
 * 解題邏輯與思路：
 * 
 * 這道題目要求在不使用乘法、除法和取模運算的情況下，實現兩個整數的除法，結果向零截斷（即捨棄小數部分），並需考慮 32 位整數範圍的限制（[-2^31,
 * 2^31 - 1]）。我們使用位運算和減法來模擬除法過程，採用「快速除法」技術以提高效率。
 * 
 * 具體邏輯如下：
 * 
 * 1. **處理特殊情況**：
 * - 如果 dividend 為 Integer.MIN_VALUE 且 divisor 為 -1，結果會溢出（因為 |Integer.MIN_VALUE|
 * = 2^31，而 Integer.MAX_VALUE = 2^31 - 1），直接返回 Integer.MAX_VALUE。
 * 
 * 2. **確定結果的符號**：
 * - 使用異或運算 (dividend < 0) ^ (divisor < 0) 判斷結果的符號：如果 dividend 和 divisor
 * 符號相同，結果為正；否則為負。
 * 
 * 3. **轉為長整型並取絕對值**：
 * - 將 dividend 和 divisor 轉為 long 類型並取絕對值，以避免整數溢出問題。
 * 
 * 4. **快速除法（位運算優化）**：
 * - 使用減法模擬除法：不斷從 ldividend 中減去 ldivisor，直到 ldividend 小於 ldivisor。
 * - 為了加速，使用位運算來快速找到最大的 ldivisor 倍數（通過左移操作實現倍增）。
 * - 在每次循環中：
 * - 初始化 temp = ldivisor 和 multiple = 1。
 * - 只要 ldividend >= temp << 1，就將 temp 和 multiple 左移（即 temp 和 multiple 翻倍）。
 * - 當無法再翻倍時，從 ldividend 中減去當前的 temp，並將對應的 multiple 加到 quotient 上。
 * - 重複此過程，直到 ldividend 小於 ldivisor。
 * 
 * 5. **處理符號和溢出**：
 * - 將 quotient 乘以符號 sign。
 * - 檢查結果是否超出 32 位整數範圍，若超出則返回 Integer.MAX_VALUE 或 Integer.MIN_VALUE。
 * - 最後將 quotient 轉為 int 返回。
 ** 
 * 時間複雜度**：O(log n * log n)，其中 n 是 dividend 的絕對值。外層循環執行 O(log n) 次（因為每次至少將
 * ldividend 減半），內層位運算循環最多執行 O(log n) 次。
 ** 空間複雜度**：O(1)，僅使用常數級別的額外空間。
 * 
 * 這種方法通過位運算優化了減法過程，避免了直接使用除法或乘法，同時正確處理了符號和溢出問題，符合題目要求。
 */