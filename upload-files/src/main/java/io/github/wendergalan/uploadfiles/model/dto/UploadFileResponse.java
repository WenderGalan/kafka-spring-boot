package io.github.wendergalan.uploadfiles.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The type Upload file response.
 */
@Data
@AllArgsConstructor
public class UploadFileResponse {

    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
}
