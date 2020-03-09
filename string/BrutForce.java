package String;

/**
 * 暴力匹配算法
 */
public class BrutForce {
    /**
     * 暴力匹配算法
     *
     * @param txt     主串
     * @param pattern 模式串
     * @return 主串中第一个匹配到的模式串的起始下标
     */
    public int match(String txt, String pattern) {
        if (txt == null || txt.isEmpty() || pattern == null || pattern.isEmpty())
            return -1;
        char[] txts = txt.toCharArray();
        char[] patterns = pattern.toCharArray();
        int i = 0;
        int j = 0;
        while (i < txts.length && j < patterns.length) {
            if (txts[i] == patterns[j]) {
                i++;
                j++;
            } else {
                i = i - j + 1;
                j = 0;
            }
        }
        return j == patterns.length ? i - j : -1;
    }
}
