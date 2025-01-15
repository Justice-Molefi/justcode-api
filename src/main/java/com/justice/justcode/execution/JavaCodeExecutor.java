package com.justice.justcode.execution;

import org.springframework.stereotype.Component;
import java.io.IOException;


@Component
public class JavaCodeExecutor extends AsbstractCodeExecutor{
    @Override
    public ProcessResults compile(String filename) throws IOException, InterruptedException {
        ProcessBuilder compileProcess = new ProcessBuilder("javac", filename);
        return executeProcess(compileProcess);
    }

    @Override
    public ProcessResults run(String filename) throws IOException, InterruptedException {
        ProcessBuilder runProcess = new ProcessBuilder("java", filename);
        return executeProcess(runProcess);
    }

    @Override
    public String getLanguage() {
        return "java";
    }
}
