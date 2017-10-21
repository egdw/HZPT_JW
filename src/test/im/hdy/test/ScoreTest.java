package im.hdy.test;

import im.hdy.entity.Score;
import im.hdy.entity.ScoreDetail;
import im.hdy.entity.Semester;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.LinkedList;

/**
 * Created by hdy on 21/10/2017.
 */
public class ScoreTest {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/hdy/Documents/score1.html");
        FileInputStream stream = new FileInputStream(file);
        int len = -1;
        byte[] bytes = new byte[512];
        StringBuffer buffer = new StringBuffer();
        while ((len = stream.read(bytes)) != -1) {
            buffer.append(new String(bytes, 0, len));
        }
        stream.close();
        Document document = Jsoup.parse(buffer.toString());
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
            System.out.println("***********" + i + "**********");
            Element table = tables.get(i);
            System.out.println(table);
            System.out.println("***********" + i + "**********/r/n");
            if (table.select("tr[valign$=middle]").size() == 1) {
                //说明是头.即是杭州科技职业技术学院学生成绩明细.为表格头部
                System.out.println("tou1");
                String title = table.select("tr > td").get(0).text();
                scoreDetail = new ScoreDetail();
                scoreDetail.setTitle(title);
            } else if (table.select("tr > td.noborder").size() > 0) {
                System.out.println(table.select("tr > td.noborder").size());
                if (table.select("tr > td > table").size() != 1) {
                    continue;
                }
                //说明是页码.为表格尾部
                System.out.println("tou4");
//                System.out.println(table);
                scoreDetail.setSemesters(semesters);
                scoreDetails.add(scoreDetail);
                semesters = new LinkedList<>();

            } else if (table.select("tr[$align!=center]").size() == 2) {
                System.out.println("tou2");
                //代表是学期表头
                scoreDetail = new ScoreDetail();
                scoreDetail.setCourtyard(table.select("tr").get(0).getElementsByTag("td").get(0).text());
                scoreDetail.setAdministrative_class(table.select("tr").get(0).getElementsByTag("td").get(1).text());
                scoreDetail.setStuId(table.select("tr").get(1).getElementsByTag("td").get(0).text());
                scoreDetail.setName(table.select("tr").get(1).getElementsByTag("td").get(1).text());
                scoreDetail.setTime(table.select("tr").get(1).getElementsByTag("td").get(2).text());
            } else {
                System.out.println("tou3");
                //说明就是普通的学期了
                semester = new Semester();
                semester.setTitle(table.select("tr > td[align$=left]").get(0).text());
                String src = imgs.get(temp).attr("src");
                semester.setImage(new File(src));
//                new FileOutputStream()
                //这里进行课表的下载
                semesters.add(semester);
                temp++;
            }
        }
        score.setScoreDetails(scoreDetails);
        System.out.println(score);
    }
}
