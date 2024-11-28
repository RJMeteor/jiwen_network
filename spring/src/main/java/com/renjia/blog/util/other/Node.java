package com.renjia.blog.util.other;

import java.util.HashMap;
import java.util.Map;

/**
 * trie tree
 * 构建前缀树
 */
public class Node {
    /**
     * 子节点
     */
    private Map<Character, Node> nextNodes = new HashMap<>();

    public void addNext(Character key, Node node) {
        nextNodes.put(key, node);
    }

    public Node getNext(Character key) {
        return nextNodes.get(key);
    }

    public boolean isLastCharacter() {
        return nextNodes.isEmpty();
    }
}
