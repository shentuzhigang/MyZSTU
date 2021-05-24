package club.zstuca.myzstu.boot.controller;

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
    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello MyZSTU";
    }
}
