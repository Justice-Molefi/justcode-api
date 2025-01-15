package com.justice.justcode.execution;

import com.justice.justcode.dto.CodeExecutionResponse;
import com.justice.justcode.dto.CodeRequest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

public abstract class AsbstractCodeExecutor implements CodeExecutor{
    @Override
    public CodeExecutionResponse execute(CodeRequest codeRequest) throws IOException, InterruptedException {
        String output;
        String filename = "Main." + codeRequest.getExtension();
        File program = new File(filename);

        Files.writeString(program.toPath(),codeRequest.getCode());

        try{
            ProcessResults compileResults = compile(filename);
            if(compileResults.exitCode() != 0){
                output = "Compilation Failed!<br>Errors: <br>" + compileResults.output();
                return new CodeExecutionResponse(output, false);
            }

            ProcessResults runResults = run(filename);
            if(runResults.exitCode() != 0){
                output = "Program Execution Failed!<br>Errors: <br>" + runResults.output();
                return new CodeExecutionResponse(output, false);
            }
            output = "Program Execution Successful!<br>Output: <br>" + runResults.output();
            return new CodeExecutionResponse(output, true);
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
