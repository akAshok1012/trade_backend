package com.tm.app.dto;

import lombok.Data;

@Data
public class ImportCsvResponse {
    private int successCount;
    private int failureCount;
    private byte[] errorCsvData;
}
