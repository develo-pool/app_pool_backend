package appool.pool.project.user;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserStatus {

    BRAND_USER("BRAND_USER"),
    WAITING("WAITING"),
    USER("USER");


    private final String type;

    public String value() {
        return type;
    }

}
