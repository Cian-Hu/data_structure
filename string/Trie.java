package String;

public class Trie {

    private Node root = new Node('/');

    public void insert(char[] txt) {
        Node curr = root;
        for (int i = 0; i < txt.length; i++) {
            int loc = txt[i] - 'a';
            if (curr.childs[loc] == null) {
                curr.childs[loc] = new Node(txt[i]);
            }
            curr = curr.childs[loc];
        }
        curr.isEnd = true;
    }

    public boolean find(char[] txt) {
        Node curr = root;
        for (int i = 0; i < txt.length; i++) {
            int loc = txt[i] - 'a';
            if(curr.childs[loc] == null)
                return false;
            else
                curr = curr.childs[loc];
        }
        return curr.isEnd;
    }

    class Node {
        char data;
        Node[] childs;
        boolean isEnd;

        Node(char ch) {
            this.data = ch;
            this.isEnd = false;
            this.childs = new Node[26];
        }
    }
}
