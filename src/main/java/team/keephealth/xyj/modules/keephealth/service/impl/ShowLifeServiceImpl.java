package team.keephealth.xyj.modules.keephealth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.exception.SystemException;
import team.keephealth.common.utils.BeanCopyUtils;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.*;
import team.keephealth.xyj.modules.keephealth.domain.entity.ShowLife;
import team.keephealth.xyj.modules.keephealth.domain.entity.ShowLike;
import team.keephealth.xyj.modules.keephealth.domain.entity.User;
import team.keephealth.xyj.modules.keephealth.domain.vo.PageVo;
import team.keephealth.xyj.modules.keephealth.domain.vo.ShowLifeVo;
import team.keephealth.xyj.modules.keephealth.mapper.ShowLifeMapper;
import team.keephealth.xyj.modules.keephealth.mapper.ShowLikeMapper;
import team.keephealth.xyj.modules.keephealth.mapper.UserMapper;
import team.keephealth.xyj.modules.keephealth.service.ShowLifeService;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static team.keephealth.common.constants.SystemConstants.*;

/**
 * 用户动态表(ShowLife)表服务实现类
 *
 * @author xyj
 * @since 2022-07-29 19:54:58
 */
@Service("showLifeService")
public class ShowLifeServiceImpl extends ServiceImpl<ShowLifeMapper, ShowLife> implements ShowLifeService {

    @Autowired
    private ShowLifeMapper showLifeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ShowLikeMapper likeMapper;
    @Override
    public ResponseResult addShowLife(ShowLifeDto showLifeDto) {
        if (!StringUtils.hasText(showLifeDto.getText())) {
            throw new SystemException(AppHttpCodeEnum.TEXT_NOT_NULL);
        }
        Long id = SecurityUtils.getLoginUser().getUser().getId();
        ShowLife showLife = BeanCopyUtils.copeBean(showLifeDto, ShowLife.class);
        showLife.setUserId(id);
        save(showLife);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteShowLife(Long kslId) {
        if (Objects.isNull(kslId)) {
            throw new SystemException(AppHttpCodeEnum.KSLID_NOT_NULL);
        }
        Long id = SecurityUtils.getLoginUser().getUser().getId();
        LambdaQueryWrapper<ShowLife> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShowLife::getKslId, kslId);
        queryWrapper.eq(ShowLife::getUserId, id);
        boolean remove = remove(queryWrapper);
        if (!remove) {
            throw new SystemException(AppHttpCodeEnum.KSLID_ERROR);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getShowLifeById(Long kslId) {
        if (Objects.isNull(kslId)) {
            throw new SystemException(AppHttpCodeEnum.KSLID_NOT_NULL);
        }
        LambdaQueryWrapper<ShowLife> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShowLife::getKslId, kslId);
        queryWrapper.eq(ShowLife::getState, STATUS_NORMAL);
        //获取对应的动态
        ShowLife one = getOne(queryWrapper);
        Long userId = one.getUserId();
        //通过用户id查询用户
        User user = userMapper.selectById(userId);
        //封装
        ShowLifeVo showLifeVo = BeanCopyUtils.copeBean(one, ShowLifeVo.class);
        showLifeVo.setUserName(user.getNickName());
        showLifeVo.setUserAvatar(user.getAvatar());
        return ResponseResult.okResult(showLifeVo);
    }

    @Override
    public ResponseResult getShowLifeByUserId(UserSelectShowDto showDto) {
        Integer pageNum = showDto.getPageNum();
        Integer pageSize = showDto.getPageSize();
        Long userId = showDto.getUserId();
        String startTime = showDto.getStartTime();
        String endTime = showDto.getEndTime();
        if (Objects.isNull(pageNum)) {
            throw new SystemException(AppHttpCodeEnum.PAGE_NUM_NOT_NULL);
        }
        if (Objects.isNull(pageSize)) {
            throw new SystemException(AppHttpCodeEnum.PAGE_SIZE_NOT_NULL);
        }
        if (!StringUtils.hasText(showDto.getTimeOrder())) {
            throw new SystemException(AppHttpCodeEnum.TIME_ORDER_NOT_NULL);
        }
        if (!showDto.getTimeOrder().equals(DESC) && !showDto.getTimeOrder().equals(ASC)) {
            throw new SystemException(AppHttpCodeEnum.TIME_ORDER_ERROR);
        }
        IPage<ShowLifeVo> page = new Page<>(pageNum, pageSize);
        QueryWrapper<ShowLifeVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ksl.state", STATUS_NORMAL);
        if (Objects.nonNull(userId)) {
            queryWrapper.eq("ksl.user_id", userId);
        }
        //时间范围搜索
        queryWrapper.apply(StringUtils.hasText(startTime),
                        "date_format (ksl.create_time, '%Y-%m-%d %H:%i:%s') >= date_format('" + startTime + "','%Y-%m-%d %H:%i:%s')")
                .apply(StringUtils.hasText(endTime),
                        "date_format (ksl.create_time, '%Y-%m-%d %H:%i:%s') <= date_format('" + endTime + "','%Y-%m-%d %H:%i:%s')");
        //按发布时间排序
        if (showDto.getTimeOrder().equals(DESC)) {
            queryWrapper.orderByDesc("ksl.create_time");
        } else {
            queryWrapper.orderByAsc("ksl.create_time");
        }
        IPage<ShowLifeVo> showPage = showLifeMapper.getShowLife(page, queryWrapper);
        List<ShowLifeVo> records = showPage.getRecords();
        //添加点赞状态字段flag
        addLikeState(records);
        PageVo pageVo = new PageVo(records, showPage.getPages(), showPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    private void addLikeState(List<ShowLifeVo> records) {
        Long userId = SecurityUtils.getUserId();
        Long showId = null;
        LambdaQueryWrapper<ShowLike> queryWrapper = null;
        for (ShowLifeVo vo : records) {
            showId = vo.getKslId();
            queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ShowLike::getShowId,showId);
            queryWrapper.eq(ShowLike::getUserId,userId);
            ShowLike showLike = likeMapper.selectOne(queryWrapper);
            if (Objects.isNull(showLike)){
                vo.setFlag(false);
            }else {
                vo.setFlag(true);
            }
        }
    }

    @Override
    public ResponseResult getShowInfoByPage(SysSelectShowDto showDto) {
        Integer pageNum = showDto.getPageNum();
        Integer pageSize = showDto.getPageSize();
        String order = showDto.getTimeOrder();
        String startTime = showDto.getStartTime();
        String endTime = showDto.getEndTime();
        Long userId = showDto.getUserId();
        String state = showDto.getState();
        if (Objects.isNull(pageNum)) {
            throw new SystemException(AppHttpCodeEnum.PAGE_NUM_NOT_NULL);
        }
        if (Objects.isNull(pageSize)) {
            throw new SystemException(AppHttpCodeEnum.PAGE_SIZE_NOT_NULL);
        }
        if (!StringUtils.hasText(order)) {
            throw new SystemException(AppHttpCodeEnum.TIME_ORDER_NOT_NULL);
        }
        if (!order.equals(DESC) && !order.equals(ASC)) {
            throw new SystemException(AppHttpCodeEnum.TIME_ORDER_ERROR);
        }

        IPage<ShowLifeVo> page = new Page<>(pageNum, pageSize);
        Long id = SecurityUtils.getLoginUser().getUser().getId();
        QueryWrapper<ShowLifeVo> queryWrapper = new QueryWrapper<>();
        if (StringUtils.hasText(state)) {
            if (state.equals(STATUS_NORMAL)||state.equals(STATUS_BLOCK)) {
                queryWrapper.eq("ksl.state", state);
            }else {
                throw new SystemException(AppHttpCodeEnum.STATE_TYPE_ERROR);
            }
        }
        if (Objects.nonNull(userId)) {
            queryWrapper.eq("ksl.user_id", userId);
        }
        //时间范围搜索
        queryWrapper.apply(StringUtils.hasText(startTime),
                "date_format (ksl.create_time, '%Y-%m-%d %H:%i:%s') >= date_format('" + startTime + "','%Y-%m-%d %H:%i:%s')")
                .apply(StringUtils.hasText(endTime),
                        "date_format (ksl.create_time, '%Y-%m-%d %H:%i:%s') <= date_format('" + endTime + "','%Y-%m-%d %H:%i:%s')");
        //按发布时间排序
        if (order.equals(DESC)) {
            queryWrapper.orderByDesc("ksl.create_time");
        } else {
            queryWrapper.orderByAsc("ksl.create_time");
        }
        IPage<ShowLifeVo> showPage = showLifeMapper.getShowLife(page, queryWrapper);
        List<ShowLifeVo> records = showPage.getRecords();
        PageVo pageVo = new PageVo(records, showPage.getPages(), showPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getShowLife(MyShowLifeDto showDto) {
        Integer pageNum = showDto.getPageNum();
        Integer pageSize = showDto.getPageSize();
        String order = showDto.getTimeOrder();
        if (Objects.isNull(pageNum)) {
            throw new SystemException(AppHttpCodeEnum.PAGE_NUM_NOT_NULL);
        }
        if (Objects.isNull(pageSize)) {
            throw new SystemException(AppHttpCodeEnum.PAGE_SIZE_NOT_NULL);
        }
        if (!StringUtils.hasText(order)) {
            throw new SystemException(AppHttpCodeEnum.TIME_ORDER_NOT_NULL);
        }
        if (!order.equals(DESC) && !order.equals(ASC)) {
            throw new SystemException(AppHttpCodeEnum.TIME_ORDER_ERROR);
        }
        IPage<ShowLifeVo> page = new Page<>(pageNum, pageSize);
        Long id = SecurityUtils.getLoginUser().getUser().getId();
        QueryWrapper<ShowLifeVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ksl.state", STATUS_NORMAL);
        queryWrapper.eq("ksl.user_id", id);
        //按发布时间排序
        if (order.equals(DESC)) {
            queryWrapper.orderByDesc("ksl.create_time");
        } else {
            queryWrapper.orderByAsc("ksl.create_time");
        }
        IPage<ShowLifeVo> showPage = showLifeMapper.getShowLife(page, queryWrapper);
        List<ShowLifeVo> records = showPage.getRecords();
        //增加检验字段
        addLikeState(records);
        PageVo pageVo = new PageVo(records, showPage.getPages(), showPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult setShowState(StateDto stateDto) {
        String toState = stateDto.getToState();
        Long id = stateDto.getId();
        if (!StringUtils.hasText(toState)){
            throw new SystemException(AppHttpCodeEnum.STATE_TYPE_NOT_NULL);
        }
        if (Objects.isNull(toState)){
            throw new SystemException(AppHttpCodeEnum.USER_ID_NOT_NULL);
        }
        if (!toState.equals(STATUS_NORMAL) && !toState.equals(STATUS_BLOCK)) {
            throw new SystemException(AppHttpCodeEnum.STATE_TYPE_ERROR);
        }
        LambdaUpdateWrapper<ShowLife> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ShowLife::getKslId, id);
        updateWrapper.set(ShowLife::getState, toState);
        boolean update = update(updateWrapper);
        if (!update){
            throw new SystemException(AppHttpCodeEnum.KSLID_ERROR);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult sysCancelShow(Long kslId) {
        if (Objects.isNull(kslId)) {
            throw new SystemException(AppHttpCodeEnum.KSLID_NOT_NULL);
        }
        boolean result = removeById(kslId);
        if (!result) {
            throw new SystemException(AppHttpCodeEnum.ERROR);
        }
        return ResponseResult.okResult();
    }
}

