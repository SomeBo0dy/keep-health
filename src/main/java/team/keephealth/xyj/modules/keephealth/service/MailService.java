package team.keephealth.xyj.modules.keephealth.service;

import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.BindEmailDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.EmailLoginDto;

public interface MailService {
    ResponseResult sendEmailCode(String email);

    ResponseResult bindEmail(BindEmailDto bindEmailDto);

    ResponseResult sendLoginEmailCode(EmailLoginDto emailLoginDto);
}
