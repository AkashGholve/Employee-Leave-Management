package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.app.entity.LeaveForm;
import com.app.entity.LeaveResponse;
import com.app.service.LeaveService;

@Controller
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @GetMapping("/leave")
    public String showLeaveForm(Model model) {
        model.addAttribute("leaveForm", new LeaveForm());
        return "leaveForm";
    }

    @PostMapping("/leave")
    public String applyLeave(@ModelAttribute LeaveForm leaveForm, Model model) {
        try {
            LeaveResponse leaveResponse = leaveService.applyLeave(leaveForm);
            model.addAttribute("leaveResponse", leaveResponse);
            return "leaveResponse"; // View name for leaveResponse.jsp
        } catch (Exception e) {
            // Handle exception
            model.addAttribute("error", e.getMessage());
            return "errorPage"; // View name for errorPage.jsp
        }
    }
}





