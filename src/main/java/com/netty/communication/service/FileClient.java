package com.netty.communication.service;

import com.netty.communication.dto.FileDownloadDto;
import com.netty.communication.dto.FileUploadDto;

/**
 * Interface representing a file client that provides methods for downloading and uploading files.
 */
public interface FileClient {

    /**
     * Downloads a file based on the specified download parameters.
     *
     * @param spec the specification containing details for the file download
     * @throws Exception if an error occurs during the file download process
     */
    void downloadFile(FileDownloadDto spec) throws Exception;

    /**
     * Uploads a file based on the specified upload parameters.
     *
     * @param spec the specification containing details for the file upload
     * @throws Exception if an error occurs during the file upload process
     */
    void uploadFile(FileUploadDto spec) throws Exception;
}
