package com.example.demo.controller;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@RestController
public class ReadXMLFile {

    @RequestMapping("/readFiles")
    public static void main(String[] args) throws  Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document d = builder.parse("C:\\Software\\IDEA_Projects\\HKCRC\\Website\\index1\\dktGetByTrk.xml");
            NodeList sList = d.getElementsByTagName("dockets");
            node(sList);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void node(NodeList list)
    {
        for (int i = 0; i <list.getLength() ; i++) {
            Node node = list.item(i);
            NodeList childNodes = node.getChildNodes();
            for (int j = 0; j <childNodes.getLength() ; j++) {
                if (childNodes.item(j).getNodeType()==Node.ELEMENT_NODE) {
                    System.out.print(childNodes.item(j).getNodeName() + ":");
                    System.out.println(childNodes.item(j).getFirstChild().getNodeValue());
                }
            }
        }
    }

    public static void element(NodeList list){
        for (int i = 0; i <list.getLength() ; i++) {
            Element element = (Element) list.item(i);
            NodeList childNodes = element.getChildNodes();
            for (int j = 0; j <childNodes.getLength() ; j++) {
                if (childNodes.item(j).getNodeType()==Node.ELEMENT_NODE) {
                    //获取节点
                    System.out.print(childNodes.item(j).getNodeName() + ":");
                    //获取节点值
                    System.out.println(childNodes.item(j).getFirstChild().getNodeValue());
                }
            }
        }
    }


    public static void listNodes(Node node, String space) {
        Element element = null;
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            element = (Element)node;
            System.out.println(space + element.getTagName());
        }

        NodeList list = node.getChildNodes();
        int childNodesLength = list.getLength();
        if (childNodesLength > 0) {
            for (int i = 0; i < childNodesLength; i++) {
                listNodes(list.item(i), space + "    ");
            }
        }
    }
}