package im.hdy.utils;

import im.hdy.entity.Score;
import im.hdy.entity.ScoreDetail;
import im.hdy.entity.Semester;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hdy on 20/10/2017.
 * 用于hzpt的网站登录
 */
public class LoginUtils {
    //cookie
    static CookieStore cookieStore = null;
    public static StringBuilder sb = null;
    public static DefaultHttpClient client = new DefaultHttpClient();
    static String cookie = null;
    static String VIEWSTATE = null;

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     */
    public static void login(String username, String password, String code) {
        // 加密密码
        String Tmm = Md5Utils.encrypByMd5(username + Md5Utils.encrypByMd5(password).substring(0, 30).toUpperCase() + "13026").substring(0, 30).toUpperCase();
        System.out.println(Tmm);
        // 加密验证码
        String yzm = Md5Utils.encrypByMd5(Md5Utils.encrypByMd5(code.toUpperCase()).substring(0, 30).toUpperCase() + "13026").substring(0, 30)
                .toUpperCase();
        System.out.println(yzm);
        // 设置登录POST内容
        String path = "http://jw.hzpt.edu.cn/_data/index_LOGIN.aspx";
        String encode = "GBK";
        Map<String, String> map = new HashMap<String, String>();
        map.put("__VIEWSTATE", VIEWSTATE);
        map.put("pcInfo",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36undefined5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36 SN:NULL");
        try {
            map.put("typeName", URLEncoder.encode("学生", "gb2312"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map.put("Sel_Type", "STU");
        map.put("txt_asmcdefsddsd", username);
        map.put("txt_pewerwedsdfsdff", "");
        map.put("txt_sdertfgsadscxcadsads", "");
        map.put("sbtState", "");
        map.put("dsdsdsdsdxcxdfgfg", Tmm);
        map.put("fgfggfdgtyuuyyuuckjg", yzm);

        List<NameValuePair> list = new ArrayList<NameValuePair>();

        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        try {
            // 实现将请求的参数封装到表单中
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, encode);
            HttpPost httppost = new HttpPost(path);
            httppost.setHeader("Accept-Encoding", "gzip, deflate, sdch");
            httppost.setHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
            httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httppost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            httppost.setHeader("Cookie", sb.toString());
            httppost.setHeader("Referer", "http://jw.hzpt.edu.cn/_data/index_LOGIN.aspx");
            httppost.setHeader("Origin", "http://jw.hzpt.edu.cn/");
            httppost.setHeader("Host", "jw.hzpt.edu.cn");
            httppost.setHeader("Connection", "keep-alive");

            httppost.setEntity(entity);
            HttpResponse httpResponse = client.execute(httppost);
            httppost.abort();
            Header headers[] = httppost.getAllHeaders();
            try {
                CookieStore set = client.getCookieStore();
                System.out.println("LOGIN cookie-->" + set.toString());
            } catch (Exception e) {
                System.out.println(e.toString() + "2");
            }
            sb = new StringBuilder();
            List<Cookie> cookies = ((AbstractHttpClient) client).getCookieStore().getCookies();
            for (int j = 0; j < cookies.size(); j++) {
                sb.append(cookies.get(j).getName() + "=" + cookies.get(j).getValue() + ";");
            }
            System.out.print("成功后的Cookie---->" + sb.toString());

            HttpGet main = new HttpGet("http://jw.hzpt.edu.cn/MAINFRM.aspx");
            HttpResponse mainhttpResponse = client.execute(main);
            main.abort();// 休眠
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 用于重新获取验证码
     */
    public static File getVerfiy() {
        String LoginHtml = "";
        HttpResponse set1 = null;
        HttpResponse set2 = null;
        String set_cookie1 = "";

        // 对主页面发起GET请求
        HttpGet gg = new HttpGet("http://jw.hzpt.edu.cn/");
        try {
            gg.setHeader("Accept-Encoding", "gzip, deflate, sdch");
            gg.setHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
            gg.setHeader("Content-Type", "application/x-www-form-urlencoded");
            gg.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            gg.setHeader("Connection", "keep-alive");
            gg.setHeader("Host", "jw.hzpt.edu.cn");
            set1 = client.execute(gg);
            sb = new StringBuilder();
            List<Cookie> cookies = ((AbstractHttpClient) client).getCookieStore().getCookies();
            for (int i = 0; i < cookies.size(); i++) {
                sb.append(cookies.get(i).getName() + "=" + cookies.get(i).getValue() + ";");
            }
            CookieStore set = client.getCookieStore();
            System.out.println("主页面      cookie-->" + set.toString());
            gg.abort();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // 对LOGIN页面发起Get请求
        HttpGet kk = new HttpGet("http://jw.hzpt.edu.cn/_data/index_LOGIN.aspx");

        try {
            // kk.setHeader("Cookie",sb.toString());
            kk.setHeader("Accept-Encoding", "gzip, deflate, sdch");
            kk.setHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
            kk.setHeader("Content-Type", "application/x-www-form-urlencoded");
            kk.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            kk.setHeader("Referer", "http://jw.hzpt.edu.cn/");
            kk.setHeader("Connection", "keep-alive");
            kk.setHeader("Host", "jw.hzpt.edu.cn");
            set2 = client.execute(kk);

            CookieStore set = client.getCookieStore();
            //获取到登陆的cookie
            System.out.println("LOGIN cookie-->" + set.toString());
        } catch (IOException e) {

            e.printStackTrace();
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(set2.getEntity().getContent(), "gb2312"));
            char[] c = new char[1024];
            int len = -1;
            StringBuilder sb = new StringBuilder();
            while ((len = reader.read(c)) != -1) {
                sb.append(new java.lang.String(c, 0, len));
            }
            LoginHtml = sb.toString();
        } catch (UnsupportedOperationException | IOException e) {
            System.out.println(e.toString() + "1");
        }
        //搜索__VIEWSTATE的值
        String regEx = "name=\"__VIEWSTATE\" value=\"(.*?)\"";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(LoginHtml);
        boolean rs = mat.find();
        String STR = mat.group(0);
        String VTE = STR.substring(26, STR.length() - 1);
        System.out.println("__VIEWSTATE-->" + VTE);
        VIEWSTATE = VTE;
        // 获取验证码
        File file = new File("/Users/hdy/Downloads/test.jpg");
        downloadVerfiyImage(file);
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    /**
     * 下载登录验证图片
     *
     * @param saveDest 保存的地址
     */
    private static void downloadVerfiyImage(File saveDest) {
        HttpGet httpGet = new HttpGet("http://jw.hzpt.edu.cn/sys/ValidateCode.aspx");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
        httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpGet.setHeader("Accept", "image/webp,image/*,*/*;q=0.8");
        httpGet.setHeader("Cookie", sb.toString());
        httpGet.setHeader("Referer", "http://jw.hzpt.edu.cn/_data/index_LOGIN.aspx");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Host", "jw.hzpt.edu.cn");
        FileOutputStream fos;
        try {
            // 客户端开始向指定的网址发送请求
            HttpResponse response = client.execute(httpGet);

            CookieStore set = client.getCookieStore();
            System.out.println("验证码      cookie-->" + set.toString());

            InputStream inputStream = response.getEntity().getContent();

            File file = new File(saveDest.getParent());
            if (!file.exists()) {
                file.mkdirs();
            }
            fos = new FileOutputStream(saveDest);
            byte[] data = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(data)) != -1) {
                fos.write(data, 0, len);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    /**
     * 发送登录请求
     *
     * @param path
     * @param map
     * @param encode
     * @return
     */
    public static String sendHttpClientPost(String path, Map<String, String> map, String encode) {

        List<NameValuePair> list = new ArrayList<NameValuePair>();

        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        try {
            // 实现将请求的参数封装到表单中
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, encode);
            HttpPost httppost = new HttpPost(path);
            httppost.setHeader("Accept-Encoding", "gzip, deflate, sdch");
            httppost.setHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
            httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httppost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            httppost.setHeader("Cookie", sb.toString());
            httppost.setHeader("Referer", "http://jw.hzpt.edu.cn/_data/index_LOGIN.aspx");
            httppost.setHeader("Origin", "http://jw.hzpt.edu.cn/");
            httppost.setHeader("Host", "jw.hzpt.edu.cn");
            httppost.setHeader("Connection", "keep-alive");

            httppost.setEntity(entity);
            HttpResponse httpResponse = client.execute(httppost);
            httppost.abort();
            Header headers[] = httppost.getAllHeaders();
            try {
                CookieStore set = client.getCookieStore();
                System.out.println("LOGIN cookie-->" + set.toString());
            } catch (Exception e) {
                System.out.println(e.toString() + "2");
            }
            sb = new StringBuilder();
            List<Cookie> cookies = ((AbstractHttpClient) client).getCookieStore().getCookies();
            for (int j = 0; j < cookies.size(); j++) {
                sb.append(cookies.get(j).getName() + "=" + cookies.get(j).getValue() + ";");
            }
            System.out.print("成功后的Cookie---->" + sb.toString());

            HttpGet main = new HttpGet("http://jw.hzpt.edu.cn/MAINFRM.aspx");
            HttpResponse mainhttpResponse = client.execute(main);
            main.abort();// 休眠

            HttpGet main1 = new HttpGet("http://jw.hzpt.edu.cn/xsxj/Stu_MyInfo_RPT.aspx");
            HttpResponse main1httpResponse = client.execute(main1);

            int aa = main1httpResponse.getStatusLine().getStatusCode();

            String res = reader(main1httpResponse.getEntity().getContent(), "gb2312");

            if (aa == 200) {
                // 获取重定向之后跳转的url http://jiaowu.pdsu.edu.cn/MAINFRM.aspx
                // String
                // locationUrl=httpResponse.getLastHeader("Location").getValue();
                // System.out.println("重定向后的地址:http://jiaowu.pdsu.edu.cn"+locationUrl);
                // 跳转到重定向的url
                // HttpGet httpget = new
                // HttpGet("http://jiaowu.pdsu.edu.cn"+locationUrl);
                // 重定向之后cookie发生变化
                return "200\n\r" + res;

            } else {
                return "状态码:" + aa;
            }

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        } catch (ClientProtocolException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return "失败";
    }

    /**
     * 流转换为字符串
     *
     * @param inputStream 输入流
     * @param encode      编码
     * @return
     */
    private static String reader(InputStream inputStream, String encode) {
        ByteArrayOutputStream OutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result = "";
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    OutputStream.write(data, 0, len);
                }
                result = new String(OutputStream.toByteArray(), encode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取成绩
     *
     * @return 成绩下载地址
     */
    private static File downloadScore(String url) {
        HttpGet get = new HttpGet("http://jw.hzpt.edu.cn/xscj/" + url);
        HttpResponse mainHttpResponse = null;
        try {
            mainHttpResponse = client.execute(get);
            InputStream stream = mainHttpResponse.getEntity().getContent();
            File file = new File("/Users/hdy/Downloads/" + UUID.randomUUID().toString() + ".jpg");
            FileOutputStream outputStream = new FileOutputStream(file);
            int len = -1;
            byte[] bytes = new byte[2048];
            while ((len = stream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            outputStream.flush();
            outputStream.close();
            stream.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 下载考试安排表
     *
     * @return 安排表下载地址
     */
    private static File downloadExam(String url) {
        HttpGet get = new HttpGet("http://jw.hzpt.edu.cn/KSSW/" + url);
        HttpResponse mainHttpResponse = null;
        try {
            mainHttpResponse = client.execute(get);
            InputStream stream = mainHttpResponse.getEntity().getContent();
            File file = new File("/Users/hdy/Downloads/" + UUID.randomUUID().toString() + ".jpg");
            FileOutputStream outputStream = new FileOutputStream(file);
            int len = -1;
            byte[] bytes = new byte[2048];
            while ((len = stream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            outputStream.flush();
            outputStream.close();
            stream.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param SJ       有效成绩(1)还是原始成绩(0)
     * @param SelXNXQ  0 入学以来 1学年 2学期
     * @param zfx_flag 主修0 辅修1
     * @param sel_xn   学年
     * @param sel_xq   0第一学期 1第二学期
     * @apiNote 由于请求的参数比较多.有些参数在某些请求的时候又不需要携带.所以请仔细的看我下面需要的请求
     * <p>
     * 入学以来的请求:SJ(默认为0) SelXNXQ:0 zfx_flag(默认为0)
     * 学年的请求:sel_xn:学年 SJ(默认为0) SelXNXQ:1 zfx_flag(默认为0)
     * 学期的请求:sel_xn:学年 SJ(默认为0) SelXNXQ:2 zfx_flag(默认为0) sel_xq(默认为0)
     * </p>
     */
    public static Score getScore(int SJ, int SelXNXQ, int zfx_flag, int sel_xn, int sel_xq) {
        HashMap<String, String> map = new HashMap<>();
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        try {
            map.put("btn_search", URLEncoder.encode("检索", "gb2312"));
            map.put("zxf", "0");
            map.put("SJ", SJ + "");
            map.put("SelXNXQ", SelXNXQ + "");
            map.put("zfx_flag", zfx_flag + "");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //判断当前的请求类型
        switch (SelXNXQ) {
            //入学以来
            case 0:
                break;
            //学年
            case 1:
                map.put("sel_xn", sel_xn + "");
                break;
            //学期
            case 2:
                map.put("sel_xn", sel_xn + "");
                map.put("sel_xq", sel_xq + "");
                break;
            //输入有误
            default:
                return null;
        }
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        try {

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "gb2312");
            HttpPost httppost = new HttpPost("http://jw.hzpt.edu.cn/xscj/Stu_MyScore_rpt.aspx");
            httppost.setHeader("Accept-Encoding", "gzip, deflate, sdch");
            httppost.setHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
            httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httppost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            httppost.setHeader("Cookie", sb.toString());
            httppost.setHeader("Referer", "http://jw.hzpt.edu.cn/xscj/Stu_MyScore.aspx");
            httppost.setHeader("Origin", "http://jw.hzpt.edu.cn/");
            httppost.setHeader("Host", "jw.hzpt.edu.cn");
            httppost.setHeader("Connection", "keep-alive");

            httppost.setEntity(entity);
            HttpResponse httpResponse = client.execute(httppost);
            int code = httpResponse.getStatusLine().getStatusCode();
            if (code == 200) {
                String res = reader(httpResponse.getEntity().getContent(), "gb2312");
                httppost.abort();
                Document document = Jsoup.parse(res);
                //获取到所有的表格
                Elements tables = document.getElementsByTag("table");
                //获取所有的成绩表图片地址
                Elements imgs = document.getElementsByTag("img");
                //临时的变量.用于学期换个成绩表绑定
                int temp = 0;
                Score score = new Score();
                LinkedList<Semester> semesters = new LinkedList<>();
                LinkedList<ScoreDetail> scoreDetails = new LinkedList<>();

                Semester semester = null;
                ScoreDetail scoreDetail = null;
                for (int i = 0; i < tables.size(); i++) {
                    Element table = tables.get(i);
                    if (table.select("tr[valign$=middle]").size() == 1) {
                        //说明是头.即是杭州科技职业技术学院学生成绩明细.为表格头部
                        String title = table.select("tr > td").get(0).text();
                        scoreDetail = new ScoreDetail();
                        scoreDetail.setTitle(title);
                    } else if (table.select("tr > td.noborder").size() > 0) {
                        if (table.select("tr > td > table").size() != 1) {
                            continue;
                        }
                        //说明是页码.为表格尾部
                        scoreDetail.setSemesters(semesters);
                        scoreDetails.add(scoreDetail);
                        semesters = new LinkedList<>();

                    } else if (table.select("tr[$align!=center]").size() == 2) {
                        //代表是学期表头
                        scoreDetail = new ScoreDetail();
                        scoreDetail.setCourtyard(table.select("tr").get(0).getElementsByTag("td").get(0).text());
                        scoreDetail.setAdministrative_class(table.select("tr").get(0).getElementsByTag("td").get(1).text());
                        scoreDetail.setStuId(table.select("tr").get(1).getElementsByTag("td").get(0).text());
                        scoreDetail.setName(table.select("tr").get(1).getElementsByTag("td").get(1).text());
                        scoreDetail.setTime(table.select("tr").get(1).getElementsByTag("td").get(2).text());
                    } else {
                        //说明就是普通的学期了
                        semester = new Semester();
                        semester.setTitle(table.select("tr > td[align$=left]").get(0).text());
                        String src = imgs.get(temp).attr("src");
                        semester.setImage(downloadScore(src));
//                new FileOutputStream()
                        //这里进行课表的下载
                        semesters.add(semester);
                        temp++;
                    }
                }
                score.setScoreDetails(scoreDetails);
                return score;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取考试安排时间
     * <p>
     * sel_xnxq 学年学期 20161
     *
     * @param sel_lcxz 考试轮次性质 3中考 2末考 1补考
     *                 sel_lc   考试轮次 null默认 2016102 猜测和学年有一些关系
     */
    public static void getExam(int sel_lcxz) {
        HttpGet main = new HttpGet("http://jw.hzpt.edu.cn/KSSW/stu_ksap.aspx");
        try {
            HttpResponse mainHttpResponse = null;
            mainHttpResponse = client.execute(main);
            int code = mainHttpResponse.getStatusLine().getStatusCode();
            String res = reader(mainHttpResponse.getEntity().getContent(), "gb2312");
            main.abort();
            if (code == 200) {
                //从文档当中获取sel_xnxq参数.以此来动态的获取当前的学年而不是通过写死的方式.
                Document document = Jsoup.parse(res);
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
                HashMap<String, String> map = new HashMap<>();
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                try {
                    map.put("btn_search", URLEncoder.encode("检索", "gb2312"));
                    map.put("sel_xnxq", sel_xnxq);
                    map.put("sel_lcxz", sel_lcxz + "");
                    map.put("sel_lc", sel_lc);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (map != null && !map.isEmpty()) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                    }
                }

                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "gb2312");
                HttpPost httppost = new HttpPost("http://jw.hzpt.edu.cn/KSSW/stu_ksap_rpt.aspx");
                httppost.setHeader("Accept-Encoding", "gzip, deflate, sdch");
                httppost.setHeader("User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
                httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
                httppost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                httppost.setHeader("Cookie", sb.toString());
                httppost.setHeader("Referer", "http://jw.hzpt.edu.cn/KSSW/stu_ksap.aspx");
                httppost.setHeader("Origin", "http://jw.hzpt.edu.cn/");
                httppost.setHeader("Host", "jw.hzpt.edu.cn");
                httppost.setHeader("Connection", "keep-alive");

                httppost.setEntity(entity);
                HttpResponse httpResponse = client.execute(httppost);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    String r = reader(httpResponse.getEntity().getContent(), "gb2312");
                    httppost.abort();
                    Document document2 = Jsoup.parse(r);
                    Elements imgs = document2.getElementsByTag("img");
                    //获取考试安排图片
                    Element element = null;
                    for (int i = 0; i < imgs.size(); i++) {
                        element = imgs.get(i);
                        if (element.text().contains("stu_ksap_drawimg.aspx")) {
                            break;
                        }
                    }
                    String src = element.attr("src");
                    File file = downloadExam(src);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static HashMap<String, String> getUserInfo() {
        try {
            HttpGet main = new HttpGet("http://jw.hzpt.edu.cn/xsxj/Stu_MyInfo_RPT.aspx");
            HttpResponse mainHttpResponse = null;
            mainHttpResponse = client.execute(main);
            int aa = mainHttpResponse.getStatusLine().getStatusCode();
            String res = reader(mainHttpResponse.getEntity().getContent(), "gb2312");
            HashMap<String, String> map = new HashMap<>();
            main.abort();
            if (aa == 200) {
                //说明请求成功
                Document document = Jsoup.parse(res);
                Elements trs = document.getElementsByTag("tr");
                Iterator<Element> iterator = trs.iterator();
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
                map.put("头像", "http://jw.hzpt.edu.cn" + document.getElementsByTag("img").get(0).attr("src").substring(2));
            }
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断当前是否已经登录
     *
     * @return
     */
    public static boolean isLogin() {
        try {
            HttpGet main = new HttpGet("http://jw.hzpt.edu.cn/PUB/foot.aspx");
            HttpResponse mainHttpResponse = null;
            mainHttpResponse = client.execute(main);
            int aa = mainHttpResponse.getStatusLine().getStatusCode();
            String res = reader(mainHttpResponse.getEntity().getContent(), "gb2312");
            main.abort();
            if (aa == 200) {
                Document document = Jsoup.parse(res);
                Element theFootMemo = document.getElementById("TheFootMemo");
                boolean contains = theFootMemo.text().contains("学生");
                return contains;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
