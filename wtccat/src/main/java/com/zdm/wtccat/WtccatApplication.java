package com.zdm.wtccat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    @Bean
    public Path path() throws IOException {
        Path path = Paths.get("website.txt");
        if(!Files.exists(path))
            Files.createFile(path);
        return path;
    }

}
