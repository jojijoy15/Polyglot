package com.problems.learning.designpatterns.structural.composite.group;

import com.problems.learning.designpatterns.structural.composite.component.FileSystemNode;

import java.util.ArrayList;
import java.util.List;

public class Folder implements FileSystemNode {
    private String name;

    private List<FileSystemNode> children = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    public void add(FileSystemNode node) {
        children.add(node);
    }

    @Override
    public int getSize() {
        int total = 0;
        for (FileSystemNode node : children) {
            total += node.getSize();
        }
        return total;
    }
}