package im.hdy.test;

import im.hdy.utils.Md5Utils;
import org.junit.Test;

/**
 * Created by hdy on 10/10/2017.
 */
public class Md5Test {

    @Test
    public void test1() {
        String s = Md5Utils.encrypByMd5("2015002530" + (Md5Utils.encrypByMd5("hzkjzyjsxy").toUpperCase().substring(0, 30)) + "13026").substring(0, 30).toUpperCase();
        System.out.println(s);
    }

}
