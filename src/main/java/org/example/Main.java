package org.example;

import java.io.IOException;
import java.net.FileNameMap;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {

        fileModule FileModule = new fileModule();
        FileModule.loadFile_formTree();
        nodeList tree = FileModule.fileNodes;
        graphDrawer Drawer = new graphDrawer();
        String filename = "F:\\Campus\\engineer\\softwareEngineering\\lab1\\mySW\\src\\main\\java\\org\\example\\graph.png";
        Drawer.drawDirectedGraph(tree,filename);
    }
}