package org.example;

import java.io.IOException;
import java.util.*;

public class pathCalc {

    //如果找到从word1到word2的路径，则输出句子，否则输出null

    String calcShortestPath(String word1, String word2, nodeList fileNodes) throws IOException {
        StringBuilder output= new StringBuilder();
        //用来存储节点到初始节点的最短距离
        Map<node, Integer> distance = new HashMap<>();
        //用来存储节点的前驱节点用来回溯访问
        Map<node, node> predecessor = new HashMap<>();
        //初始化距离为无穷大
        for (node Node : fileNodes.returnAllNode()) {
            distance.put(Node, Integer.MAX_VALUE);
            predecessor.put(Node, null);
        }
        node startNode = fileNodes.findNodeByName(word1);
        node endNode = fileNodes.findNodeByName(word2);
        distance.put(startNode,0);

        //存储已经访问过的节点
        Set<node> visited = new HashSet<>();


        while(!visited.contains(endNode)){
            node currentNode = getNodeWithLowestDistance(distance, visited);
            if(currentNode == null){
                //不可达
                return null;
            }
            visited.add(currentNode);

            //更新相邻节点的距离
            for (edge Edge : currentNode.childlist){
                node neighborNode = Edge.childNode;
                int edgeWeight = Edge.weight;
                int tentativeDistance = distance.get(currentNode) + edgeWeight;
                if (tentativeDistance < distance.get(neighborNode)) {
                    distance.put(neighborNode, tentativeDistance);
                    predecessor.put(neighborNode, currentNode);
                }
            }
        }

            //回溯路径
        List<node> shortestPath = new ArrayList<>();
        node currentNode2 = endNode;
        while (currentNode2 != null) {
            shortestPath.add(currentNode2);
            currentNode2 = predecessor.get(currentNode2);
        }
        graphDrawer drawer = new graphDrawer();
        drawer.drawHighlightGraph(fileNodes,shortestPath,"path.png");

        Collections.reverse(shortestPath);

            //生成输出字符串
        for (node Node : shortestPath){
            //if(output.isEmpty()){
            if(output.length() == 0){
                output.append(Node.name);
            }
            else{
                output.append(" ").append(Node.name);
            }
        }
        Integer dis = distance.get(fileNodes.findNodeByName(word2));
        output.append("\nLength of path is: ").append(dis.toString()).append(" .\n");
        return output.toString();
    }

    private static node getNodeWithLowestDistance(Map<node, Integer> distance, Set<node> visited) {
        int minDistance = Integer.MAX_VALUE;
        node minNode = null;
        for (Map.Entry<node, Integer> entry : distance.entrySet()) {
            node Node = entry.getKey();
            int dist = entry.getValue();
            if (!visited.contains(Node) && dist < minDistance) {
                minDistance = dist;
                minNode = Node;
            }
        }
        return minNode;
    }

}
