package org.example;

import org.w3c.dom.NodeList;

import java.util.regex.Pattern;

public class generateNewText {
    nodeList fileNodes;


    String generateNewText(String inputText){
        String[] words = inputText.split("\\s+");
        StringBuilder output = new StringBuilder();
        // 定义一个正则表达式，匹配标点符号
        String punctuationRegex = "[\\p{Punct}]";
        String buffer = null;
        node bufferNode = null;
        boolean isBufferNodeValid = false;
        boolean isBufferValid = false;
        for (String word : words){
            if(!word.isEmpty()){
                if (Pattern.matches(punctuationRegex, word)) {
                    output.append(word).append(" ");
                    buffer = word;
                    isBufferNodeValid =false;
                    isBufferValid = false;
                } else {
                    //第一个词
                    if(buffer==null){
                        buffer = word;
                        isBufferValid = true;
                        output.append(word).append(" ");
                        if(fileNodes.is_node_exist(buffer)){
                            bufferNode = fileNodes.findNodeByName(buffer);
                            isBufferNodeValid = true;
                        }
                        else{
                            isBufferNodeValid = false;
                        }
                    }
                    //非第一个词
                    else {
                        buffer = word;
                        if(!isBufferValid || !isBufferNodeValid){
                            output.append(word).append(" ");
                            }
                        else {
                            //选桥梁节点
                            for(edge child : bufferNode.childlist){
                                if(child.childNode.findChildByName(word)!=null){
                                    output.append(child.childNode.name).append(" ").append(word).append(" ");
                                    break;
                                }
                            }
                        }
                        if(fileNodes.is_node_exist(word)){
                            bufferNode = fileNodes.findNodeByName(word);
                            isBufferNodeValid = true;
                        }
                        else{
                            isBufferNodeValid = false;
                        }
                        isBufferValid = true;
                    }
                }

            }
        }
        return output.toString();
    }


}
