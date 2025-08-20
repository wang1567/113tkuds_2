package midterm;

import java.util.*;

public class M04_TieredTaxSimple {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        int[] incomes = new int[n];
        long totalTax = 0;

        for (int i = 0; i < n; i++) {
            incomes[i] = scanner.nextInt();
            int tax = calculateTax(incomes[i]);
            System.out.println("Tax: " + tax);
            totalTax += tax;
        }

        int average = (int) (totalTax / n);
        System.out.println("Average: " + average);

        scanner.close();
    }

    private static int calculateTax(int income) {
        double tax = 0.0;

        
        if (income > 0) {
            int taxableInThisBracket = Math.min(income, 120000);
            tax += taxableInThisBracket * 0.05;
        }

        if (income > 120000) {
            int taxableInThisBracket = Math.min(income - 120000, 380000);
            tax += taxableInThisBracket * 0.12;
        }

        if (income > 500000) {
            int taxableInThisBracket = Math.min(income - 500000, 500000);
            tax += taxableInThisBracket * 0.20;
        }

        if (income > 1000000) {
            int taxableInThisBracket = income - 1000000;
            tax += taxableInThisBracket * 0.30;
        }

        return (int) tax;
    }
}

/*
 * Time Complexity: O(n)
 * 說明：對每個收入值進行固定次數(4次)的級距計算，每次計算為O(1)
 * 總共n個收入值，所以總複雜度為O(n)
 */
