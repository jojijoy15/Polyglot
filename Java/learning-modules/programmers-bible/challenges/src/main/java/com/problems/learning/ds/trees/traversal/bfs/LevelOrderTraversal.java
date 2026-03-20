package com.problems.learning.ds.trees.traversal.bfs;

import com.problems.learning.ds.trees.Traversal;
import com.problems.learning.ds.trees.model.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LevelOrderTraversal implements Traversal<Integer> {


    @Override
    public List<Integer> traverse(TreeNode<Integer> root) {
        return levelOrder(root, new ArrayList<>());
    }

    //Le Ro Ri
    private List<Integer> levelOrder(TreeNode<Integer> root, List<Integer> list) {
        Queue<TreeNode<Integer>> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            TreeNode<Integer> node = queue.poll();
            list.add(node.getData());
            if(node.getLeft() != null)  queue.add(node.getLeft());
            if(node.getRight() != null) queue.add(node.getRight());
        }
        return list;
    }
}
