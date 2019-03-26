package br.com.douglas.ftpclient.endpoint;

import br.com.douglas.ftpclient.model.Student;
import br.com.douglas.ftpclient.service.SftpServices;
import br.com.douglas.ftpclient.service.UploadGateway;
import br.com.douglas.ftpclient.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/connectorftp/v1")
public class SftpController {

    private SftpServices sftpServices;
    private UploadGateway gateway;
    private FileUtils utils;

    private static final Logger logger = LoggerFactory.getLogger(SftpController.class);

    @Autowired
    public SftpController(SftpServices sftpServices, UploadGateway gateway, FileUtils utils) {
        this.sftpServices = sftpServices;
        this.gateway = gateway;
        this.utils = utils;
    }

    @PostMapping(value = "file", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> sendFile(@RequestBody Student student) {
        HttpStatus status = HttpStatus.CREATED;

        try {
            sftpServices.sftpSessionFactory();
            gateway.upload(utils.createFile(student));
        } catch (Exception e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.error(status.toString());
        } finally {
            logger.info("File successfully uploaded to remote sftp server!");
            utils.eraseFile();
        }

        return new ResponseEntity<>(student, status);
    }

}
