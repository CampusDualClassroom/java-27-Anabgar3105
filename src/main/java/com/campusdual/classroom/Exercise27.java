package com.campusdual.classroom;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Exercise27 {
    public static void main(String[] args) {
        try {
            Exercise27.createXMLDocument();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        Exercise27.createJSONFile();
    }

    private static void createJSONFile() {
        JsonArray itemsArray = new JsonArray();
        itemsArray.add(createJSONItem(2,"Manzana"));
        itemsArray.add(createJSONItem(1,"Leche"));
        itemsArray.add(createJSONItem(3,"Pan"));
        itemsArray.add(createJSONItem(2,"Huevo"));
        itemsArray.add(createJSONItem(1,"Queso"));
        itemsArray.add(createJSONItem(1,"Cereal"));
        itemsArray.add(createJSONItem(4,"Agua"));
        itemsArray.add(createJSONItem(6,"Yogur"));
        itemsArray.add(createJSONItem(2,"Arroz"));
        JsonObject items = new JsonObject();
        items.add("items",itemsArray);

        try (FileWriter fw = new FileWriter("src/main/resources/shoppingList.json")){
//            fw.write(items.toString()); Para ponerlo sin formato
//            fw.flush();
            //Con formato
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(items);
            fw.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JsonObject createJSONItem(int quantity, String itemDesc) {
        JsonObject obj = new JsonObject();
        obj.addProperty("quantity",quantity);
        obj.addProperty("text",itemDesc);
        return obj;
    }


    private static void createXMLDocument() throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        Element root = document.createElement(("shoppinglist"));
        document.appendChild(root);
        Element items = document.createElement("items");
        root.appendChild(items);
        items.appendChild(Exercise27.createXMLItem(document,"2","Manzana","item"));
        items.appendChild(Exercise27.createXMLItem(document,"1","Leche","item"));
        items.appendChild(Exercise27.createXMLItem(document,"3","Pan","item"));
        items.appendChild(Exercise27.createXMLItem(document,"2","Huevo","item"));
        items.appendChild(Exercise27.createXMLItem(document,"1","Queso","item"));
        items.appendChild(Exercise27.createXMLItem(document,"1","Cereal","item"));
        items.appendChild(Exercise27.createXMLItem(document,"4","Agua","item"));
        items.appendChild(Exercise27.createXMLItem(document,"6","Yogur","item"));
        items.appendChild(Exercise27.createXMLItem(document,"2","Arroz","item"));
        Exercise27.writeToXMLFile(document,"src/main/resources/shoppingList.xml");
    }

    private static void writeToXMLFile(Document document, String pathName) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount","3");
        File file = new File(pathName);
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(file);
        transformer.transform(source,result);
    }

    private static Element createXMLItem(Document document, String quantity, String itemDesc, String tag){
        Element item = document.createElement(tag);
        item.setAttribute("quantity",quantity);
        item.setTextContent(itemDesc);
        return item;
    }

}
