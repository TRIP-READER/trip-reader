package com.tripreader.common.util.Email;

import lombok.Data;

@Data
public class EmailMessage {
    private String to;
    private String subject;
    private String message;
    private boolean containsHtml;
}