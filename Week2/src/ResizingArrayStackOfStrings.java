public class ResizingArrayStackOfStrings {
    private String s[];
    private int N;

    public ResizingArrayStackOfStrings() {
        s = new String[1];
    }

    public void push(String item) {
        if (N == s.length) resize(2 * s.length);
        s[N++] = item;
    }

    public String pop() {
        String popItem = s[--N];
        s[N] = null;
        if (N > 0 && N == s.length/4) resize(s.length/2);
        return popItem;
    }

    private void resize(int capacity) {
        String[] newArray = new String[capacity];
        for (int i = 0; i < s.length; i++) {
            newArray[i] = s[i];
        }
        s = newArray;
    }
}
