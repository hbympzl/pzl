package xml;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import utils.XmlUtils;

import java.util.ArrayList;
import java.util.List;

public class Demo1 {
    @Test
    public void test1() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read("students.xml");
        Element root = document.getRootElement();
        List<Element> list = root.elements();
        for(Element e:list){
            //获取子元素
            String name = e.elementText("name");
            String age = e.elementText("age");
            String sex = e.elementText("sex");
            //获取属性
            String num = e.attributeValue("number");
            System.out.println(name+".."+age+".."+sex+".."+num);
        }
    }
    /*
    生成document文档
     */
    @Test
    public void test2(){
        List<Student> list = getList();
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("students");
        for(Student s : list){
            Element stuEle = toElement(s);
            root.add(stuEle);
        }
        System.out.println(root.asXML());
        XmlUtils.saveDocument(doc,"./fileplace/student4.xml");
    }

    private Element toElement(Student s) {
        Element e = DocumentHelper.createElement("student");
        e.addElement("name").setText(s.getName());
        e.addElement("age").setText(s.getAge());
        e.addElement("sex").setText(s.getSex());
        return e;
    }

    private List<Student> getList() {
        List<Student> list = new ArrayList<Student>();
        list.add(new Student("zs","20","male"));
        list.add(new Student("ls","21","female"));
        list.add(new Student("ww","22","female"));
        return list;
    }
}
