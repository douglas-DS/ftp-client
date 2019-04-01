package br.com.douglas.ftpclient.endpoint.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@MessagingGateway
public interface UploadGateway {
    @Gateway(requestChannel = "toSftpChannel")
    void upload(File file);
}