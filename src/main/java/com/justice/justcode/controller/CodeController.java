package com.justice.justcode.controller;

import com.justice.justcode.dto.CodeExecutionResponse;
import com.justice.justcode.dto.CodeRequest;
import com.justice.justcode.service.CodeExecutionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;

@RestController
@RequestMapping("/execute")
@CrossOrigin(origins = "http://localhost:4200")
public class CodeController {
    private final CodeExecutionService codeExecutionService;

    public CodeController(CodeExecutionService codeExecutionService) {
        this.codeExecutionService = codeExecutionService;
    }

    @PostMapping()
    public ResponseEntity<CodeExecutionResponse> executeCode(@RequestBody CodeRequest codeRequest) {
        try{
            CodeExecutionResponse response = codeExecutionService.excuteCode(codeRequest);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (IOException | InterruptedException ex){
            System.err.println(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(new CodeExecutionResponse("Something went wrong", false));
    }

}
