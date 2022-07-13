package app.pool.project.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PhotoDto {

    private final Long id;
    private final String origFileName;
    private final String filePath;
    private final Long fileSize;

    @Builder

    public PhotoDto(Long id, String origFileName, String filePath, Long fileSize) {
        this.id = id;
        this.origFileName = origFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}
