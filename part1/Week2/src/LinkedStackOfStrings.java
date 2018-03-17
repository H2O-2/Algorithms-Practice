public class LinkedStackOfStrings {
    private Node first = null;

    private class Node {
        String item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void push(String item) {
        Node temp = first;
        first = new Node();
        first.item = item;
        first.next = temp;
    }

    public String pop() {
        String returnS = first.item;
        first = first.next;
        return returnS;
    }
}
