package br.com.douglas.ftpclient.endpoint.service;

import br.com.douglas.ftpclient.model.Student;
import br.com.douglas.ftpclient.service.SftpConfiguration;
import br.com.douglas.ftpclient.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SftpService {
    private final SftpConfiguration sftpConfiguration;
    private final  UploadGateway uploadGateway;

    @Autowired
    public SftpService(SftpConfiguration sftpConfiguration, UploadGateway uploadGateway) {
        this.sftpConfiguration = sftpConfiguration;
        this.uploadGateway = uploadGateway;
    }
    public ResponseEntity<Student> sendFile(Student student) {
        FileUtils fileUtils = new FileUtils();
        try {
            sftpConfiguration.sftpSessionFactory();
            uploadGateway.upload(fileUtils.createFile(student));
            log.info("File successfully uploaded to remote sftp server!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(student, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            fileUtils.eraseFile();
        }
        return ResponseEntity.ok(student);
    }
}
