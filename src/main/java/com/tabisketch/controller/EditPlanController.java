package com.tabisketch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/plan/{uuid}/edit")
public class EditPlanController {
    @GetMapping
    public String get(final @PathVariable String uuid) {
        return "plan/edit";
    }
}
