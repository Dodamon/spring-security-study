package come.cos.securitybasic.controller;

import come.cos.securitybasic.model.User;
import come.cos.securitybasic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // view를 리턴하겠다
public class IndexController {
    //localhost:8080

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({"", "/"})
    public String index(){
        // mustache template 엔진
        // 기본폴더 src/main/resources/
        // 뷰리졸버 기본 설정: template (prefix), mustache(suffix)
        return "index"; // src/main/resources/templates/index.mustache
    }

    @GetMapping("/user")
    @ResponseBody
    public String user() {
        return "user";
    }
    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    @ResponseBody
    public String manager() {
        return "manager";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(User user) {
        System.out.println(user);
        return "login";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        System.out.println("회원가입 진행 : " + user);
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole("ROLE_USER");
        userRepository.save(user);
        return "redirect:/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @GetMapping("/joinProc")
    @ResponseBody
    public String joinProc() {
        return "회원가입 완료!";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    @ResponseBody
    public String info() {
        return "개인정보";
    }

    //@Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    @ResponseBody
    public String data() {
        return "데이터정보";
    }
}
