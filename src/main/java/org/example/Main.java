package org.example;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.net.FileNameMap;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        //计算出工作目录绝对路径
        String currDir = System.getProperty("user.dir");
        String relativeDir = "\\src\\main\\java\\org\\example\\";
        String workDir = currDir + relativeDir;
        System.out.println("workDir:"+workDir);
        //获取用户输入的文件路径/文件名
        System.out.println("Input text file:");
        Scanner scanner = new Scanner(System.in);
        String filepath = scanner.nextLine();

        File file = new File(workDir,filepath);
        filepath = file.getAbsolutePath();

        //System.out.println("filePath:"+filepath);

        //System.exit(0);
        try{
            //构造单词树
            fileModule FileModule = new fileModule(filepath);
            FileModule.loadFile_formTree();
            //画有向图
            nodeList tree = FileModule.fileNodes;
            graphDrawer Drawer = new graphDrawer();
            System.out.println("Input output file name(format: .png):");
            String outputFile = scanner.nextLine();
            outputFile = workDir + outputFile;
            //String filename = "F:\\Campus\\engineer\\softwareEngineering\\lab1\\mySW\\src\\main\\java\\org\\example\\graph.png";
            Drawer.drawDirectedGraph(tree,outputFile);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}