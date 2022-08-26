package team.keephealth.xyj.modules.cos.service;

import org.springframework.web.multipart.MultipartFile;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;


public interface UploadService {
    ResponseResult uploadFile(MultipartFile file);
}
