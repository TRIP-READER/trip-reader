package com.tripreader.ai.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ChatUtil {
    private final ChatModel chatModel;
    private Map<String, PromptTemplateLoader> promptTemplateLoaders;
    @Value("${spring.ai.template.path}")
    private String PROMPT_TEMPLATE_PATH;

    public ChatUtil(ChatModel chatModel) {
        this.chatModel = chatModel;
        promptTemplateLoaders = new HashMap<>();
    }

    public void addPromptLoader(String promptName, String systemPromptFile, String userPromptFile) {
        systemPromptFile = PROMPT_TEMPLATE_PATH + systemPromptFile + ".st";
        userPromptFile = PROMPT_TEMPLATE_PATH + userPromptFile + ".st";

        promptTemplateLoaders.put(promptName, new PromptTemplateLoader(systemPromptFile, userPromptFile));
    }

    public String runPrompt(String promptName, Map<String, Object> userParam, String userTemplateAdder, Map<String, Object> systemParam, String systemTemplateAdder) {
        try {
            PromptTemplateLoader promptTemplateLoader = promptTemplateLoaders.get(promptName);
            // 유저 프롬프트 템플릿 로드 및 변수 설정
            String userPromptTemplate = promptTemplateLoader.loadUserPrompt() + userTemplateAdder;
            PromptTemplate userTemplate = new PromptTemplate(userPromptTemplate);

            //유저 프롬프트 템플릿에 param 설정
            for (Map.Entry<String, Object> entry : userParam.entrySet()) {
                userTemplate.add(entry.getKey(), entry.getValue());
            }
            String userCommand = userTemplate.render();

            // 시스템 프롬프트 로드
            String systemPromptTemplate = promptTemplateLoader.loadSystemPrompt() + systemTemplateAdder;
            PromptTemplate systemTemplate = new PromptTemplate(systemPromptTemplate);

            //시스템 프롬프트 템플릿에 param 설정
            for (Map.Entry<String, Object> entry : systemParam.entrySet()) {
                userTemplate.add(entry.getKey(), entry.getValue());
            }
            String systemCommand = systemTemplate.render();

            // 메시지 생성
            Message userMessage = new UserMessage(userCommand);
            Message systemMessage = new SystemMessage(systemCommand);

            // API 호출
            String response = chatModel.call(userMessage, systemMessage);
            log.info("\npromptName : {} \nResponse: {}", promptName, response);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
