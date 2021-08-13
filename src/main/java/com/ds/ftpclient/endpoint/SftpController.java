package com.ds.ftpclient.endpoint;

import com.ds.ftpclient.service.SftpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/sftp")
public class SftpController {

  private final SftpService sftpService;

    @Autowired
    public SftpController(SftpService sftpService) {
        this.sftpService = sftpService;
    }

    @PostMapping("/file")
    public ResponseEntity<?> sendFile(@RequestBody Map<String, Object> data) {
        try {
            sftpService.sendFile(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("An error occurred while sending file to remote server.");
        }
        return ResponseEntity.ok().build();
    }
}
