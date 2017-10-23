package im.hdy.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by hdy on 23/10/2017.
 */
public class MainTest {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/hdy/Documents/main.html");
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);
            int len = -1;
            byte[] bytes = new byte[512];
            StringBuffer buffer = new StringBuffer();
            while ((len = stream.read(bytes)) != -1) {
                buffer.append(new String(bytes, 0, len));
            }
            stream.close();
            Document document = Jsoup.parse(buffer.toString());
            Elements options = document.getElementsByTag("option");
            //获取当前的学年
            String sel_xnxq = null;
            //获取当前的
            String sel_lc = null;
            for (int i = 0; i < options.size(); i++) {
                Element element = options.get(i);
                if (element.text().contains("学年")) {
                    sel_xnxq = element.val();
                    sel_lc = sel_xnxq + "02";
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
