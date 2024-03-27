package com.app.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // Forward to custom error page or handle the error in some way
        return "errorPage"; // Assuming you have an errorPage.jsp or errorPage.html
    }
}
