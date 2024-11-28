package com.renjia.blog.util;

import com.renjia.blog.util.other.Node;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * trie tree searcher
 * 前缀书工具类
 */
@Component
public class TrieSearcherUtil {

    private Node root = new Node();

    /**
     * 添加敏感词词
     *
     * @param word 词
     */
    public void addWord(String word) {
        Node tmpNode = root;
        for (char c : word.toCharArray()) {
            Node node = tmpNode.getNext(c);
            if (null == node) {
                node = new Node();
                tmpNode.addNext(c, node);
            }
            tmpNode = node;
        }
    }

    /**
     * 替换词
     *
     * @param text         待处理文本
     * @param afterReplace 替换后的词
     * @return 处理后的文本
     */
    public String replace(String text, String afterReplace) {
        StringBuilder result = new StringBuilder(text.length());
        Node tmpNode = root;
        int begin = 0, pos = 0;
        while (pos < text.length()) {
            char c = text.charAt(pos);
            tmpNode = tmpNode.getNext(c);
            if (null == tmpNode) {
                result.append(text.charAt(begin));
                begin++;
                pos = begin;
                tmpNode = root;
            } else if (tmpNode.isLastCharacter()) {
                // 匹配完成, 进行替换
                result.append(afterReplace);
                pos++;
                begin = pos;
                tmpNode = root;
            } else {
                // 匹配上向后移
                pos++;
            }
        }
        result.append(text.substring(begin));
        return result.toString();
    }

    /**
     * 查找
     *
     * @param text 待处理文本
     * @return 统计数据 key: word value: count
     */
    public Map<String, Integer> find(String text) {
        Map<String, Integer> resultMap = new HashMap<>(16);
        Node tmpNode = root;
        StringBuilder word = new StringBuilder();
        int begin = 0, pos = 0;
        while (pos < text.length()) {
            char c = text.charAt(pos);
            tmpNode = tmpNode.getNext(c);
            if (null == tmpNode) {
                begin++;
                pos = begin;
                tmpNode = root;
            } else if (tmpNode.isLastCharacter()) {
                // 匹配完成
                String w = word.append(c).toString();
                resultMap.put(w, resultMap.getOrDefault(w, 0) + 1);
                pos++;
                begin = pos;
                tmpNode = root;
                word = new StringBuilder();
            } else {
                // 匹配上向后移
                word.append(c);
                pos++;
            }
        }
        return resultMap;
    }
}
