package com.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.domain.People;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SampleController {
    @GetMapping("/people")
    public List<People> findAll(){
        log.info("Sample Api Server Call /people");
        return new ArrayList<>(Arrays.asList(
                new People(1,"홍길동"),
                new People(2,"홍길동"),
                new People(3,"홍길동"),
                new People(4,"홍길동"),
                new People(5,"홍길동"),
                new People(6,"홍길동"),
                new People(7,"홍길동"),
                new People(8,"홍길동"),
                new People(9,"홍길동")
            ));
    }

    @GetMapping("/people/1")
    public People findByAge(){
        log.info("Sample Api Server Call /people/1");
        return new People(1,"홍길동");
    }
}
