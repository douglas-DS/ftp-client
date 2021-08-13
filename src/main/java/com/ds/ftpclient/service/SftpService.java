package com.ds.ftpclient.service;

import com.ds.ftpclient.core.CsvFileGenerator;
import com.ds.ftpclient.core.UploadGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class SftpService {
    private final UploadGateway uploadGateway;
    private final CsvFileGenerator csvFileGenerator;

    @Autowired
    public SftpService(UploadGateway uploadGateway,
                       CsvFileGenerator csvFileGenerator) {
        this.uploadGateway = uploadGateway;
        this.csvFileGenerator = csvFileGenerator;
    }

    public void sendFile(Map<String, Object> data) {
        try {
            uploadGateway.upload(csvFileGenerator.createFile(data));
            log.info("File successfully uploaded to remote sftp server.");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            csvFileGenerator.eraseFile();
        }
    }

}
