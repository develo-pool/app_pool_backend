package appool.pool.project.file.service;

import appool.pool.project.file.exception.FileException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String save(MultipartFile multipartFile) throws FileException;

    void delete(String filePath);
}
