package com.shady.huffman;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Build a Huffman tree and compresses a source string consisting of `alphabet`
 * @author rlopezna
 */
public class Huffman {
    private char[] alphabet = {'a', 'b', 'c', 'd'};

    private HashMap<Character, Double> p = new HashMap<>(alphabet.length);

    public static void main(String[] args){
        Huffman h = new Huffman();
        String toCompress = "aababcabcd";
        System.out.println("Compress source: " + toCompress);
        String compressed = h.encode(toCompress);
        System.out.println("Compressed value: " + compressed);


    }

    private String encode(String source){
        char[] chars = source.toCharArray();

        PriorityQueue<Node> queue = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Double.valueOf(o1.p).compareTo(o2.p);
            }
        });

        for(Character c : chars){
            Double occurrences = p.get(c);
            if(occurrences == null) occurrences = 0d;
            p.put(c, occurrences+1);
        }

        int l = source.length();

        //Save every symbol of alphabet with its probability of occurrence into priority queue
        p.forEach((k,v) -> {
            Node n = new Node();
            n.value = k;
            n.p = v/l;
            queue.add(n);
            System.out.println("Key: " + k + " value " + v/l);
        });
        Node root = buildHuffmanTree(queue);

        return compressWithTree(root, source);
    }

    private Node buildHuffmanTree(PriorityQueue<Node> q){
        Node n1 = q.poll();
        Node n2 = q.poll();
        Node newNode = new Node();

        while(n1 != null && n2 != null) {
            //Generate new node with probabilities summed up
            newNode = new Node();
            newNode.p = n1.p + n2.p;
            newNode.addChildren(n1, '0', n2, '1');
            n1.addParent(newNode, '0');
            n2.addParent(newNode, '1');

            //Enqueue again
            q.add(newNode);

            System.out.println("Generating new node with p " + newNode.p);
            n1 = q.poll();
            n2 = q.poll();
        }

        return n1;
    }

    private String compressWithTree(Node root, String source){
        StringBuilder result = new StringBuilder();
        char[] s = source.toCharArray();
        for(char c:s){
            Node foundNode = find(root,c);
            result.append(walkBack(foundNode, ""));
        }
        return result.toString();
    }

    private Node find(Node root, Character c){
        if(root == null) return null;
        if(root.value == c) return root;

        Node n1 = find(root.leftChild, c);
        Node n2 = find(root.rightChild, c);
        if(n1 != null) return n1;
        if(n2 != null) return n2;
        return null;
    }

    private String walkBack(Node n, String compressed){
        if(n.parentChar == null) return compressed;
        return walkBack(n.parent, compressed + n.parentChar);
    }

}
