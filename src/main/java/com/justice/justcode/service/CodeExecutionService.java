package com.justice.justcode.service;

import com.justice.justcode.dto.CodeExecutionResponse;
import com.justice.justcode.dto.CodeRequest;
import com.justice.justcode.execution.CodeExecutor;
import com.justice.justcode.execution.CodeExecutorFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CodeExecutionService {
    private final CodeExecutorFactory codeExecutorFactory;

    public CodeExecutionService(CodeExecutorFactory codeExecutorFactory) {
        this.codeExecutorFactory = codeExecutorFactory;
    }

    public CodeExecutionResponse excuteCode(CodeRequest codeRequest) throws IOException, InterruptedException {
        CodeExecutor codeExecutor = codeExecutorFactory.getExecutor(codeRequest.getLanguage());

        if(codeExecutor == null){
            throw new IllegalArgumentException("Unsupported Langauge " + codeRequest.getLanguage());
        }
        return codeExecutor.execute(codeRequest);
    }
}
