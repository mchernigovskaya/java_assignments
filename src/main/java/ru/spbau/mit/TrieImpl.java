package ru.spbau.mit;

/**
 * Created by masha on 2/23/17.
 */

public class TrieImpl implements Trie {

    private static final int ALPHABET_SIZE = 2 * ('z' - 'a' + 1);

    private static class Node {
        private Node[] next;
        private boolean isLeaf;
        private int numberOfLeafs;

        public Node() {
            next = new Node[ALPHABET_SIZE];
            isLeaf = false;
            numberOfLeafs = 0;
        }
    }

    private int size;
    private Node root;

    public TrieImpl() {
        size = 0;
        root = new Node();
    }

    private int charToIndex(char c) {
        if (c >= 'A' && c <= 'Z') {
            return c - 'A' + 'z' - 'a' + 1;
        } else {
            return c - 'a';
        }
    }

    @Override
    public boolean add(String element) {
        if (contains(element)) {
            return false;
        } else {
            size++;
            addToNode(root, element, 0);
            return true;
        }
    }

    private void addToNode(Node currentNode, String element, int depth) {

        if (depth == element.length()) {
            currentNode.numberOfLeafs++;
            currentNode.isLeaf = true;
            return;
        }

        int nextIndex = charToIndex(element.charAt(depth));
        Node nextNode = currentNode.next[nextIndex];

        if (nextNode == null) {
            currentNode.next[nextIndex] = new Node();
            nextNode = currentNode.next[nextIndex];
        }

        currentNode.numberOfLeafs++;
        addToNode(nextNode, element, depth + 1);
    }

    @Override
    public boolean contains(String element) {
        return containsInNode(root, element, 0);
    }

    private boolean containsInNode(Node currentNode, String element, int depth) {

        if (depth == element.length()) {
            return currentNode.isLeaf;
        }

        Node nextNode = currentNode.next[charToIndex(element.charAt(depth))];

        if (nextNode == null) {
            return false;
        }
        return containsInNode(nextNode, element, depth + 1);
    }

    @Override
    public boolean remove(String element) {
        if (contains(element)) {
            removeFromNode(root, element, 0);
            return true;
        } else {
            return false;
        }

    }

    private void removeFromNode(Node currentNode, String element, int depth) {

        if (depth == element.length()) {
            size--;
            currentNode.numberOfLeafs--;
            currentNode.isLeaf = false;
            return;
        }

        Node nextNode = currentNode.next[charToIndex(element.charAt(depth))];

        currentNode.numberOfLeafs--;
        removeFromNode(nextNode, element, depth + 1);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int howManyStartsWithPrefix(String prefix) {
        return howManyStartsWithPrefixFromNode(root, prefix, 0);
    }

    private int howManyStartsWithPrefixFromNode(Node currentNode, String prefix, int depth) {

        if (depth == prefix.length()) {
            return currentNode.numberOfLeafs;
        }

        int nextIndex = charToIndex(prefix.charAt(depth));
        Node nextNode = currentNode.next[nextIndex];

        if (nextNode == null) {
            return 0;
        }

        return howManyStartsWithPrefixFromNode(nextNode, prefix, depth + 1);
    }

}
