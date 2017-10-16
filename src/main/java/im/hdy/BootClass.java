package im.hdy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by hdy on 10/10/2017.
 */
@SpringBootApplication
@ComponentScan
public class BootClass {

    public static void main(String[] args) {
        SpringApplication.run(BootClass.class, args);
    }

}
