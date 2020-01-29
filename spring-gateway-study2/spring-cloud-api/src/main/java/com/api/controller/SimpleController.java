package com.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.domain.Sample;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SimpleController {
    @GetMapping("/items")
    public String getText() {
        log.info("Api Called !! /items");
        return "item get!";
    }

    @GetMapping("/domains")
    public List<Sample> getList() {
        log.info("Api Called !! /domains");
        List<Sample> sampleList = new ArrayList<>();

        sampleList.add(new Sample("테스트1", 1));
        sampleList.add(new Sample("테스트2", 2));
        sampleList.add(new Sample("테스트3", 3));
        sampleList.add(new Sample("테스트4", 4));
        sampleList.add(new Sample("테스트5", 5));
        sampleList.add(new Sample("테스트6", 6));

        return sampleList;
    }
}
