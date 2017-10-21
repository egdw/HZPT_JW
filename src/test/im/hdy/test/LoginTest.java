package im.hdy.test;

import im.hdy.utils.LoginUtils;
import org.junit.Test;

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
        HashMap<String, String> info = LoginUtils.getUserInfo();
        File file1 = LoginUtils.downloadClass();
        System.out.println(info);
    }

    public void test() {

    }
}
