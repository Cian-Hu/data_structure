package String;

public class RabinKarp {
    public static void main(String[] args) {
        System.out.println(rabinKarp("cecabcab", "cabcab"));
    }

    /**
     * Rabin-Karp字符串匹配算法
     *
     * @param txt     主串
     * @param pattern 模式串
     * @return 第一个在主串中出现的模式串的起始位置
     */
    public static int rabinKarp(String txt, String pattern) {
        if (txt == null || txt.isEmpty() || pattern == null || pattern.isEmpty())
            return -1;
        char[] txts = txt.toCharArray();
        char[] patterns = pattern.toCharArray();
        int pval = 0;
        int tval = 0;
        int h = 1;
        //256进制，101为模数
        for (int i = 0; i < patterns.length - 1; i++) {
            h = (h * 256) % 101;
        }
        for (int i = 0; i < patterns.length; i++) {
            pval = (pval * 256 + patterns[i]) % 101;
            tval = (tval * 256 + txts[i]) % 101;
        }
        for (int i = 0; i <= txts.length - patterns.length; i++) {
            if (pval == tval) {
                //哈希值相同，再次比较字符串本身
                int j = 0;
                while (j < patterns.length && txts[i + j] == patterns[j])
                    j++;
                if (j == patterns.length)
                    return i;
            }
            if (i == txts.length - patterns.length)
                return -1;
            //根据之前计算得到的哈希值，计算新子串的哈希值
            tval = ((tval - txts[i] * h) * 256 + txts[i + patterns.length]) % 101;
            if (tval < 0) {
                tval += 101;
            }
        }
        return -1;
    }
}
