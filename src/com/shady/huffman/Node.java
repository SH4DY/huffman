package com.shady.huffman;

import java.util.HashMap;

/**
 * created on 06/05/16
 *
 * @author rlopezna
 */
public class Node {

    double p;

    //If set, THIS is a leaf node
    Character value;

    Node parent;
    Character parentChar = null;

    Node leftChild;
    Character leftChar = null;

    Node rightChild;
    Character rightChar = null;

    public void addChildren(Node n1, Character c1, Node n2, Character c2){
        this.leftChild = n1;
        this.leftChar = c1;

        this.rightChild = n2;
        this.rightChar = c2;
    }

    public void addParent(Node p, Character c) {
        this.parent = p;
        this.parentChar = c;
    }

}
