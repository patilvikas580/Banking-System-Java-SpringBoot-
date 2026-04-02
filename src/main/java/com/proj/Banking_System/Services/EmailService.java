package com.proj.Banking_System.Services;

import com.proj.Banking_System.DTO.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
    void sendEmailAttachment(EmailDetails emailDetails);

}
