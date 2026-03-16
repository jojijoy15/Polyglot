package com.problems.learning.ds.traversal.problems;

import com.problems.learning.ds.traversal.model.TreeNode;

public class BinaryTreeOperations {

     public <T> TreeNode<T> invert(TreeNode<T> node) {
        if(node == null)
            return null;
        TreeNode<T> temp = node.getLeft();
        node.setLeft(node.getRight());
        node.setRight(temp);
        invert(node.getRight());
        invert(node.getLeft());
        return node;
     }
}
