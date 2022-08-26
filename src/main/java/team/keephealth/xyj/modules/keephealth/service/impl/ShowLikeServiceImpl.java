package team.keephealth.xyj.modules.keephealth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.exception.SystemException;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.CheckLikeDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.ShowLikeDto;
import team.keephealth.xyj.modules.keephealth.domain.entity.ShowLike;
import team.keephealth.xyj.modules.keephealth.mapper.ShowLikeMapper;
import team.keephealth.xyj.modules.keephealth.service.ShowLikeService;

import java.util.Objects;

/**
 * (ShowLike)表服务实现类
 *
 * @author xyj
 * @since 2022-08-05 15:50:49
 */
@Service("showLikeService")
public class ShowLikeServiceImpl extends ServiceImpl<ShowLikeMapper, ShowLike> implements ShowLikeService {

    @Override
    public ResponseResult likeShow(ShowLikeDto showLikeDto) {
        Long showId = showLikeDto.getShowId();
        if (Objects.isNull(showId)){
            throw new SystemException(AppHttpCodeEnum.KSLID_NOT_NULL);
        }
        Long userId = SecurityUtils.getUserId();
        ShowLike showLike = new ShowLike(showId,userId);
        try{
            save(showLike);
        }catch (DuplicateKeyException e){
            throw new SystemException(AppHttpCodeEnum.LIKE_EXIST);
        }catch (DataIntegrityViolationException e2) {
            throw new SystemException(AppHttpCodeEnum.KSLID_ERROR);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult cancelLikeShow(Long kslId) {
        if (Objects.isNull(kslId)){
            throw new SystemException(AppHttpCodeEnum.KSLID_NOT_NULL);
        }
        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<ShowLike> updateWrapper = new LambdaQueryWrapper<>();
        updateWrapper.eq(ShowLike::getShowId,kslId);
        updateWrapper.eq(ShowLike::getUserId,userId);
        boolean remove = remove(updateWrapper);
        if (!remove){
            throw new SystemException(AppHttpCodeEnum.LIKE_NOT_EXIST);
        }
        return ResponseResult.okResult();

    }

    @Override
    public ResponseResult checkLike(CheckLikeDto checkLikeDto) {
        Long kslId = checkLikeDto.getKslId();
        if (Objects.isNull(kslId)){
            throw new SystemException(AppHttpCodeEnum.KSLID_NOT_NULL);
        }
        return ResponseResult.okResult("未完成");
    }
}

