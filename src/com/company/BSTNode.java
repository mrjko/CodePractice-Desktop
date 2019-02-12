package com.company;

import java.util.Stack;

public class BSTNode {

    private int data;
    public BSTNode left = null;
    public BSTNode right = null;
    private int size = 0;

    public BSTNode(int val) {
        data = val;
        size = 1;
    }

    public int size() { return size; }

    public BSTNode remove(BSTNode node, int val) {

        if (node == null) return null;

        if (node.data < val) {
            node.right = remove(node.right, val);
        } else if (node.data > val) {
            node.left = remove(node.left, val);
        } else {
            if (node.left == null || node.right == null) {
                BSTNode temp = null;
                temp = (node.left == null) ? node.right : node.left;

                if (temp == null) { // this is used if there is nothing below the removed node
                    return null;
                } else { // if there are, this is used to replace the removed node
                    return node;
                }

            } else {
                BSTNode successor = findMin(node);
                node.data = successor.data;
                node.right = remove(node, successor.data);
            }
        }

        return node;
    }

    private BSTNode findMin(BSTNode root) {
        if (root == null) return null;

        BSTNode temp = root;

        while (temp.left != null) {
            temp = temp.left;
        }

        return temp;
    }

    public void insert(int val) {
        if (data >= val) {
            if (left == null) {
                left = new BSTNode(val);
            } else {
                left.insert(val);
            }
        } else {
            if (right == null) {
                right = new BSTNode(val);
            } else {
                right.insert(val);
            }
        }
        size++;
    }



    public void print(String type) {
        switch (type) {
            case "in":
                inOrderPrint();
                break;

            case "pre":
                preOrderPrint();
                break;

            case "post":
                postOrderPrint();
                break;

            default:
                preOrderPrint();
        }
    }

    private void inOrderPrint() {
        if (left != null) {
            left.inOrderPrint();
        }
        System.out.print(data + " ");
        if (right != null) {
            right.inOrderPrint();
        }
    }

    private void preOrderPrint() {
        System.out.print(data + " ");
        if (left != null) {
            left.preOrderPrint();
        }
        if (right != null) {
            right.preOrderPrint();
        }
    }

    private void postOrderPrint() {
        if (left != null) {
            left.postOrderPrint();
        }
        if (right != null) {
            right.postOrderPrint();
        }
        System.out.print(data + " ");
    }

}
