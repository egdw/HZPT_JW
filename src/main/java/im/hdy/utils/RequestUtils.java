package im.hdy.utils;

import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by hdy on 10/10/2017.
 */
public class RequestUtils {
    CookieStore cookieStore = null;
    HttpClient httpclient = null;
    private String jsessionid;

    {
        init();
    }

    private void init() {
        httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);
    }


    public String request(String url) {
        HttpGet get = new HttpGet(url);
        get.setHeader("Referer", url);
        get.setHeader("Host", "jw.hzpt.edu.cn");
        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");


        get.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,la;q=0.4,zh-TW;q=0.2");
        get.setHeader("Accept-Encoding", "gzip, deflate");
        get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        get.setHeader("Content-Type", "application/x-www-form-urlencoded");

        if (jsessionid != null) {
            //获取到cookie请求对象
//            get.setHeader("Cookie", "ASP.NET_SessionId=" + jsessionid);
        }
        try {
            HttpResponse response = httpclient.execute(get);
//            Header header = response.getEntity().getContentType();
//            Header[] headers = get.getAllHeaders();
//            for(int i = 0 ;i<headers.length;i++){
//                System.err.println(headers[i]);
//            }
//
//            HeaderElement[] elements = header.getElements();
//            for (int i = 0; i<elements.length;i++){
//                System.err.println(elements[i]);
//            }
//
//
            if (response.getFirstHeader("Set-Cookie") != null) {
                jsessionid = String.valueOf(response.getFirstHeader("Set-Cookie").getValue());
                jsessionid = jsessionid.substring("ASP.NET_SessionId=".length(),
                        jsessionid.indexOf(";"));
                jsessionid = jsessionid.trim();
            }
            System.err.println(jsessionid);
            HttpEntity entity = response.getEntity();
            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "gb2312"));
            char[] c = new char[1024];
            int len = -1;
            StringBuilder sb = new StringBuilder();
            while ((len = reader.read(c)) != -1) {
                sb.append(new java.lang.String(c, 0, len));
            }
            return sb.toString();
        } catch (IOException e) {
            return null;
        } finally {
            get.abort();
        }
    }

    public String doPost(String url, Map<String, String> map) {
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setHeader("Referer", url);
            httpPost.setHeader("Host", "jw.hzpt.edu.cn");
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
            httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,la;q=0.4,zh-TW;q=0.2");
            httpPost.setHeader("Accept-Encoding", "gzip, deflate");
            httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//            httpPost.setHeader("Cookie", "name=value;ASP.NET_SessionId=" + jsessionid);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
                System.err.println("elementKey:" + elem.getKey() + " elementValue:" + elem.getValue());
            }
            HttpResponse response = httpclient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            httpPost.abort();
        }
        return result;
    }


    public String login(String username, String password, Elements __VIEWSTATEGENERATOR, Elements __VIEWSTATE) {

        HashMap<String, String> map = new HashMap<>();
        try {
            map.put("fgfggfdgtyuuyyuuckjg", "");
            map.put("typeName", URLEncoder.encode("学生", "gb2312"));
            map.put("Sel_Type", URLEncoder.encode("STU", "gb2312"));
            map.put("__VIEWSTATEGENERATOR", URLEncoder.encode(__VIEWSTATEGENERATOR.val(), "gb2312"));
            map.put("__VIEWSTATE", URLEncoder.encode(__VIEWSTATE.val(), "gb2312"));
            map.put("txt_asmcdefsddsd", URLEncoder.encode(username, "gb2312"));
            map.put("pcinfo", "Mozilla%2F5.0+%28Macintosh%3B+Intel+Mac+OS+X+10_13_0%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Chrome%2F61.0.3163.100+Safari%2F537.36undefined5.0+%28Macintosh%3B+Intel+Mac+OS+X+10_13_0%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Chrome%2F61.0.3163.100+Safari%2F537.36+SN%3ANULL");
            map.put("txt_pewerwedsdfsdff", "");
            map.put("txt_sdertfgsadscxcadsads", "");
            map.put("dsdsdsdsdxcxdfgfg", URLEncoder.encode(Md5Utils.encrypByMd5(username + (Md5Utils.encrypByMd5(password).toUpperCase().substring(0, 30)) + "13026").substring(0, 30).toUpperCase(), "gb2312"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        String result = null;
        HttpPost httpPost = new HttpPost("http://jw.hzpt.edu.cn/_data/home_login.aspx");
        try {
            httpPost.setHeader("Referer", "http://jw.hzpt.edu.cn/_data/home_login.aspx");
            httpPost.setHeader("Host", "jw.hzpt.edu.cn");
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
            httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,la;q=0.4,zh-TW;q=0.2");
            httpPost.setHeader("Accept-Encoding", "gzip, deflate");
            httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//            httpPost.setHeader("Cookie", "name=value;ASP.NET_SessionId=" + jsessionid);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
                System.err.println("elementKey:" + elem.getKey() + " elementValue:" + elem.getValue());
            }
            HttpResponse response = httpclient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            httpPost.abort();
        }
        return result;
    }
}
