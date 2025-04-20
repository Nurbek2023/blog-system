package com.nurbek.blog.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/not-found")
    public String triggerNotFound() {
        throw new EntityNotFoundException("This entity does not exist");
    }

    @GetMapping("/bad-request")
    public String triggerBadRequest() {
        throw new IllegalArgumentException("Invalid input data");
    }

    @GetMapping("/error")
    public String triggerError() throws Exception {
        throw new Exception("Something ");
    }
}
