package com.problems.learning.ds.traversal;

import com.problems.learning.ds.traversal.model.TreeNode;

import java.util.List;

public interface Traversal<T> {

    List<T> traverse(TreeNode<T> root);
}
