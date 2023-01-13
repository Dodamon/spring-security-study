package come.cos.securitybasic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // view를 리턴하겠다
public class IndexController {
    //localhost:8080

    @GetMapping({"", "/"})
    public String index(){
        System.out.println("controller");
        // mustache template 엔진
        // 기본폴더 src/main/resources/
        // 뷰리졸버 기본 설정: template (prefix), mustache(suffix)
        return "index"; // src/main/resources/templates/index.mustache
    }
}
