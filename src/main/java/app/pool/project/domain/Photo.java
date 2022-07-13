package app.pool.project.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "file")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Message message;

    @Column(nullable = false)
    private String origFileName;  // 파일 원본명

    @Column(nullable = false)
    private String filePath;  // 파일 저장 경로

    private Long fileSize;

    @Builder
    public Photo(String origFileName, String filePath, Long fileSize) {
        this.origFileName = origFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    // message 정보 저장
    public void setMessage(Message message) {
        this.message = message;

        // 메시지에 현재 파일이 존재하지 않는다면
        if (!message.getPhoto().contains(this)) {
            // 파일 추가
            message.getPhoto().add(this);
        }
    }



}
