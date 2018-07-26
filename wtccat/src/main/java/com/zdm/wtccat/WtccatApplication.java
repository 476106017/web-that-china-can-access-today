package com.zdm.wtccat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.servlet.ModelAndView;

@Controller
@SpringBootApplication
public class WtccatApplication {

    public static void main(String[] args) {
        SpringApplication.run(WtccatApplication.class, args);
    }

    @GetMapping
    public ModelAndView index() {
        return new ModelAndView ("wtccat.html");
    }

}
