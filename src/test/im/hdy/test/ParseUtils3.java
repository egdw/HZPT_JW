package im.hdy.test;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by hdy on 17/10/2017.
 */
public class ParseUtils3 {

    @Test
    public void login() {
        try {

            Connection.Response execute = Jsoup.connect("http://cas.hzpt.edu.cn/cas/login").execute();
            Map<String, String> cookies1 = execute.cookies();
            System.out.println(cookies1);

            Connection.Response response = Jsoup.connect("http://cas.hzpt.edu.cn/cas/login").header("Host", "cas.hzpt.edu.cn")
                    .header("Referer", "http://cas.hzpt.edu.cn/cas/login?service=http%3A%2F%2Fi.hzpt.edu.cn%2Fdcp%2Findex.jsp")
                    .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
                    .header("Origin","http://cas.hzpt.edu.cn")
                    .header("Content-Type","application/x-www-form-urlencoded")
                    .header("Accept-Encoding","gzip, deflate")
                    .header("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6,la;q=0.4,zh-TW;q=0.2")
                    .header("Cache-Control","no-cache")
                    .header("Connection","keep-alive")
                    .header("Content-Length","243")
                    .data("username", "2015002530")
                    .data("encodedService", "http%253A%252F%252Fi.hzpt.edu.cn%252Fdcp%252Findex.jsp")
                    .data("service", "http://i.hzpt.edu.cn/dcp/index.jsp")
                    .data("serviceName", "null")
                    .data("loginErrCnt", "0")
                    .data("password", "f0c5fef7ec5d967affb278b6ac2979af")
                    .data("lt", "LT-1830339-aP88paEnfjzxomFK0Pj7")
                    .cookies(cookies1)
//                    .header("Cookie","key_dcp_cas="+cookies1.get("key_dcp_cas"))
//                    .cookie("key_dcp_cas",cookies1.get("key_dcp_cas"))
                    .method(Connection.Method.POST).execute();

            System.err.println("response:" + response.body());
            System.out.println(response.headers());
            Map<String, String> cookies = response.cookies();
            System.out.println(cookies);

//            Connection connection = Jsoup.connect("http://i.hzpt.edu.cn/dcp/index.jsp?ticket=ST-1830371-Yir1PG9Wdec7aRnbmHn1")
//                    .header("Referer", "http://cas.hzpt.edu.cn/cas/login")
//                    .header("Host", "i.hzpt.edu.cn");
//
//            Iterator<Map.Entry<String, String>> iterator = cookies.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry<String, String> next = iterator.next();
//                connection.cookie(next.getKey(), next.getValue());
//            }
//            Connection.Response response1 = connection.execute();
//            System.err.println("response1:" + response1.body());
//
//            Connection connection1 = Jsoup.connect("http://i.hzpt.edu.cn/dcp/forward.action?path=/portal/portal&p=home").header("Referer", "http://i.hzpt.edu.cn/dcp/index.jsp?ticket=ST-1830371-Yir1PG9Wdec7aRnbmHn1")
//                    .header("Host", "i.hzpt.edu.cn");
//
//            Iterator<Map.Entry<String, String>> iterator1 = response1.cookies().entrySet().iterator();
//            while (iterator1.hasNext()) {
//                Map.Entry<String, String> next = iterator1.next();
//                connection1.cookie(next.getKey(), next.getValue());
//            }
//
//            Connection.Response response2 = connection1.execute();
//            System.err.println("response2:" + response2.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
