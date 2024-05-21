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
    public String generateHighLightGraph(nodeList fileNodes, List<edge> edgePath) {
        StringBuilder dot = new StringBuilder();
        dot.append("digraph G {\n");

        for (node Node : fileNodes.returnAllNode()) {
            dot.append("\t").append(Node.name).append(";\n");

            for (edge Edge : Node.childlist) {
                String edgeColor = ""; // Color for the edge
                if (edgePath.contains(Edge)) {
                    edgeColor = "[color=red, fontcolor=red]";
                }
                dot.append("\t").append(Node.name).append(" -> ").append(Edge.childNode.name)
                        .append(" [label=\"").append(Edge.weight).append("\"]")
                        .append(edgeColor).append(";\n");
            }
        }

        for (edge Edge : edgePath) {
            dot.append("\t").append(Edge.fatherNode.name).append(" [color=red, fontcolor=red];\n");
            dot.append("\t").append(Edge.childNode.name).append(" [color=red, fontcolor=red];\n");
            dot.append("\t").append(Edge.fatherNode.name).append(" -> ").append(Edge.childNode.name)
                    .append(" [label=\"").append(Edge.weight).append("\", color=red, fontcolor=red];\n");
        }

        dot.append("}\n");
        return dot.toString();
    }

    public void drawDirectedGraph(nodeList fileNodes, String fileName) throws IOException {

        String dotSource = generateDirectedGraph(fileNodes);

        Graphviz graphviz = Graphviz.fromString(dotSource);
        File outputFile = new File(fileName);
        graphviz.render(Format.PNG).toFile(outputFile);
        displayImage(outputFile);

    }
    public void drawHighlightGraph(nodeList fileNodes,List<edge> highlightPath, String fileName) throws IOException {

        String dotSource = generateHighLightGraph(fileNodes,highlightPath);

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


