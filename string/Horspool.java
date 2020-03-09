package String;

public class Horspool {

    private final int SIZE = 256;

    /**
     * 构建滑动数组
     *
     * @param pattern
     * @return
     */
    private int[] buildSkipArray(char[] pattern) {
        if (pattern == null || pattern.length == 0)
            return null;
        int[] skipArray = new int[SIZE];
        for (int i = 0; i < skipArray.length; i++) {
            skipArray[i] = pattern.length;
        }
        for (int i = 0; i < pattern.length - 1; i++) {
            skipArray[pattern[i]] = pattern.length - 1 - i;
        }
        return skipArray;
    }

    /**
     * 基于Boyer-Moore-Horspool算法的字符串单模式匹配
     *
     * @param txt     主串
     * @param pattern 模式串
     * @return 主串中等于模式串的第一个子串的起始下标
     */
    public int match(char[] txt, char[] pattern) {
        if (txt == null || txt.length == 0 || pattern == null || pattern.length == 0)
            return -1;
        int[] skipArray = buildSkipArray(pattern);
        int i = 0;
        while (i <= txt.length - pattern.length) {
            int j = pattern.length - 1;
            while (j >= 0 && txt[i + j] == pattern[j])
                j--;
            if (j < 0)
                return i;
            i += skipArray[txt[i + pattern.length - 1]];
        }
        return -1;
    }
}
