package org.example;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class graphDrawer {
    public String generateDirectedGraph(nodeList fileNodes){
        StringBuilder dot = new StringBuilder();
        dot.append("digraph G {\n");

        for (node Node : fileNodes.returnAllNode()) {
            dot.append("\t").append(Node.name).append(";\n");

            for (edge Edge : Node.childlist) {
                dot.append("\t").append(Node.name).append(" -> ").append(Edge.childNode.name).append(" [label=\"").append(Edge.weight).append("\"];\n");
            }
        }

        dot.append("}\n");

        return dot.toString();
    }
    public String generateHighLightGraph(nodeList fileNodes,List<node> path, List<edge> edgePath) {
        StringBuilder dot = new StringBuilder();
        dot.append("digraph G {\n");

        for (node Node : fileNodes.returnAllNode()){
            if(path.contains(Node)){
                dot.append("\t").append(Node.name).append(" [color=red, fontcolor=red];\n");
            }
            else{
                dot.append("\t").append(Node.name).append(";\n");
            }
            edge find_edge  = find_edge_in_list(edgePath, Node);
            if(find_edge!=null){
                for (edge Edge : Node.childlist){
                    if(Edge.childNode == find_edge.childNode){
                        dot.append("\t").append(Node.name).append(" -> ").append(Edge.childNode.name)
                                .append(" [label=\"").append(Edge.weight).append("\", color=red, fontcolor=red];\n");
                    }
                    else{
                        dot.append("\t").append(Node.name).append(" -> ").append(Edge.childNode.name)
                                .append(" [label=\"").append(Edge.weight).append("\"];\n");
                    }

                }
            }
            else{
                for (edge Edge : Node.childlist){
                    dot.append("\t").append(Node.name).append(" -> ").append(Edge.childNode.name)
                            .append(" [label=\"").append(Edge.weight).append("\"];\n");
                }
            }
        }

        dot.append("}\n");
        return dot.toString();
    }

    public edge find_edge_in_list(List<edge> edgeList, node Node){
        for (edge Edge : edgeList){
            if (Edge.fatherNode == Node){
                return Edge;
            }
        }
        return null;
    }

    public void drawDirectedGraph(nodeList fileNodes, String fileName) throws IOException {

        String dotSource = generateDirectedGraph(fileNodes);

        Graphviz graphviz = Graphviz.fromString(dotSource);
        File outputFile = new File(fileName);
        graphviz.render(Format.PNG).toFile(outputFile);
        displayImage(outputFile);

    }
    public void drawHighlightGraph(nodeList fileNodes,List<node> path,List<edge> highlightPath, String fileName) throws IOException {

        String dotSource = generateHighLightGraph(fileNodes,path,highlightPath);

        Graphviz graphviz = Graphviz.fromString(dotSource);
        File outputFile = new File(fileName);
        graphviz.render(Format.PNG).toFile(outputFile);
        displayImage(outputFile);


    }
    public void displayImage(File file) throws IOException {
        if (GraphicsEnvironment.isHeadless()) {
            System.err.println("No graphical environment available.");
            return;
        }

        BufferedImage image = ImageIO.read(file);
        ImageIcon icon = new ImageIcon(image);
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(image.getWidth(), image.getHeight());
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}


