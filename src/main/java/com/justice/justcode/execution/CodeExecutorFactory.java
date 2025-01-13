package com.justice.justcode.execution;

import com.justice.justcode.dto.CodeRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CodeExecutorFactory {
    private final Map<String, CodeExecutor> executors = new HashMap<>();

    public CodeExecutorFactory(List<CodeExecutor> codeExecutorList){
        for(CodeExecutor codeExecutor: codeExecutorList){
            executors.put(codeExecutor.getLanguage(), codeExecutor);
        }
    }

    public CodeExecutor getExecutor(String language){
        return  executors.getOrDefault(language.toLowerCase(), null);
    }
}
