package im.hdy.test;

import im.hdy.entity.Score;
import im.hdy.utils.LoginUtils;
import org.junit.Test;
import sun.rmi.runtime.Log;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by hdy on 20/10/2017.
 */
public class LoginTest {
    public static void main(String[] args) {
        File file =
                LoginUtils.getVerfiy();

        Scanner scanner = new Scanner(System.in);
        System.out.println("验证码:");
        String next = scanner.next();
        LoginUtils.login("2015002530", "hzkjzyjsxy", next);
        boolean login = LoginUtils.isLogin();
        System.err.println("当前是否登录:" + login);

//        HashMap<String, String> info = LoginUtils.getUserInfo();
//        Score score = LoginUtils.getScore(0, 0, 0, 2016, 0);
//        System.out.println(info);
//        System.out.println(score);
//        LoginUtils.getExam(2);
    }

    public void test() {

    }
}
