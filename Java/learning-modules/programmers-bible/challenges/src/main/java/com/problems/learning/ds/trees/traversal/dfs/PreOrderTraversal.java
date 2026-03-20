package com.problems.learning.ds.trees.traversal.dfs;

import com.problems.learning.ds.trees.Traversal;
import com.problems.learning.ds.trees.model.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class PreOrderTraversal implements Traversal<Integer> {

    @Override
    public List<Integer> traverse(TreeNode<Integer> root) {
        return preOrder(root, new ArrayList<>());
    }

    //Ro Le Ri
    private List<Integer> preOrder(TreeNode<Integer> root, List<Integer> list) {
        if(root == null)
            return list;

        list.add(root.getData());
        if(root.getLeft() != null) {
            preOrder(root.getLeft(), list);
        }
        if(root.getRight() != null) {
            preOrder(root.getRight(), list);
        }
        return list;
    }
}
