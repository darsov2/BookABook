package com.sorsix.intern.backend.service

interface MailingService {
    fun sendMail(to: String, subject: String, message: String);
}