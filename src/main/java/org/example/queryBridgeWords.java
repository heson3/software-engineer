package org.example;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class queryBridgeWords {
    nodeList fileNodes;
    /*
    String word1_name;
    String word2_name;
    */
    List<edge> findEdge;

    String  queryBridgeWords(String word1, String word2){
        StringBuilder output = new StringBuilder();
        if(fileNodes.is_node_exist(word1)&&fileNodes.is_node_exist(word2)){

            for(edge Edge : fileNodes.findNodeByName(word1).childlist){
                if(Edge.childNode.name.equals(word2)){
                    findEdge.add(Edge);
                }
            }
            output.append("The bridge words from " + "'").append(word1).append(" to '").append(word2).append("' are: ");
            boolean isFirst = true;
            for (edge Edge : findEdge){
                if(isFirst){
                    output.append(Edge.childNode.name);
                    isFirst = false;
                }
                else{
                    output.append(",").append(Edge.childNode.name);
                }
            }
            output.append(".");

        }
        else{
            if(!fileNodes.is_node_exist(word1)){
                output.append("No '").append(word2).append("' or '").append(word2).append("' in the graph!");
            }
        }
        return output.toString();
    }
}
