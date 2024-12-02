package com.tripreader.ai.util;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

public class PromptTemplateLoader {
    private final Resource systemPromptResource;
    private final Resource userPromptResource;

    public PromptTemplateLoader(String systemPromptResource, String userPromptResource) {
        this.systemPromptResource = new ClassPathResource(systemPromptResource);
        this.userPromptResource = new ClassPathResource(userPromptResource);
    }

    public String loadSystemPrompt() {
        try {
            return new String(FileCopyUtils.copyToByteArray(systemPromptResource.getInputStream()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load system prompt template", e);
        }
    }

    public String loadUserPrompt() {
        try {
            return new String(FileCopyUtils.copyToByteArray(userPromptResource.getInputStream()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load user prompt template", e);
        }
    }

}

