package appool.pool.project.message.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MessageSearch {

    private static final int MAX_SIZE = 2000;

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    public long getOffset() {
        return (long) (Math.max(1, page) - 1) * Math.min(size, MAX_SIZE);
    }
}
