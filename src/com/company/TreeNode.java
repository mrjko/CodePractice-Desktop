package com.company;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;
    public TreeNode(int x) { val = x; }

    // print in
    public void print() {
        preOrderPrint(this);
    }

    public void print(String type) {
        switch (type) {
            case "preorder":
                preOrderPrint(this);
                break;
            case "inorder":
                inOrderPrint(this);
                break;
            case "postorder":
                postOrderPrint(this);
                break;
            case "levelorder":
                levelOrderPrint(this);
                break;
            default:
                preOrderPrint(this);
        }
    }

    private void levelOrderPrint(TreeNode n) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(n);

        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            System.out.print(curr.val + " ");
            if (curr.left != null) {
                queue.add(curr.left);
            }
            if (curr.right != null) {
                queue.add(curr.right);
            }
        }

    }

    private void inOrderPrint(TreeNode n) {
        System.out.print(n.val + " ");
        if (n.left != null) {
            inOrderPrint(n.left);
        }
        if (n.right != null) {
            inOrderPrint(n.right);
        }
    }

    private void postOrderPrint(TreeNode n) {
        if (n.left != null) {
            inOrderPrint(n.left);
        }
        if (n.right != null) {
            inOrderPrint(n.right);
        }
        System.out.print(n.val + " ");
    }

    private void preOrderPrint(TreeNode n) {
        System.out.print(n.val + " ");
        if (n.left != null) {
            preOrderPrint(n.left);
        }
        if (n.right != null) {
            preOrderPrint(n.right);
        }
    }


}
