package String;

import java.util.Arrays;

public class BoyerMoore {

    private static final int SIZE = 256;

    /**
     * 根据模式串构建坏字符表
     * 坏字符表存储根据字符计算得到的位置。
     * 若模式串中存在相同的字符，以最后一次出现的位置为准。
     *
     * @param pattern 模式串
     * @return 坏字符表
     */
    private int[] initBadCharacterTable(char[] pattern) {
        if (pattern == null || pattern.length == 0)
            return null;
        int[] table = new int[SIZE];
        Arrays.fill(table, -1);
        for (int i = 0; i < pattern.length; i++) {
            table[pattern[i]] = i;//存储当前字符在模式串中出现的位置
        }
        return table;
    }

    /**
     * 字符串匹配    BM算法
     *
     * @param txt     主串
     * @param pattern 模式串
     * @return 主串中匹配成功的第一个字符串的下标, 未匹配成功则返回-1
     */
    public int match(char[] txt, char[] pattern) {
        //构建坏字符表
        int[] badCharacterTable = initBadCharacterTable(pattern);
        boolean[] prefixTable = new boolean[pattern.length];
        int[] suffixTable = new int[pattern.length];
        //构建前缀数组、后缀数组
        generateGS(pattern, prefixTable, suffixTable);
        //当前参与匹配的主串的位置
        int i = 0;
        //坏字符规则和好后缀规则在一起计算，取较大者作为移动步数
        while (i <= txt.length - pattern.length) {
            //pattern position 当前参与匹配的模式串的位置
            int j = pattern.length - 1;
            while (j >= 0 && txt[i + j] == pattern[j])
                j--;
            //匹配成功，返回当前主串的位置
            if (j < 0) return i;
            //根据坏字符规则计算出的移动步数
            int badCharacterRuleOffset = j - badCharacterTable[txt[i + j]];
            //根据好后缀规则计算出的移动步数，如果存在好后缀的话
            int goodSuffixRuleOffset = j < pattern.length - 1 ? moveByGS(j, pattern.length, suffixTable, prefixTable) : 0;
            // 主串移动，就相当于模式串在移动
            i += Math.max(badCharacterRuleOffset, goodSuffixRuleOffset);
        }

        return -1;
    }

    /**
     * 好后缀规则下，计算得到的移动步数
     *
     * @param index  坏字符在模式串中的下标
     * @param length 模式串的长度
     * @param suffix 前缀数组
     * @param prefix 后缀数组
     * @return 好后缀规则下应的移动步数
     */
    private static int moveByGS(int index, int length, int[] suffix, boolean[] prefix) {
        // 好后缀长度
        int k = length - index - 1;
        //如果模式串中存在与好后缀相等的子串
        if (suffix[k] != -1)
            return index - suffix[k] + 1;
        for (int r = index + 2; r <= length - 1; r++) {
            if (prefix[length - r])
                return r;
        }
        return length;
    }

    /**
     * 预处理前缀数组及后缀数组
     *
     * @param pattern 模式串
     * @param prefix  前缀数组
     * @param suffix  后缀数组
     */
    private static void generateGS(char[] pattern, boolean[] prefix, int[] suffix) {

        if (pattern == null || prefix == null || suffix == null) return;

        // 初始化
        for (int i = 0; i < pattern.length; ++i) {
            prefix[i] = false;
            suffix[i] = -1;
        }

        //所有前缀
        for (int pos = 0; pos < pattern.length - 1; pos++) {
            //j--，表示从后向前匹配
            int i = pos;
            //后缀子串长度，一个长度可以唯一确定一个后缀子串
            int len = 0;
            // 判断前缀pattern[0, pos]与后缀pattern[m - len,m - 1]是否相等，同时也判断前缀子串
            //m为模式串长度，即pattern.length
            while (i >= 0 && pattern[i] == pattern[pattern.length - 1 - len])
                suffix[++len] = i--;
            //匹配到了一个前缀子串
            if (i == -1)
                prefix[len] = true;
        }
    }
}