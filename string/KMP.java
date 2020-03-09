package String;

public class KMP {

    private static int[] D;

    public static void main(String[] args) {
        char[] txt = {'D', 'A', 'A', 'B', 'C', 'E', 'A', 'A', 'A', 'A', 'B', 'C'};
        char[] pattern = {'A', 'A', 'A', 'A', 'B'};
        System.out.println(kmp(txt, pattern));
    }


    /**
     * KMP字符串匹配算法
     *
     * @param txt     主串
     * @param pattern 模式串
     * @return 主串中第一个与模式串相等的子串的起始位置
     */
    private static int kmp(char[] txt, char[] pattern) {

        if (txt == null || pattern == null || pattern.length == 0 || txt.length < pattern.length) return -1;

        generateD(pattern);
        int i = 0;
        int j = 0;
        while (i < txt.length) {
            //匹配成功
            if (j == pattern.length) return i - j;
            if (txt[i] == pattern[j]) {
                i++;
                j++;
            } else {
                if (j > 0)
                    j = D[j - 1];
                else {
                    i++;
                }
            }
        }

        return -1;
    }

    private static void generateD(char[] pattern) {

        //D数组，用于保存最长公共前后缀长度
        //D[i]表示 模式串pattern的前缀pattern[0]~pattern[i] 的最长公共前后缀长度
        D = new int[pattern.length];
        int i = 1;//后缀
        int j = 0;//前缀
        //D[0] = 0;最长公共前后缀不包含本身，所以为0，初始化的时候自动为0
        while (i < pattern.length) {
            if (pattern[i] == pattern[j]) {
                D[i++] = ++j;
            } else {
                if (j > 0)
                    j = D[j - 1];
                else
                    i++;
            }
        }
    }
}
