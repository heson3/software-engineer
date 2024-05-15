package org.example;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

import java.io.File;
import java.io.IOException;

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

    public void drawDirectedGraph(nodeList fileNodes, String fileName) throws IOException {

        String dotSource = generateDirectedGraph(fileNodes);

        Graphviz graphviz = Graphviz.fromString(dotSource);
        graphviz.render(Format.PNG).toFile(new File(fileName));

    }

}


