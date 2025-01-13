package com.justice.justcode.execution;

import com.fasterxml.jackson.databind.deser.BuilderBasedDeserializer;
import com.justice.justcode.dto.CodeRequest;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.prefs.PreferenceChangeEvent;


@Component
public class JavaCodeExecutor implements CodeExecutor{

    @Override
    public String execute(CodeRequest codeRequest) throws IOException, InterruptedException {
        StringBuilder outPut = new StringBuilder();
        String fileName = "main." + codeRequest.getExtension();
        File file = new File(fileName);

        Files.writeString(file.toPath() ,codeRequest.getCode());

        ProcessBuilder compileProcess = new ProcessBuilder("javac", fileName);
        compileProcess.redirectErrorStream(true);
        Process process = compileProcess.start();
        int compileExitCode = process.waitFor();

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))){
            String line;
            while((line = bufferedReader.readLine()) != null){
                outPut.append(line).append(System.lineSeparator());
            }
        }

        if(compileExitCode != 0){
            return "Compilation failed! Errors: \n" + outPut.toString().trim();
        }

        ProcessBuilder runProcess = new ProcessBuilder("java", fileName);
        process = runProcess.start();
        int runExitCode = process.waitFor();

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))){
            String line;
            while((line = bufferedReader.readLine()) != null){
                outPut.append(line).append(System.lineSeparator());
            }
        }

        if(runExitCode != 0){
            return "Execution failed! Errors: \n" + outPut.toString().trim();
        }

        return "Execution Successful! Output: \n" + outPut.toString().trim();
    }

    @Override
    public String getLanguage() {
        return "java";
    }
}
