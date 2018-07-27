package com.zdm.wtccat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.expression.Maps;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;



@RestController
public class WtccatController {

    @Autowired
    private Path path;

    @GetMapping("/website")
    public List getWebsite() throws IOException {
        return getWsl();
    }
    @DeleteMapping("/website")
    public void delWebsite(String name) throws IOException {
        List<Map> wsl = getWsl();
        wsl.removeIf(ws->ws.get("name").equals(name));
        writeWsl(wsl);
    }
    @PostMapping("/website")
    public void website(String name,String url) throws IOException {
        List<Map> wsl = getWsl();
        if(wsl.stream().anyMatch(p->p.get("name").equals(name))
                || name.contains(" ") || url.contains(" "))
            return;
        var map = new HashMap();
        map.put("name",name);
        map.put("url",url);
        wsl.add(map);
        writeWsl(wsl);
    }


    @GetMapping("/websiteTest")
    public long websiteTest(String url) {
        int code;
        Instant i1 = Instant.now();
        try {
            URLConnection connection = new URL(url).openConnection();
            connection.setUseCaches(false);
            connection.setConnectTimeout(10*1000);
            code = ((HttpURLConnection) connection).getResponseCode();
        }catch (SocketTimeoutException e){
            return -999;
        }catch (Exception e){
            return -1000;
        }
        return code != 200 ? -code :
                Duration.between(i1,Instant.now()).toMillis();
    }

    private List<Map> getWsl() throws IOException {
        return Files.lines(path)
                .map(p -> {
                    var split = p.split(" ");
                    if (split.length!=2)
                        return null;
                    var map = new HashMap();
                    map.put("name",split[0]);
                    map.put("url",split[1]);
                    return map;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    private void writeWsl(List<Map> wsl) throws IOException {
        List<String> wsls = wsl.stream()
                .map(p -> p.get("name") + " " + p.get("url"))
                .collect(Collectors.toList());
        Files.write(path,wsls);
    }

}