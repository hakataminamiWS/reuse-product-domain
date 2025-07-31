package jp.my.spring.rest_open_api.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = { "/", "/{path:^(?!api|static|assets|favicon\\.ico|.*\\..*$).*$}" })
    public String index() {
        return "forward:/index.html";
    }
}