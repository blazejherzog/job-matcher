package org.blazejherzog.jobmatcher.controller;

import org.blazejherzog.jobmatcher.web.ResponseView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public ResponseView home() {
        return ResponseView.of("home/home");
    }
}
