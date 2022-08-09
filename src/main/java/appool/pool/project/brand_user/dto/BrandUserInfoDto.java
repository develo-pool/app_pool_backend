package appool.pool.project.brand_user.dto;

import appool.pool.project.brand_user.BrandUser;
import appool.pool.project.user.PoolUser;
import appool.pool.project.user.dto.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BrandUserInfoDto {

    private String brandUsername;
    private String brandInfo;
    private String brandProfileImage;
    private UserInfoDto userInfoDto;
}
