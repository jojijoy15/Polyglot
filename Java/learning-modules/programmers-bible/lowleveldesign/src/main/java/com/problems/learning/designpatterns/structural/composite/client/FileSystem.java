package com.problems.learning.designpatterns.structural.composite.client;

import com.problems.learning.designpatterns.structural.composite.component.FileSystemNode;
import com.problems.learning.designpatterns.structural.composite.group.Folder;
import com.problems.learning.designpatterns.structural.composite.leaf.File;

import java.util.List;

/*
    Note:
    * Compose objects into tree structures to represent part-whole hierarchies.
    * The Composite Design Pattern lets clients treat individual objects and compositions of objects uniformly.
 */
public class FileSystem {

    public static void main(String[] args) {

        File file1 = new File("file1.txt", 100);
        File file2 = new File("file2.txt", 200);

        Folder folder1 = new Folder("Documents");
        folder1.add(file1);
        folder1.add(file2);

        File file3 = new File("file3.txt", 300);

        Folder root = new Folder("Root");
        root.add(folder1);
        root.add(file3);


        System.out.println(root.getSize()); // 600

        List<FileSystemNode> nodes = List.of(root, file1, file2, folder1);
        Integer totalSize = nodes.stream()
                .map(FileSystemNode::getSize)
                .reduce(0, Integer::sum);
        System.out.println(totalSize);

    }
}