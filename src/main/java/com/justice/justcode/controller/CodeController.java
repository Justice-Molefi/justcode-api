package com.justice.justcode.controller;

import com.justice.justcode.dto.CodeRequest;
import com.justice.justcode.service.CodeExecutionService;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;

@RestController
@RequestMapping("/execute")
public class CodeController {
    private final CodeExecutionService codeExecutionService;

    public CodeController(CodeExecutionService codeExecutionService) {
        this.codeExecutionService = codeExecutionService;
    }

    @PostMapping(produces = "text/plain")
    public String executeCode(@RequestBody CodeRequest codeRequest) {
        try{
            return codeExecutionService.excuteCode(codeRequest);
        }catch (IOException | InterruptedException ex){
            System.err.println(ex.getMessage());
        }
        return "Something went wrong";
    }

}
