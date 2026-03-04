package com.problems.learning.ds.traversal.impl.dfs;

import com.problems.learning.ds.traversal.Traversal;
import com.problems.learning.ds.traversal.model.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class InOrderTraversal implements Traversal<Integer> {


    @Override
    public List<Integer> traverse(TreeNode<Integer> root) {
        return inOrder(root, new ArrayList<>());
    }

    //Le Ro Ri
    private List<Integer> inOrder(TreeNode<Integer> root, List<Integer> list) {
        if(root == null)
            return list;

        if(root.getLeft() != null) {
            inOrder(root.getLeft(), list);
        }
        list.add(root.getData());
        if(root.getRight() != null) {
            inOrder(root.getRight(), list);
        }
        return list;
    }
}
