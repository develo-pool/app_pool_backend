package appool.pool.project.file.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileServiceTest {

    @Autowired FileService fileService;

    private MockMultipartFile getMockUploadFile() throws IOException {
        return new MockMultipartFile("file", "file.png", "image/png", new FileInputStream("/Users/johnn/Desktop/img/diva.png"));
    }

    @Test
    public void 파일_저장_성공() throws Exception {
        // given, when
        String filePath = fileService.save(getMockUploadFile());

        // then
        File file = new File(filePath);
        assertThat(file.exists()).isTrue();

        // finally
        file.delete();
    }

}