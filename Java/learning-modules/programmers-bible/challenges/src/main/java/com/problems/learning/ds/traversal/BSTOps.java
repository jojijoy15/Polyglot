package com.problems.learning.ds.traversal;

import com.problems.learning.ds.traversal.model.TreeNode;

public class BSTOps {

    public <T> int height(TreeNode<T> root) {
        if (root == null) return 0;
        int leftHeight = height(root.getLeft());
        int rightHeight = height(root.getRight());
        return 1 + Math.max(leftHeight, rightHeight);
    }

    public <T> int maxDepth(TreeNode<T> root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(maxDepth(root.getLeft()), maxDepth(root.getRight()));
    }

    public boolean search(TreeNode<Integer> root, int target) {
        if (root == null) {
            return false;
        }
        if (target > root.getData()) {
            return search(root.getRight(), target);
        } else if (target < root.getData()) {
            return search(root.getRight(), target);
        } else {
            return true;
        }
    }

    public TreeNode<Integer> insert(TreeNode<Integer> root, int val) {
        if (root == null) {
            return new TreeNode<>(val, null, null);
        }
        if (val > root.getData()) {
            root.setRight(insert(root.getRight(), val));
        } else  if (val < root.getData()) {
            root.setLeft(insert(root.getLeft(), val));
        }
        return root;
    }

    public <T> TreeNode<T> minValueNode(TreeNode<T> root) {
        TreeNode<T> curr = root;
        while(curr != null && curr.getLeft() != null) {
            curr = curr.getLeft();
        }
        return curr;
    }

    // Remove a node and return the root of the BST.
    public TreeNode<Integer> remove(TreeNode<Integer> root, int val) {
        if (root == null) {
            return null;
        }
        if (val > root.getData()) {
            root.setRight(remove(root.getRight(), val));
        } else if (val < root.getData()) {
            root.setLeft(remove(root.getLeft(), val));
        } else {
            if (root.getLeft() == null) { //only right child
                return root.getRight();
            } else if (root.getRight() == null) { // only left child
                return root.getLeft();
            } else {    //both node exist
                TreeNode<Integer> minNode = minValueNode(root.getRight()); //finding the min
                root.setData(minNode.getData());    // setting this node as min
                root.setRight(remove(root.getRight(), minNode.getData()));  // removing the older min i.e leaf node
            }
        }
        return root;
    }

}
