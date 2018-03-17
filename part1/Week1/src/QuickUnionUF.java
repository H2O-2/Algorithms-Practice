/**
 * Created by peiranhong on 16/11/3.
 */
public class QuickUnionUF {
    private int[] id;
    private int count;

    public QuickUnionUF(int N) {
        count = N;
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    private int find(int n) {
        while (id[n] != n) n = id[n];
        return n;
    }

    public void union(int p, int q){
        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot != qRoot) {
            id[pRoot] = qRoot;
        }
    }

    public boolean connected(int p, int q){
        return find(p) == find(q);
    }
}
