package com.justice.justcode.execution;

import com.justice.justcode.dto.CodeRequest;

import java.io.IOException;

public interface CodeExecutor {
    public String execute(CodeRequest codeRequest) throws IOException, InterruptedException;
    public String getLanguage();//return lower case string on implementation
}
