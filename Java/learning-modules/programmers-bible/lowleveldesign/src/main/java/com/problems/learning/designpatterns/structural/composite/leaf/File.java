package com.problems.learning.designpatterns.structural.composite.leaf;

import com.problems.learning.designpatterns.structural.composite.component.FileSystemNode;

public class File implements FileSystemNode {
    private String name;
    private int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public int getSize() {
        return size;
    }
}