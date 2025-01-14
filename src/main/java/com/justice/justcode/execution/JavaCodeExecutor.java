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
