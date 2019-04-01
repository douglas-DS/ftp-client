package br.com.douglas.ftpclient.endpoint.controller;

import br.com.douglas.ftpclient.endpoint.service.SftpService;
import br.com.douglas.ftpclient.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/connectorftp")
public class SftpController {

  private final SftpService sftpService;

    @Autowired
    public SftpController(SftpService sftpService) {
        this.sftpService = sftpService;
    }

    @PostMapping(value = "file", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> sendFile(@RequestBody Student student) {
        return sftpService.sendFile(student);
    }
}
