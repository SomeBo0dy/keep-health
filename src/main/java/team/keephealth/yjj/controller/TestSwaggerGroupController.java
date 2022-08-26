package team.keephealth.yjj.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestSwaggerGroupController {
    @GetMapping
    public String test(){
        return "success";
    }
}
