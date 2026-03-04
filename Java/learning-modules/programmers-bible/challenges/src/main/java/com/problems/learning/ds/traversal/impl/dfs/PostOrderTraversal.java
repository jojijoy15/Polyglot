package com.problems.learning.ds.traversal.impl.dfs;

import com.problems.learning.ds.traversal.Traversal;
import com.problems.learning.ds.traversal.model.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class PostOrderTraversal implements Traversal<Integer> {

    @Override
    public List<Integer> traverse(TreeNode<Integer> root) {
        return postOrder(root, new ArrayList<>());
    }

    //Le Ri Ro
    private List<Integer> postOrder(TreeNode<Integer> root, List<Integer> list) {
        if(root == null)
            return list;

        if(root.getLeft() != null) {
            postOrder(root.getLeft(), list);
        }
        if(root.getRight() != null) {
            postOrder(root.getRight(), list);
        }
        list.add(root.getData());
        return list;
    }
}
