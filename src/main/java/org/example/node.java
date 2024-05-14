package org.example;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

public class node {
    String name;
    List<edge> childlist = new ArrayList<>();
    node father;

    //在父节点下加新边
    void addChild(edge child){
        childlist.add(child);
    }
    //为边增加权重
    void addWeight(edge child){
        child.weight+=1;

    }
    boolean isChildExist(node child){
        for (edge Edge : this.childlist){
            if (Edge.childNode.name.equals(child.name)){
                return true;
            }
        }
        return  false;
    }
    edge findChildByName(String child){
        for (edge Edge : this.childlist){
            if (Edge.childNode.name.equals(child)){
                return Edge;
            }
        }
        return null;
    }

}


