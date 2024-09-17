package org.blazejherzog.jobmatcher.controller.admin;

import org.blazejherzog.jobmatcher.web.ResponseView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @GetMapping({"/login"})
    public ResponseView adminLogin() {
        return ResponseView.of("admin/admin_login");
    }

    @GetMapping
    public ResponseView panel() {
        return ResponseView.of("admin/panel");
    }
}
