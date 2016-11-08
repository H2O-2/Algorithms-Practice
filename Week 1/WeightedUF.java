public class WeightedUF {
    private int[] id;
    private int[] size;
    private int count;

    public WeightedUF(int N) {
        count = N;
        id = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            size[i] = 1;
        }
    }

    private int find(int n) {
        while (id[n] != n) n = id[n];
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
