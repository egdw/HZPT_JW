package im.hdy.entity;

import java.util.LinkedList;

/**
 * Created by hdy on 21/10/2017.
 */
public class ScoreDetail {
    //存放标题 比如杭州科技职业技术学院成绩明细(有效)
    private String title;

    //存放院 院(系)/部：信息工程学院
    private String courtyard;

    //行政班级：网络（移动互联）1501
    private String administrative_class;

    //学号
    private String stuId;

    //姓名
    private String name;

    //打印时间
    private String time;

    //存放学期信息
    private LinkedList<Semester> semesters;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCourtyard() {
        return courtyard;
    }

    public void setCourtyard(String courtyard) {
        this.courtyard = courtyard;
    }

    public String getAdministrative_class() {
        return administrative_class;
    }

    public void setAdministrative_class(String administrative_class) {
        this.administrative_class = administrative_class;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public LinkedList<Semester> getSemesters() {
        return semesters;
    }

    public void setSemesters(LinkedList<Semester> semesters) {
        this.semesters = semesters;
    }

    @Override
    public String toString() {
        return "ScoreDetail{" +
                "title='" + title + '\'' +
                ", courtyard='" + courtyard + '\'' +
                ", administrative_class='" + administrative_class + '\'' +
                ", stuId='" + stuId + '\'' +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", semesters=" + semesters +
                '}';
    }
}
