package com.example.Integradoraturismo.service;


import com.azure.communication.email.EmailClient;
import com.azure.communication.email.EmailClientBuilder;
import com.azure.communication.email.models.EmailAddress;
import com.azure.communication.email.models.EmailAttachment;
import com.azure.communication.email.models.EmailMessage;
import com.azure.communication.email.models.EmailSendResult;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private final EmailClient emailClient;

    public EmailService(@Value("${azure.communication.email.endpoint}") String endpoint,
                        @Value("${azure.communication.email.access-key}") String accessKey) {
        this.emailClient = new EmailClientBuilder()
                .connectionString(String.format("endpoint=%s;accesskey=%s", endpoint, accessKey))
                .buildClient();
    }

    public String sendEmail(String to, String subject, String plainTextBody, String htmlBody, List<EmailAttachment> attachments) {
        try {
            // Crear destinatario
            EmailAddress toAddress = new EmailAddress(to);

            // Crear el mensaje
            EmailMessage emailMessage = new EmailMessage()
                    .setSenderAddress("DoNotReply@4ecb8a36-2fb8-45bd-bb67-a32cfdbd2346.azurecomm.net") // Cambia esto por el remitente configurado en Azure
                    .setToRecipients(toAddress)
                    .setSubject(subject)
                    .setBodyPlainText(plainTextBody)
                    .setBodyHtml(htmlBody);

            // Agregar adjuntos (si los hay)
            if (attachments != null) {
                emailMessage.getAttachments().addAll(attachments);
            }

            // Enviar el correo
            SyncPoller<EmailSendResult, EmailSendResult> poller = emailClient.beginSend(emailMessage, null);
            PollResponse<EmailSendResult> result = poller.waitForCompletion();

            if (result.getStatus().isComplete()) {
                return "Email sent successfully. Message ID: " + result.getValue().getId();
            } else {
                throw new RuntimeException("Failed to send email. Status: " + result.getStatus());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error sending email: " + e.getMessage(), e);
        }
    }
}
