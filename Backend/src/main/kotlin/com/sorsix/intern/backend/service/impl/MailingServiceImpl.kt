package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.service.MailingService
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailingServiceImpl(
    private val mailSender: JavaMailSender
) : MailingService {
    override fun sendMail(to: String, subject: String, message: String) {
        val mail = SimpleMailMessage()
        mail.from = "LiBib"
        mail.setTo(to)
        mail.subject = subject
        mail.text = message
        mailSender.send(mail)
    }
}