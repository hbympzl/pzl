package utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;

public class XmlUtils {
    public static Document getDocument(String xmlName){
        try {
            return new SAXReader().read(xmlName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void saveDocument(Document doc,String xmlName) {
        try {
            //创建格式器(/t:缩进使用的字符 true:开启换行)
            OutputFormat format = new OutputFormat("\t", true);
            format.setTrimText(true);//去除文档当中已经存在的空白
            //创建输出流(字节流)
            OutputStream out = new FileOutputStream(xmlName);
            //转化成字符流
            Writer wout = new OutputStreamWriter(out, "utf-8");
            //创建xml输出流
            XMLWriter writer = new XMLWriter(wout, format);
            writer.write(doc);
            writer.close();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
