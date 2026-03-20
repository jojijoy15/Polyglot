package com.problems.learning.ds.trees;

import com.problems.learning.ds.trees.model.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RootToLeaf {

    public List<List<Integer>> pathsToLeaf(TreeNode<Integer> root) {
        if(null == root){
            return Collections.emptyList();
        }
        List<Integer> path = new ArrayList<>();
        List<List<Integer>> container = new ArrayList<>();
        pathCollector(root, path, container);
        return container;
    }

    //Le Ro Ri
    private void pathCollector(TreeNode<Integer> root, List<Integer> path, List<List<Integer>> container) {
        if(null == root)
            return;
        path.add(root.getData());

        if(root.getLeft() == null && root.getRight() == null){
            container.add(new ArrayList<>(path));
        } else {
            pathCollector(root.getLeft(), path, container);
            pathCollector(root.getRight(), path, container);
        }
        path.remove(path.size()-1);
    }
}
