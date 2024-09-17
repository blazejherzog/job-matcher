package org.blazejherzog.jobmatcher.controller.user;

import org.blazejherzog.jobmatcher.web.ResponseView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/user")
public class LoginController {

    @GetMapping({"/login"})
    public ResponseView userLogin() {
        return ResponseView.of("user/user_login");
    }
}
