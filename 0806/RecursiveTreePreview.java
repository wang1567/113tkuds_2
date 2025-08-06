import java.util.*;

public class RecursiveTreePreview {

    // 模擬資料夾結構
    static class Folder {
        String name;
        List<Folder> subFolders = new ArrayList<>();
        int fileCount;

        Folder(String name, int fileCount) {
            this.name = name;
            this.fileCount = fileCount;
        }

        void addSubFolder(Folder folder) {
            subFolders.add(folder);
        }
    }

    // 遞迴計算總檔案數
    public static int countFiles(Folder folder) {
        int total = folder.fileCount;
        for (Folder sub : folder.subFolders) {
            total += countFiles(sub);
        }
        return total;
    }

    // 遞迴列印多層選單結構
    public static void printMenu(Folder folder, String indent) {
        System.out.println(indent + folder.name + " (" + folder.fileCount + " files)");
        for (Folder sub : folder.subFolders) {
            printMenu(sub, indent + "  ");
        }
    }

    // 遞迴展平巢狀陣列
    public static List<Integer> flatten(List<Object> nested) {
        List<Integer> result = new ArrayList<>();
        for (Object obj : nested) {
            if (obj instanceof Integer) {
                result.add((Integer) obj);
            } else if (obj instanceof List) {
                result.addAll(flatten((List<Object>) obj));
            }
        }
        return result;
    }

    // 遞迴計算巢狀清單最大深度
    public static int maxDepth(List<Object> nested) {
        int depth = 1;
        for (Object obj : nested) {
            if (obj instanceof List) {
                depth = Math.max(depth, 1 + maxDepth((List<Object>) obj));
            }
        }
        return depth;
    }

    public static void main(String[] args) {
        // 測試資料夾結構
        Folder root = new Folder("Root", 2);
        Folder sub1 = new Folder("Sub1", 3);
        Folder sub2 = new Folder("Sub2", 1);
        Folder sub11 = new Folder("Sub1-1", 4);
        sub1.addSubFolder(sub11);
        root.addSubFolder(sub1);
        root.addSubFolder(sub2);

        System.out.println("總檔案數：" + countFiles(root));
        System.out.println("選單結構：");
        printMenu(root, "");

        // 測試巢狀陣列
        List<Object> nestedList = Arrays.asList(1, Arrays.asList(2, Arrays.asList(3, 4), 5), 6);
        System.out.println("展平後：" + flatten(nestedList));
        System.out.println("最大深度：" + maxDepth(nestedList));
    }
}
