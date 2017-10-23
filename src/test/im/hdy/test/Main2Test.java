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
public class Main2Test {

    public static void main(String[] args) {
        File file = new File("/Users/hdy/Documents/main2.html");
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
            Elements imgs = document.getElementsByTag("img");
            System.out.println(imgs.get(0).attr("src"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
