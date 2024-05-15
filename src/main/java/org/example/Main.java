package org.example;

public class Main {
    public static void main(String[] args) {

        fileModule FileModule = new fileModule();
        FileModule.loadFile_formTree();
        nodeList tree = FileModule.fileNodes;
        graphDrawer Drawer = new graphDrawer();
        String filename = "F:\\Campus\\engineer\\softwareEngineering\\lab1\\mySW\\src\\main\\java\\org\\example\\graph.png";
        Drawer.drawDirectedGraph(tree,filename);
    }
}