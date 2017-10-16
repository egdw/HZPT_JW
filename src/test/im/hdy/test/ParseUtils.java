package im.hdy.test;

import im.hdy.utils.Md5Utils;
import im.hdy.utils.RequestUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by hdy on 10/10/2017.
 */
public class ParseUtils {


    @Test
    public void login() {
        RequestUtils utils = new RequestUtils();
//        String request = utils.getCookie();
        String request = utils.request("http://jw.hzpt.edu.cn/_data/home_login.aspx");
        Document parse = Jsoup.parse(request);
        //获取第一个数据
        Elements __VIEWSTATE = parse.getElementsByAttributeValue("name", "__VIEWSTATE");
        //获取到第二个数据
        Elements __VIEWSTATEGENERATOR = parse.getElementsByAttributeValue("name", "__VIEWSTATEGENERATOR");
        String post = utils.login("2015002530", "hzkjzyjsxy", __VIEWSTATEGENERATOR, __VIEWSTATE);
        System.err.println(post);
//        System.out.println(utils.request("http://jw.hzpt.edu.cn/xsxj/Stu_MyInfo_RPT.aspx"));
    }
}
