package com.ds.ftpclient.configuration;

import com.jcraft.jsch.ChannelSftp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.outbound.SftpMessageHandler;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.messaging.MessageHandler;

import java.io.File;

@Configuration
public class SftpConfiguration {

    @Value("${sftp.host}")
    private String host;

    @Value("${sftp.user}")
    private String user;

    @Value("${sftp.pass}")
    private String pass;

    @Value("${sftp.port}")
    private int port;

    @Value("${sftp.remote.directory}")
    private String remoteDirectory;

    @Bean
    public SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
        try {
            factory.setHost(host);
            factory.setPort(port);
            factory.setUser(user);
            factory.setPassword(pass);
            factory.setAllowUnknownKeys(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new CachingSessionFactory<>(factory);
    }

    @Bean
    @ServiceActivator(inputChannel = "toSftpChannel")
    public MessageHandler handler() {
        SftpMessageHandler handler = new SftpMessageHandler(sftpSessionFactory());
        handler.setRemoteDirectoryExpression(new LiteralExpression(remoteDirectory));
        handler.setFileNameGenerator(message -> {
            if (message.getPayload() instanceof File) return ((File) message.getPayload()).getName();
             else {
                throw new IllegalArgumentException("File expected as payload.");
            }
        });
        return handler;
    }

}
