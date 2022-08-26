package team.keephealth.xyj.modules.keephealth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.exception.SystemException;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.FollowDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.SelectOtherWatchPageDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.SelectUserPageDto;
import team.keephealth.xyj.modules.keephealth.domain.entity.WatchList;
import team.keephealth.xyj.modules.keephealth.domain.vo.PageVo;
import team.keephealth.xyj.modules.keephealth.domain.vo.UserInfoVo;
import team.keephealth.xyj.modules.keephealth.mapper.WatchListMapper;
import team.keephealth.xyj.modules.keephealth.service.WatchListService;

import java.util.List;
import java.util.Objects;

/**
 * 关注列表(WatchList)表服务实现类
 *
 * @author xyj
 * @since 2022-07-29 14:30:06
 */
@Service("watchListService")
public class WatchListServiceImpl extends ServiceImpl<WatchListMapper, WatchList> implements WatchListService {

    @Autowired
    private WatchListMapper watchListMapper;

    @Override
    public ResponseResult followUserById(FollowDto followDto) {
        Long userId = followDto.getUserId();
        if (Objects.isNull(userId)) {
            throw new SystemException(AppHttpCodeEnum.USER_ID_NOT_NULL);
        }
        Long followerId = SecurityUtils.getLoginUser().getUser().getId();
        WatchList watchList = new WatchList(followerId, userId);
        try {
            save(watchList);
        } catch (DuplicateKeyException e1) {
            throw new SystemException(AppHttpCodeEnum.ALREADY_FOLLOWED);
        } catch (DataIntegrityViolationException e2) {
            throw new SystemException(AppHttpCodeEnum.WATCHED_USER_NOT_EXIST);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult unFollowUserById(Long userId) {
        if (Objects.isNull(userId)) {
            throw new SystemException(AppHttpCodeEnum.USER_ID_NOT_NULL);
        }
        Long followerId = SecurityUtils.getLoginUser().getUser().getId();
        LambdaQueryWrapper<WatchList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WatchList::getFollowerId, followerId);
        queryWrapper.eq(WatchList::getWatchedId, userId);
        boolean remove = remove(queryWrapper);
        if (!remove) {
            throw new SystemException(AppHttpCodeEnum.NEVER_WATCHED_USER);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getWatchList(SelectUserPageDto userPageDto) {
        Integer pageNum = userPageDto.getPageNum();
        Integer pageSize = userPageDto.getPageSize();
        String type = userPageDto.getType();
        String state = userPageDto.getState();
        String keyword = userPageDto.getKeyword();
        if (Objects.isNull(pageNum)) {
            throw new SystemException(AppHttpCodeEnum.PAGE_NUM_NOT_NULL);
        }
        if (Objects.isNull(pageSize)) {
            throw new SystemException(AppHttpCodeEnum.PAGE_SIZE_NOT_NULL);
        }
        Long id = SecurityUtils.getLoginUser().getUser().getId();
        IPage<UserInfoVo> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UserInfoVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("kwl.follower_id", id);
        if (StringUtils.hasText(type)) {
            queryWrapper.eq("u.type", type);
        }
        if (StringUtils.hasText(state)) {
            queryWrapper.eq("u.state", state);
        }
        if (StringUtils.hasText(keyword)) {
            queryWrapper.like("u.nick_name", keyword);
        }
        IPage<UserInfoVo> userPage = watchListMapper.getWatchList(page, queryWrapper);
        List<UserInfoVo> records = userPage.getRecords();
        PageVo pageVo = new PageVo(records, userPage.getPages(), userPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getWatchListById(SelectOtherWatchPageDto userPageDto) {
        Integer pageNum = userPageDto.getPageNum();
        Integer pageSize = userPageDto.getPageSize();
        String type = userPageDto.getType();
        String state = userPageDto.getState();
        String keyword = userPageDto.getKeyword();
        Long id = userPageDto.getUserId();
        if (Objects.isNull(pageNum)) {
            throw new SystemException(AppHttpCodeEnum.PAGE_NUM_NOT_NULL);
        }
        if (Objects.isNull(pageSize)) {
            throw new SystemException(AppHttpCodeEnum.PAGE_SIZE_NOT_NULL);
        }
        if (Objects.isNull(id)) {
            throw new SystemException(AppHttpCodeEnum.USER_ID_NOT_NULL);
        }
        IPage<UserInfoVo> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UserInfoVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("kwl.follower_id", id);
        if (StringUtils.hasText(type)) {
            queryWrapper.eq("u.type", type);
        }
        if (StringUtils.hasText(state)) {
            queryWrapper.eq("u.state", state);
        }
        if (StringUtils.hasText(keyword)) {
            queryWrapper.like("u.nick_name", keyword);
        }
        IPage<UserInfoVo> userPage = watchListMapper.getWatchList(page, queryWrapper);
        List<UserInfoVo> records = userPage.getRecords();
        PageVo pageVo = new PageVo(records, userPage.getPages(), userPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}

