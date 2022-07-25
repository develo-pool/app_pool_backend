package appool.pool.project.file.service;

import appool.pool.project.file.exception.FileException;
import appool.pool.project.file.exception.FileExceptionType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.dir}")
    private String fileDir;


    @Override
    public String save(MultipartFile multipartFile) {

        int extIdx = multipartFile.getOriginalFilename().lastIndexOf(".");
        String extension = multipartFile.getOriginalFilename().substring(extIdx+1);

        String filePath = fileDir + UUID.randomUUID()+"."+extension;
        try {
            multipartFile.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new FileException(FileExceptionType.FILE_CAN_NOT_SAVE);
        }

        return filePath;
    }

    @Override
    public void delete(String filePath) {
        File file = new File(filePath);

        if(!file.exists()) {
            return;
        }

        if(!file.exists()) {
            throw new FileException(FileExceptionType.FILE_CAN_NOT_DELETE);
        }
    }
}
