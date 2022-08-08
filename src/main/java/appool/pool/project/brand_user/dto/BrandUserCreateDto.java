package appool.pool.project.brand_user.dto;

import appool.pool.project.brand_user.BrandUser;

import java.util.List;

public record BrandUserCreateDto(String brandUsername,
                                 String brandInfo,
                                 List<String> brandCategory,
                                 Boolean brandAgreement
                                 ) {

    public BrandUser toEntity() {
        return BrandUser.builder()
                .brandUsername(brandUsername)
                .brandInfo(brandInfo)
                .brandCategory(brandCategory)
                .brandAgreement(brandAgreement)
                .build();
    }
}
