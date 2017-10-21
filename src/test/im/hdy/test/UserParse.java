package im.hdy.test;

import com.sun.org.apache.xalan.internal.xsltc.dom.SortingIterator;
import jdk.internal.util.xml.impl.Input;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by hdy on 21/10/2017.
 */
public class UserParse {

    @Test
    public void test() throws IOException {
        System.out.println(3 % 2);
        File file = new File("/Users/hdy/Documents/user.html");
        FileInputStream stream = new FileInputStream(file);
        int len = -1;
        byte[] bytes = new byte[512];
        StringBuffer buffer = new StringBuffer();
        while ((len = stream.read(bytes)) != -1) {
            buffer.append(new String(bytes, 0, len));
        }
        stream.close();
        Document document = Jsoup.parse(buffer.toString());
        Elements trs = document.getElementsByTag("tr");
        Iterator<Element> iterator = trs.iterator();
        HashMap<String, String> map = new HashMap<>();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            Elements tds = element.getElementsByTag("td");
            //如果数量为一的都是有问题的.不需要遍历
            if (tds.size() != 1) {
                //说明是偶数
                String key = null;
                String value = null;
                for (int i = 0; i < tds.size(); i++) {
                    if (i % 2 == 0) {
                        //说明是key
                        key = tds.get(i).text().trim();
                        System.out.println();
                    } else {
                        //说明是Value
                        value = tds.get(i).text();
                        if (value == null || value.equals("")) {
                            value = "暂无数据";
                        }
                        map.put(key, value);
                    }
                }
            }
        }
        String attr = "http://jw.hzpt.edu.cn" + document.getElementsByTag("img").get(0).attr("src").substring(2);
        System.out.println(attr);
//        System.out.println(map);

    }
}
