package String;

import java.util.*;

/**
 * 仅适用于英文字母的AC自动机
 */
public class AhoCorasickAutomation {
    //根结点，其fail指针为空
    public AcNode root = new AcNode('/');

    public AhoCorasickAutomation(List<String> patterns) {
        this.buildTrie(patterns);
        this.buildFailurePointer();
    }

    /**
     * Trie树与主串匹配
     *
     * @param txt 主串
     * @return 主串中所包含的所有模式串，以模式串在主串的起始位置，模式串长度的形式返回
     */
    public List<PatternInfo> match(String txt) {
        List<PatternInfo> list = new ArrayList<>();
        if (txt == null || txt.isEmpty()) return list;
        char[] chars = txt.toCharArray();
        AcNode node = this.root;
        for (int i = 0; i < chars.length; i++) {
            int loc = chars[i] - 'a';
            //当前结点不存等于chars[i]的子结点
            while (node.childs[loc] == null && node.fail != null)
                node = node.fail;
            if (node.childs[loc] != null)
                node = node.childs[loc];//存在该字符
            else
                continue;//失效指针所在结点的所有子结点也没有该字符
            //取出匹配到的模式串
            if (node.list.size() != 0) {
                for (Integer length : node.list) {
                    list.add(new PatternInfo(i - length + 1, length));
                }
            }
        }
        return list;
    }

    /**
     * 构建失效指针
     */
    private void buildFailurePointer() {
        Queue<AcNode> queue = new LinkedList();
        queue.add(this.root);
        while (!queue.isEmpty()) {
            AcNode node = queue.remove();
            for (int i = 0; i < 26; i++) {
                //当前结点
                AcNode curr = node.childs[i];
                if (curr == null)
                    continue;
                //构建失效指针
                if (node == this.root) {
                    //Trie树第二层所有结点的失效指针都指向根结点
                    curr.fail = this.root;
                } else {
                    //父结点的fail指针
                    AcNode fafail = node.fail;
                    //沿着失效指针向上搜索
                    while (fafail != null && fafail.childs[i] == null) {
                        fafail = fafail.fail;
                    }
                    if (fafail == null)
                        curr.fail = this.root;//Trie树不存在以当前字符为子结点的子结点
                    else
                        curr.fail = fafail.childs[i];//父结点不存在以当前字符为子结点的子结点
                    //结束标记处理
                    if (curr.fail.list.size() > 0)
                        curr.list.addAll(curr.fail.list);
                }
                queue.add(curr);
            }
        }
    }

    /**
     * 构建Trie树
     *
     * @param patterns 模式串集合
     */
    public void buildTrie(List<String> patterns) {
        if (patterns == null || patterns.size() == 0)
            return;
        for (String pattern : patterns)
            insert(pattern);
    }

    /**
     * 向Trie树中插入一个模式串
     *
     * @param pattern 模式串
     */
    public void insert(String pattern) {
        if (pattern == null || pattern.isEmpty())
            return;
        AcNode node = this.root;
        char[] word = pattern.toCharArray();
        for (int i = 0; i < word.length; i++) {
            int loc = word[i] - 'a';
            if (node.childs[loc] == null)
                node.childs[loc] = new AcNode(word[i]);
            node = node.childs[loc];
        }
        node.list.add(word.length);//记录长度，作为一个单词的结束标记
    }

    /**
     * AC自动机结点
     */
    private class AcNode {
        char data;//当前结点存储的字符
        AcNode[] childs;//当前结点的所有子结点
        AcNode fail;//失效指针
        List<Integer> list; //叶子结点，存储所有以data结尾的模式串的长度

        private AcNode(char ch) {
            this.data = ch;
            this.childs = new AcNode[26];
            this.list = new ArrayList<>();
            this.fail = null;
        }
    }

    public class PatternInfo {
        Integer index;
        Integer length;

        public PatternInfo(Integer index, Integer length) {
            this.index = index;
            this.length = length;
        }
    }
}
