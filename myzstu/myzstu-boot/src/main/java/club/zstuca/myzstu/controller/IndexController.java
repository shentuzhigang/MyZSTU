package club.zstuca.myzstu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-05-23 20:02
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/info")
    public String info() {
        return "info";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello MyZSTU";
    }
}
