package com.example.eagerreader.app.service.export;

import org.springframework.core.io.InputStreamResource;

import java.io.File;
import java.io.IOException;

public interface ExportService {

    void exportAuthorBooks(Long authorId);
    void exportFavoriteBooks();
    InputStreamResource readReport(File file) throws IOException;


}
