public class WeightedCompressUF {
    private int[] id;
    private int[] size;

    public WeightedCompressUF(int N) {
        id = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            size[i] = 1;
        }
    }

    // 此处文章 普林斯顿算法（1.4）quick-union算法的优化 中给出的解答不完善 文中while(p != id[p]) 和 while(orgp != id[orgp])
    // 两个条件判断实际上完全相同，可以合并
    private int find(int n) {
        int temp = n;
        while (id[n] != n) {
            n = id[n];
            id[temp] = id[n];
        }
        return n;
    }

    public void union(int p, int q){
        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot == qRoot) {
            return;
        }

        if (size[pRoot] > size[qRoot]) {
            id[qRoot] = pRoot;
            size[pRoot] += size[qRoot];
        } else {
            id[pRoot] = qRoot;
            size[qRoot] += size[pRoot];
        }
    }

    public boolean connected(int p, int q){
        return find(p) == find(q);
    }
}
