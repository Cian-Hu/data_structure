package String;

public class Sunday {

    private final int SIZE = 256;

    /**
     * 根据模式串构建坏字符表
     *
     * @param pattern 模式串
     * @return 坏字符表
     */
    private int[] buildBadCharacterTable(char[] pattern) {
        if (pattern == null || pattern.length == 0)
            return null;
        int[] table = new int[SIZE];
        for (int i = 0; i < table.length; i++) {
            table[i] = -1;
        }
        for (int i = 0; i < pattern.length; i++) {
            table[pattern[i]] = i;//存储当前字符在模式串中出现的位置
        }
        return table;
    }

    /**
     * Sunday字符串匹配算法
     *
     * @param txt     主串
     * @param pattern 模式串
     * @return 主串中等于模式串的第一个子串的起始下标
     */
    public int match(char[] txt, char[] pattern) {
        if (txt == null || txt.length == 0 || pattern == null || pattern.length == 0)
            return -1;
        int[] table = buildBadCharacterTable(pattern);
        int i = 0;
        while (i <= txt.length - pattern.length) {
            int j = 0;
            while (j < pattern.length && txt[i + j] == pattern[j])
                j++;
            if (j == pattern.length)
                return i;
            i += i + pattern.length < txt.length ? pattern.length - table[txt[i + pattern.length]] : 1;
        }
        return -1;
    }
}
