package com.netty.communication.controller;

import com.netty.communication.dto.FileDownloadDto;
import com.netty.communication.dto.FileUploadDto;
import com.netty.communication.service.FileClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling file upload and download requests.
 * Delegates the actual file handling operations to the {@link FileClient} service.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class FileClientController {

    private final FileClient fileClient;

    /**
     * Handles file download requests.
     * This method takes a {@link FileDownloadDto} specification and instructs the {@link FileClient}
     * to download the file according to the provided specification.
     *
     * @param spec the specification for the file download
     * @return {@link ResponseEntity} indicating the operation's success
     * @throws Exception if any errors occur during the download process
     */
    @PostMapping("/download")
    public ResponseEntity<Void> downloadFile(@RequestBody FileDownloadDto spec) throws Exception {
        fileClient.downloadFile(spec);
        return ResponseEntity.ok().build();
    }

    /**
     * Handles file upload requests.
     * This method takes a {@link FileUploadDto} specification and instructs the {@link FileClient}
     * to upload the file according to the provided specification.
     *
     * @param spec the specification for the file upload
     * @return {@link ResponseEntity} indicating the operation's success
     * @throws Exception if any errors occur during the upload process
     */
    @PostMapping("/upload")
    public ResponseEntity<Void> uploadFile(@RequestBody FileUploadDto spec) throws Exception {
        fileClient.uploadFile(spec);
        return ResponseEntity.ok().build();
    }
}
