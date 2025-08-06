public class GradeStatisticsSystem {
    public static void main(String[] args) {
        int[] scores = { 85, 92, 78, 96, 87, 73, 89, 94, 82, 90 };
        int sum = 0, max = scores[0], min = scores[0];
        int[] gradeCount = new int[5]; // A, B, C, D, F

        for (int score : scores) {
            sum += score;
            if (score > max)
                max = score;
            if (score < min)
                min = score;

            if (score >= 90)
                gradeCount[0]++;
            else if (score >= 80)
                gradeCount[1]++;
            else if (score >= 70)
                gradeCount[2]++;
            else if (score >= 60)
                gradeCount[3]++;
            else
                gradeCount[4]++;
        }

        double average = sum / (double) scores.length;
        int aboveAverage = 0;
        for (int score : scores) {
            if (score > average)
                aboveAverage++;
        }

        System.out.println("====== 成績統計報表 ======");
        System.out.printf("平均分：%.2f\n", average);
        System.out.printf("最高分：%d\n", max);
        System.out.printf("最低分：%d\n", min);
        System.out.println("-------------------------");
        System.out.println("各等第人數統計：");
        System.out.printf("A (90-100): %d 人\n", gradeCount[0]);
        System.out.printf("B (80-89): %d 人\n", gradeCount[1]);
        System.out.printf("C (70-79): %d 人\n", gradeCount[2]);
        System.out.printf("D (60-69): %d 人\n", gradeCount[3]);
        System.out.printf("F (0-59): %d 人\n", gradeCount[4]);
        System.out.println("-------------------------");
        System.out.println("高於平均分的人數：" + aboveAverage);
        System.out.println("-------------------------");

        // 新增：列出所有學生成績與等第
        System.out.println("詳細成績清單：");
        for (int i = 0; i < scores.length; i++) {
            System.out.printf("學生 %d: %3d 分 - 等第 %s\n", (i + 1), scores[i], getGrade(scores[i]));
        }
        System.out.println("=========================");
    }

    // 輔助方法：根據分數回傳等第
    private static String getGrade(int score) {
        if (score >= 90)
            return "A";
        else if (score >= 80)
            return "B";
        else if (score >= 70)
            return "C";
        else if (score >= 60)
            return "D";
        else
            return "F";
    }
}