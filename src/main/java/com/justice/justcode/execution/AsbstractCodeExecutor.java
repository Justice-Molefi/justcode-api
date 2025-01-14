package com.justice.justcode.execution;

import com.justice.justcode.dto.CodeRequest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

public abstract class AsbstractCodeExecutor implements CodeExecutor{
    @Override
    public String execute(CodeRequest codeRequest) throws IOException, InterruptedException {

        String filename = "main." + codeRequest.getExtension();
        File program = new File(filename);

        Files.writeString(program.toPath(),codeRequest.getCode());

        try{
            ProcessResults compileResults = compile(filename);
            if(compileResults.exitCode() != 0){
                return "Compilation Failed! Errors: \n" + compileResults.output();
            }

            ProcessResults runResults = run(filename);
            if(runResults.exitCode() != 0){
                return "Program Execution Failed! Errors: \n" + runResults.output();
            }

            return "Program Execution Successful. Output: \n" + runResults.output();
        }finally {
            Files.deleteIfExists(program.toPath());
        }
    }

    protected ProcessResults executeProcess(ProcessBuilder processBuilder) throws IOException, InterruptedException {
        StringBuilder output = new StringBuilder();

        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        int exitCode = process.waitFor();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))){
            String line;
            while((line = reader.readLine()) != null){
                output.append(line).append(System.lineSeparator());
            }
        }

        return new ProcessResults(output.toString().trim(),exitCode);
    }
}
