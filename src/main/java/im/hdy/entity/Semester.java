package im.hdy.entity;

import java.io.File;

/**
 * Created by hdy on 21/10/2017.
 */
public class Semester {

    //存放:学年学期：2016-2017学年第一学期
    private String title;

    //存放课表图片
    private File image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Semester{" +
                "title='" + title + '\'' +
                ", image=" + image +
                '}';
    }
}
