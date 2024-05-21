package org.example;

import  java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import  java.util.List;
import  java.util.Set;
import  java.util.HashSet;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.util.Random;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        //计算出工作目录绝对路径
        String currDir = System.getProperty("user.dir");
        String relativeDir = "\\src\\main\\java\\org\\example\\";
        String workDir = currDir + relativeDir;
        System.out.println("workDir:" + workDir);
        //获取用户输入的文件路径/文件名
        System.out.println("Input text file:");
        Scanner scanner = new Scanner(System.in);
        String filepath = scanner.nextLine();
        //参数传递给fileModule
        File file = new File(workDir, filepath);
        filepath = file.getAbsolutePath();

        nodeList tree;
        graphDrawer Drawer = new graphDrawer();
        try {
            //构造单词树
            fileModule FileModule = new fileModule(filepath);
            FileModule.loadFile_formTree();
            //画有向图
            tree = FileModule.fileNodes;

            System.out.println("Input output file name(format: .png):");
            String outputFile = scanner.nextLine();
            outputFile = workDir + outputFile;
            //String filename = "F:\\Campus\\engineer\\softwareEngineering\\lab1\\mySW\\src\\main\\java\\org\\example\\graph.png";
            Drawer.drawDirectedGraph(tree, outputFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("initialize already done...you can enter command now.");
        System.out.println("Enter 'exit' to end.");

        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine();
            String result = "";
            if (command.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                //break;
                scanner.close();
                System.exit(0);
            }
            //3-查询桥接词
            if (command.equals("3")) {
                System.out.print("input two words: ");
                String words = scanner.nextLine();
                result = processCommand(tree, words, 3);
            }
            //4-根据输入文本查询桥接词生成新文本
            if (command.equals("4")) {
                System.out.print("input text: ");
                String text = scanner.nextLine();
                result = processCommand(tree, text, 4);
            }
            //5-计算最短路径
            if (command.equals("5")) {
                System.out.print("input two words: ");
                String words = scanner.nextLine();
                result = processCommand(tree, words, 5);
                File f = new File("path.png");
                Drawer.displayImage(f);
            }
            //6-随机游走
            if (command.equals("6")) {
                result = processCommand(tree, "", 6);
            }
            // String result = processCommand(command);
            System.out.print(result);
        }

        //scanner.close();
    }

    public static String processCommand(nodeList nodelist, String words, int comd) throws IOException {
        //int result = -1;
        queryBridgeWords query = new queryBridgeWords(nodelist);
        String[] word = words.split(" ");
        if (comd == 3) {
            //queryBridgeWords query = new queryBridgeWords(nodelist);
            //result = query.queryBW(word[0],word[1]);
            query.queryBW(word[0], word[1]);
            return query.output;
        }
        if (comd == 4) {
            //queryBridgeWords query = new queryBridgeWords(nodelist);
            ArrayList<String> wordlist = new ArrayList<>(Arrays.asList(word));

            int cnt = 0;
            for (int i = 0; i < word.length - 1; i++) {
                //result += query.queryBW(word[i],word[i+1]);
                query.queryBW(word[i], word[i + 1]);
                if (query.reslt == 2) {
                    Random random = new Random();
                    int r = query.findEdge.size();
                    wordlist.add(i + 1 + cnt, query.findEdge.get(random.nextInt(r)).childNode.name);
                    cnt++;
                }
            }
            StringBuilder strbd = new StringBuilder();
            for (int i = 0; i < wordlist.size(); i++) {
                String s = wordlist.get(i);
                if (i == wordlist.size() - 1) {
                    strbd.append(s).append(".\n");
                    break;
                }
                // 如果不是最后一个元素，添加空格
                strbd.append(s).append(" ");
            }
            return strbd.toString();
        }
        if (comd == 5) {
            pathCalc pathcalc = new pathCalc();
            return pathcalc.calcShortestPath(word[0], word[1], nodelist);
        }
        if(comd ==6){
            randomWalk(nodelist);
        }
        return "not 3 4";
    }

    public static void randomWalk(nodeList fileNodes) {

        Random random = new Random();
        List<node> allNodes = fileNodes.returnAllNode();
        if (allNodes.isEmpty()) {
            System.out.println("The graph is empty.");
            return;
        }

        // 随机选择一个起始节点
        node currentNode = allNodes.get(random.nextInt(allNodes.size()));
        //已访问的边
        Set<edge> visitedEdges = new HashSet<>();
        //随机游走结果
        List<String> walkResult = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Starting random walk from node: " + currentNode.name);
        walkResult.add(currentNode.name);

        while (true) {
            if (currentNode.childlist.isEmpty()) {
                System.out.println("No more outgoing edges from node: " + currentNode.name);
                break;
            }

            // 从当前节点的出边中随机选择一条
            edge currentEdge = currentNode.childlist.get(random.nextInt(currentNode.childlist.size()));

            if (visitedEdges.contains(currentEdge)) {
                System.out.println("Encountered a repeated edge, stopping random walk.");
                break;
            }

            visitedEdges.add(currentEdge);
            currentNode = currentEdge.childNode;
            walkResult.add(currentNode.name);

            System.out.println("Moved to node: " + currentNode.name);
            //System.out.println("Press 'q' to stop or any other key to continue...");
            /*
            String input = scanner.nextLine();
            if ("q".equalsIgnoreCase(input)) {
                System.out.println("Random walk stopped by user.");
                break;
            }
            */
        }

        // 输出遍历结果并写入文件
        saveWalkResult(walkResult);
    }

    private static void saveWalkResult(List<String> walkResult) {
        try (FileWriter writer = new FileWriter("random_walk.txt")) {
            for (String nodeName : walkResult) {
                writer.write(nodeName + " ");
            }
            System.out.println("Walk result saved to random_walk.txt");
        } catch (IOException e) {
            System.err.println("Error writing result to file: " + e.getMessage());
        }
    }
}