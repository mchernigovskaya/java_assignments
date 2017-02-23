package ru.spbau.mit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class TrieTest {

    @Test
    public void testSimple() {
        Trie stringSet = instance();

        assertTrue(stringSet.add("abc"));
        assertTrue(stringSet.contains("abc"));
        assertEquals(1, stringSet.size());
        assertEquals(1, stringSet.howManyStartsWithPrefix("abc"));
    }

    public static Trie instance() {
        try {
            return (Trie) Class.forName("ru.spbau.mit.TrieImpl").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Error while class loading");
    }


    @Test
    public void testAdd() {
        Trie trie = instance();
        String[] elems = {"qwwq", "AAA", "aBc", "aa", "baca", "", "CAB"};

        for (String s : elems) {
            assertTrue(trie.add(s));
        }

        for (String s : elems) {
            assertFalse(trie.add(s));
        }

        assertTrue(trie.add("qwqwAAA"));
        assertFalse(trie.add("qwqwAAA"));
        assertTrue(trie.add("qwqw"));
    }

    @Test
    public void testContains() {
        Trie trie = instance();
        String[] elems = {"aaaaaaaaaaaa", "abc", "bac", "cab", "bacabca", "abacaba", "a"};

        for (String s : elems) {
            assertTrue(trie.add(s));
        }

        for (String s : elems) {
            assertTrue(trie.contains(s));
        }

        String[] absentElems = {"qwwq", "AAA", "aBc", "aa", "baca", "", "CAB"};
        for (String s : absentElems) {
            assertFalse(trie.contains(s));
        }
    }

    @Test
    public void testPrefix() {
        Trie trie = instance();
        String[] elems = {"aaaaaaaaaaaa", "abc", "bac", "cab", "bacabca", "abacaba", "a", "AAA", "aBc", "aa", "baca", "CAB"};

        for (String s : elems) {
            assertTrue(trie.add(s));
        }

        assertEquals(6, trie.howManyStartsWithPrefix("a"));
        assertEquals(2, trie.howManyStartsWithPrefix("ab"));
        assertEquals(3, trie.howManyStartsWithPrefix("ba"));
        assertEquals(1, trie.howManyStartsWithPrefix("A"));
        assertEquals(3, trie.howManyStartsWithPrefix("bac"));
        assertEquals(12, trie.howManyStartsWithPrefix(""));
    }

    @Test
    public void testSize() {
        Trie trie = instance();
        String[] elems = {"aaaaaaaaaaaa", "abc", "bac", "cab", "bacabca", "abacaba", "a", "AAA", "aBc", "aa", "baca", "CAB"};

        assertFalse(trie.remove("a"));
        assertEquals(0, trie.size());
        int size = 0;
        for (String s : elems) {
            assertTrue(trie.add(s));
            size++;
            assertEquals(size, trie.size());
        }

        for (String s : elems) {
            assertTrue(trie.remove(s));
            size--;
            assertEquals(size, trie.size());
        }
        assertFalse(trie.remove("a"));
    }

    @Test
    public void testRemove() {
        Trie trie = instance();
        String[] elems = {"aaaaaaaaaaaa", "abc", "bac", "cab", "bacabca", "abacaba", "a", "AAA", "aBc", "aa", "baca", "CAB"};

        for (String s : elems) {
            assertTrue(trie.add(s));
        }

        for (String s : elems) {
            assertTrue(trie.remove(s));
            assertFalse(trie.contains(s));
        }
    }

    @Test
    public void typicalTest() {
        Trie trie = instance();
        assertTrue(trie.add("abc"));
        assertTrue(trie.contains("abc"));
        assertFalse(trie.contains("abcc"));
        assertTrue(trie.remove("abc"));
        assertFalse(trie.contains("abc"));
        assertTrue(trie.add("ABC"));
        assertTrue(trie.add("A"));
        assertTrue(trie.add("baca"));
        assertEquals(3, trie.size());
        assertEquals(2, trie.howManyStartsWithPrefix("A"));
        assertEquals(1, trie.howManyStartsWithPrefix("baca"));

        String[] elems = {"a", "aaa", "baaa", "ababa", "babab", "b", "ccccc", "Abc", "aBc", "aa", "cab"};
        for (String s : elems) {
            assertTrue(trie.add(s));
        }

        assertEquals(14, trie.size());
        assertEquals(2, trie.howManyStartsWithPrefix("c"));
        assertTrue(trie.remove("ccccc"));
        assertEquals(1, trie.howManyStartsWithPrefix("c"));
        assertTrue(trie.remove("cab"));
        assertEquals(0, trie.howManyStartsWithPrefix("c"));
    }

}