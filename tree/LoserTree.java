
public class LoserTree {
    private int[] ls = null;//败者树的顺序表示
    private int[] tmps = null;//短暂存储当前参与比较的值
    private int k;//k路归并

    public LoserTree(int k) {
        this.k = k;
        this.ls = new int[k * 2];//k为叶节点，完全二叉树有k-1个内部结点，从1开始编号
    }

    /**
     * 初始化败者树
     *
     * @param datas 参与比较的元素数组
     */
    public void init(int... datas) {
        this.tmps = datas;
        //初始化叶结点,共有k个叶节点
        int j = 0;
        for (int i = (this.ls.length - 1) / 2 + 1; i < this.ls.length; i++) {
            ls[i] = j;
            j++;
        }
    }

    /**
     * 填充元素
     *
     * @param val 新添加的元素值
     */
    public void fill(int val) {
        this.tmps[this.ls[0]] = val;
    }

    /**
     * 单次调整
     *
     * @return 本轮胜者
     */
    public int adjust() {
        //从上一次胜者下标开始,叶结点的值永远是不变的
        int start = this.ls.length / 2 + 1 + this.ls[0];//当前结点
        int parent = start / 2;//父结点
        //上一次的胜者，留给上一层用
        int winner = this.ls[start];
        while (parent > 0) {
            //当前结点的值与父结点比较
            if (this.tmps[this.ls[winner]] > this.tmps[this.ls[parent]]) {
                int tmp = winner;
                winner = this.ls[parent];
                this.ls[parent] = tmp;
            }
            parent /= 2;
        }
        ls[0] = winner;
        return ls[0];
    }

    /**
     * 调整整棵败者树
     *
     * @param root
     * @return 上一轮比较得出的赢者
     */
    public int adjustAll(int root) {
        //数组0号下标位置存放本轮赢者
        if (root == 0) {
            this.ls[root] = adjustAll(1);
            return this.ls[root];
        }
        if (root >= this.ls.length) {
            return Integer.MAX_VALUE;
        }
        //访问左右子树
        int left = adjustAll(root * 2);
        int right = adjustAll(root * 2 + 1);
        //叶子结点
        if (left == Integer.MAX_VALUE && right == Integer.MAX_VALUE) {
            return this.ls[root];
        }
        //结点只有左孩子的情况
        if (right == Integer.MAX_VALUE) {
            this.ls[root] = Integer.MAX_VALUE;
            return left;
        }
        //一般情况，处理内部结点
        if (this.tmps[left] > this.tmps[right]) {
            this.ls[root] = left;
            return right;
        } else {
            this.ls[root] = right;
            return left;
        }
    }
}
