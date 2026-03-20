package com.problems.learning.ds.trees;

import com.problems.learning.ds.trees.model.TreeNode;

import java.util.List;

public interface Traversal<T> {

    List<T> traverse(TreeNode<T> root);
}
