package com.proj.Banking_System.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDetails {
    private String recipient;
    private String subject;
    private String messageBody;
    private String attachment;
    public EmailDetails(String recipient, String subject, String message) {

    }
}
