package org.example;

import  java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class randomWalk {
    public String randomWalk(nodeList fileNodes) {

        Random random = new Random();
        StringBuilder output = new StringBuilder();
        List<node> allNodes = fileNodes.returnAllNode();
        if (allNodes.isEmpty()) {
            output.append("The graph is empty.");
            //System.out.println("The graph is empty.");
            return output.toString();
        }

        // 随机选择一个起始节点
        node currentNode = allNodes.get(random.nextInt(allNodes.size()));
        //已访问的边
        Set<edge> visitedEdges = new HashSet<>();
        //随机游走结果
        List<String> walkResult = new ArrayList<>();

        output.append("Starting random walk from node: ").append(currentNode.name).append("\n");

        //System.out.println("Starting random walk from node: " + currentNode.name);

        walkResult.add(currentNode.name);
        while (true) {
            //没有出边
            if (currentNode.childlist.isEmpty()) {
                output.append("No more outgoing edges from node: ").append(currentNode.name).append("\n");
                //System.out.println("No more outgoing edges from node: " + currentNode.name);
                break;
            }
            // 从当前节点的出边中随机选择一条
            edge currentEdge = currentNode.childlist.get(random.nextInt(currentNode.childlist.size()));
            //第二次经过某条边
            if (visitedEdges.contains(currentEdge)) {
                output.append("Encountered a repeated edge, stopping random walk.");
                //System.out.println("Encountered a repeated edge, stopping random walk.");
                break;
            }
            visitedEdges.add(currentEdge);
            currentNode = currentEdge.childNode;
            walkResult.add(currentNode.name);

            output.append("Moved to node: ").append(currentNode.name).append("\n");

            //System.out.println("Moved to node: " + currentNode.name);
        }

        // 遍历结果写入文件
        saveWalkResult(walkResult);
        return output.toString();

    }

    private static void saveWalkResult(List<String> walkResult) {
        try (FileWriter writer = new FileWriter("random_walk.txt")) {
            for (String nodeName : walkResult) {
                writer.write(nodeName + " ");
            }
            //System.out.println("Walk result saved to random_walk.txt");
        } catch (IOException e) {
            System.err.println("Error writing result to file: " + e.getMessage());
        }
    }
}

