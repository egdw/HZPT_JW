package im.hdy.test;

import im.hdy.utils.Md5Utils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by hdy on 16/10/2017.
 */
public class ParseUtils2 {


//      httpPost.setHeader("Referer", url);
//            httpPost.setHeader("Host", "jw.hzpt.edu.cn");
//            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
//            httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,la;q=0.4,zh-TW;q=0.2");
//            httpPost.setHeader("Accept-Encoding", "gzip, deflate");
//            httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

    @Test
    public void login() {
        try {
            Connection.Response response = Jsoup.connect("http://jw.hzpt.edu.cn/_data/home_login.aspx").method(Connection.Method.GET).execute();
            String ASP_NET_SessionId = response.cookies().get("ASP.NET_SessionId");
            Document parse = Jsoup.parse(response.body());
            //获取第一个数据
            Elements __VIEWSTATE = parse.getElementsByAttributeValue("name", "__VIEWSTATE");
            //获取到第二个数据
            Elements __VIEWSTATEGENERATOR = parse.getElementsByAttributeValue("name", "__VIEWSTATEGENERATOR");
            System.out.println(ASP_NET_SessionId);
            System.out.println(__VIEWSTATE);
            System.out.println(__VIEWSTATEGENERATOR);
            String username = "2015002530";
            String password = "hzkjzyjsxy";

            Connection connection = Jsoup.connect("http://jw.hzpt.edu.cn/_data/home_login.aspx").header("Referer", "http://jw.hzpt.edu.cn/_data/home_login.aspx/")
                    .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
                    .header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,la;q=0.4,zh-TW;q=0.2")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Cookie", "ASP.NET_SessionId=" + ASP_NET_SessionId)
                    .data("fgfggfdgtyuuyyuuckjg", "E1F5D873F3224A3A122DA593B71FA1")
                    .data("typeName", URLEncoder.encode("学生", "gb2312"))
                    .data("__VIEWSTATEGENERATOR", URLEncoder.encode(__VIEWSTATEGENERATOR.val()))
                    .data("__VIEWSTATE", URLEncoder.encode(__VIEWSTATE.val()))
                    .data("txt_asmcdefsddsd", URLEncoder.encode(username, "gb2312"))
                    .data("pcinfo", "Mozilla%2F5.0+%28Macintosh%3B+Intel+Mac+OS+X+10_13_0%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Chrome%2F61.0.3163.100+Safari%2F537.36undefined5.0+%28Macintosh%3B+Intel+Mac+OS+X+10_13_0%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Chrome%2F61.0.3163.100+Safari%2F537.36+SN%3ANULL")
                    .data("txt_pewerwedsdfsdff", "")
                    .data("txt_sdertfgsadscxcadsads", "")
                    .data("dsdsdsdsdxcxdfgfg", URLEncoder.encode(Md5Utils.encrypByMd5(username + (Md5Utils.encrypByMd5(password).toUpperCase().substring(0, 30)) + "13026").substring(0, 30).toUpperCase(), "gb2312")).method(Connection.Method.POST);

            Map<String, String> cookies =
                    response.cookies();
            Iterator<Map.Entry<String, String>> iterator =
                    cookies.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                connection.cookie(next.getKey(), next.getValue());
            }
            Connection.Response response1 = connection.execute();


            String body = response1.body();
            System.out.println(response1.cookies().get("ASP.NET_SessionId"));
            System.out.println(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//
//          map.put("fgfggfdgtyuuyyuuckjg", "");
//            map.put("typeName", URLEncoder.encode("学生", "gb2312"));
//            map.put("Sel_Type", URLEncoder.encode("STU", "gb2312"));
//            map.put("__VIEWSTATEGENERATOR", URLEncoder.encode(__VIEWSTATEGENERATOR.val(), "gb2312"));
//            map.put("__VIEWSTATE", URLEncoder.encode(__VIEWSTATE.val(), "gb2312"));
//            map.put("txt_asmcdefsddsd", URLEncoder.encode(username, "gb2312"));
//            map.put("pcinfo", "Mozilla%2F5.0+%28Macintosh%3B+Intel+Mac+OS+X+10_13_0%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Chrome%2F61.0.3163.100+Safari%2F537.36undefined5.0+%28Macintosh%3B+Intel+Mac+OS+X+10_13_0%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Chrome%2F61.0.3163.100+Safari%2F537.36+SN%3ANULL");
//            map.put("txt_pewerwedsdfsdff", "");
//            map.put("txt_sdertfgsadscxcadsads", "");
//            map.put("dsdsdsdsdxcxdfgfg", URLEncoder.encode(Md5Utils.encrypByMd5(username + (Md5Utils.encrypByMd5(password).toUpperCase().substring(0, 30)) + "13026").substring(0, 30).toUpperCase(), "gb2312"));
}
