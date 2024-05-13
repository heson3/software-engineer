package org.example;
import java.util.ArrayList;
import java.util.List;
public class nodeList {
    private List<node> nodes = new ArrayList<>();

    private List<edge> edges = new ArrayList<>();

    void add_edge(edge Edge){
        this.edges.add(Edge);
    }
    boolean is_node_exist(String NodeName){
        for (node Node : nodes){
            if(Node.name.equals(NodeName)){
                return true;
            }
        }
        return false;

    }
    void add_node(node Node){
        this.nodes.add(Node);
    }
    node findNodeByName(String NodeName){
        for (node Node : this.nodes){
            if(Node.name.equals(NodeName)){
                return Node;
            }
        }
        return null;
    }
    List<node> returnAllChild(){
        return this.nodes;
    }

}

