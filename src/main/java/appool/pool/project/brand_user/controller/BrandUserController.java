package appool.pool.project.brand_user.controller;

import appool.pool.project.brand_user.dto.BrandUserCreateDto;
import appool.pool.project.brand_user.dto.BrandUserInfoDto;
import appool.pool.project.brand_user.dto.BrandUserUpdate;
import appool.pool.project.brand_user.dto.SlackRequestDto;
import appool.pool.project.brand_user.service.BrandUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BrandUserController {

    private final BrandUserService brandUserService;
    private static final int PAGE_DEFAULT_SIZE = 10;

    @PostMapping("/brand/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void submit(@Valid @ModelAttribute BrandUserCreateDto brandUserCreateDto, @RequestPart(required = false) MultipartFile multipartFile) throws Exception{
        brandUserService.submit(brandUserCreateDto, multipartFile);
    }

    @PostMapping("/brand/create/request")
    public void brandRequest(@ModelAttribute SlackRequestDto slackRequestDto) {
        brandUserService.request(slackRequestDto);
    }

    @GetMapping("/brand/{id}")
    public ResponseEntity getBrandInfo(@Valid @PathVariable("id") Long id) {
        BrandUserInfoDto info = brandUserService.getBrandInfo(id);
        return new ResponseEntity(info, HttpStatus.OK);
    }

    @GetMapping("/brand/{id}/web")
    public ResponseEntity getBrandInfoWeb(@Valid @PathVariable("id") Long id) {
        BrandUserInfoDto info = brandUserService.getBrandInfoWeb(id);
        return new ResponseEntity(info, HttpStatus.OK);
    }

    @GetMapping("/brand")
    public ResponseEntity getMyBrandInfo() {
        BrandUserInfoDto info = brandUserService.getMyBrandInfo();
        return new ResponseEntity(info, HttpStatus.OK);
    }

    @GetMapping("/brands")
    public List<BrandUserInfoDto> getBrandList(Long cursor) {
        return brandUserService.getBrands(cursor, PageRequest.of(0, PAGE_DEFAULT_SIZE));
    }

    @GetMapping("/brands/web")
    public List<BrandUserInfoDto> getBrandListWeb(Long cursor) {
        return brandUserService.getBrandsWeb(cursor, PageRequest.of(0, PAGE_DEFAULT_SIZE));
    }

    @GetMapping("/brands/web/recommend")
    public List<BrandUserInfoDto> getBrandRecommendWeb() {
        return brandUserService.getBrandsRecommendWeb();
    }

    @GetMapping("/brand-brandUsernames/{brandUsername}/exists")
    public ResponseEntity<Boolean> checkBrandUsernameDuplicate(@PathVariable String brandUsername) {
        return ResponseEntity.ok(brandUserService.checkBrandUsernameDuplicate(brandUsername));
    }

    @PutMapping("/brand/update")
    public void updateBrandInfo(@ModelAttribute BrandUserUpdate brandUserUpdate, @RequestPart(required = false) MultipartFile multipartFile) {
        brandUserService.updateBrandInfo(brandUserUpdate, multipartFile);
    }
}
