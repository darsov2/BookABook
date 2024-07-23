package com.sorsix.intern.backend

import com.sorsix.intern.backend.config.AppProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.Properties

@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
@EnableScheduling
class BackendApplication

fun main(args: Array<String>) {
    runApplication<BackendApplication>(*args)
}
