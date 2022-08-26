package team.keephealth.xyj.modules.cos.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import team.keephealth.xyj.modules.cos.service.UploadService;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;


@Api(value = "UploadControllerApi", tags = {"上传文件操作接口"})
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @ApiOperation(value = "上传图片（png，jpg），文本文件（txt）(<5MB)")
    @PostMapping("/file")
    @PreAuthorize("hasAuthority('system:file:upload')")
    public ResponseResult uploadFile(MultipartFile file) {
        return uploadService.uploadFile(file);
    }
}

