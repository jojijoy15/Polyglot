package com.problems.learning.ds.traversal.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TreeNode<T>  {

    TreeNode<T> left;
    TreeNode<T> right;
    T data;

    public TreeNode(T data, TreeNode<T> left, TreeNode<T> right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

}

