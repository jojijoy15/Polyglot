package com.problems.learning.ds.trees.traversal.bfs;

import com.problems.learning.ds.trees.model.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LevelOrderTraversalTest {

    LevelOrderTraversal levelOrderTraversal = new LevelOrderTraversal();

    @Test
    void traverse() {
        TreeNode<Integer> rootNode = createTree();
        List<Integer> traversed = levelOrderTraversal.traverse(rootNode);
        assertThat(traversed).containsExactly(1, 2, 3, 4, 5, 6, 7, 8);
    }

    private TreeNode<Integer> createTree() {

        TreeNode<Integer> leaf1 = new TreeNode<>(8, null, null);
        TreeNode<Integer> leaf2 = new TreeNode<>(6, null, null);
        TreeNode<Integer> leaf3 = new TreeNode<>(7, null, null);
        TreeNode<Integer> leaf4 = new TreeNode<>(5, null, null);

        TreeNode<Integer> i3 = new TreeNode<>(4, leaf1, null);
        TreeNode<Integer> i1 = new TreeNode<>(2, i3, leaf4);
        TreeNode<Integer> i2 = new TreeNode<>(3, leaf2, leaf3);

        TreeNode<Integer> root = new TreeNode<>(1, i1, i2);
        return root;
    }
}