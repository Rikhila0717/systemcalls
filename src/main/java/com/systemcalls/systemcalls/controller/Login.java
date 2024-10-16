package com.systemcalls.systemcalls.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/systemcalls")
public class Login {
    @GetMapping("")
    public String returnApis(){
        return "make api calls";
    }
}
