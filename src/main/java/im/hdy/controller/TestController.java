package im.hdy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hdy on 10/10/2017.
 */
@RestController
public class TestController {

    @RequestMapping(value = "/")
    public String index(){
        return "success";
    }

}
