package utils;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlUtils {
    /*
     * <student number="N_1001"> <name>zs</name> <age>23</age> <sex>male</sex>
     * </student> {number=N_1001, name=zs, age=23, sex=male}
     *
     * 可以是纯属性元素 也可以包含只有文本的子元素 <a><b><c>xxx</c></b></a>，这样的东西不转了
     */
    public static Map<String, String> toMap(Element e) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        /*
         * 循环遍历e的所有属性 循环遍历e的所有子元素（条件：只是纯文本内容的子元素）
         */
        /*
         * 把e的所有属性添加到map中
         */
        List<Attribute> attrs = e.attributes();
        for (Attribute a : attrs) {
            map.put(a.getName(), a.getValue());
        }
        /*
         * 把e的所有子元素（纯属文本内容的子元素）添加到map中
         */
        List<Element> eles = e.elements();
        for (Element ele : eles) {
            if (ele.isTextOnly()) {
                map.put(ele.getName(), ele.getText());
            }
        }
        return map;
    }

    /*
     * {number=N_1001, name=zs, age=23, sex=male} 1，元素名称我不知道！ <student
     * number="N_1001" name="zs" age="23" sex="male"/> <student>
     * <number>N_1001</number> <name>zs</name> <age>23</age> <sex>male</sex>
     * </student>
     *
     * {number=N_1001, name=zs, age=23, sex=male} toElement(map, "student",
     * [name, age, sex]);
     */
    public static Element toElement(Map<String, String> map, String eleName,
                                    List<String> childs) {
        Element e = DocumentHelper.createElement(eleName);
        for (String key : map.keySet()) {
            String value = map.get(key);
            if (childs.contains(key)) {
                e.addElement(key).setText(value);
            } else {
                e.addAttribute(key, value);
            }
        }

        return e;
    }

    public static Element toElement(Map<String, String> map, String eleName,
                                    String... childs) {
        List<String> list = Arrays.asList(childs);
        return toElement(map, eleName, list);
    }

    public static Document getDocument(String xmlName) {
        return getDocument(xmlName, true);
    }

    public static Document getDocument(String xmlName, boolean relative) {
        try {
            if (relative) {
                URL url = Thread.currentThread().getContextClassLoader()
                        .getResource(xmlName);
                xmlName = url.getPath();
            }
            return new SAXReader().read(xmlName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveDocument(Document doc, String xmlName) {
        saveDocument(doc, xmlName, true);
    }

    public static void saveDocument(Document doc, String xmlName,
                                    boolean relative) {
        try {
            // 创建格式器，\t表示缩进使用的字符，true表示为换行
            OutputFormat format = new OutputFormat("\t", true);
            format.setTrimText(true);// 把文档中已经存在空白去除。
            if (relative) {
                URL url = Thread.currentThread().getContextClassLoader()
                        .getResource(xmlName);
                if (url != null) {
                    xmlName = url.getPath();
                } else {
                    xmlName = Thread.currentThread().getContextClassLoader().getResource("").getPath() + xmlName;
                }
            }
            // 创建输出流
            OutputStream out = new FileOutputStream(xmlName);
            // 把输出流转换成字符流，其中使用了utf-8编码
            Writer wout = new OutputStreamWriter(out, "utf-8");
            // 使用输出流和格式化器创建XML输出流
            XMLWriter writer = new XMLWriter(wout, format);
            // 输出Document对象
            writer.write(doc);
            // 关闭流！
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
