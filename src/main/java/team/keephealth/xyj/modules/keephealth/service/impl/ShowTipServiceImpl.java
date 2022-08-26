package team.keephealth.xyj.modules.keephealth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.exception.SystemException;
import team.keephealth.common.utils.BeanCopyUtils;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.GetPageDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.GetTipPageDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.TipShowDto;
import team.keephealth.xyj.modules.keephealth.domain.entity.ShowLife;
import team.keephealth.xyj.modules.keephealth.domain.entity.ShowTip;
import team.keephealth.xyj.modules.keephealth.domain.entity.User;
import team.keephealth.xyj.modules.keephealth.domain.vo.PageVo;
import team.keephealth.xyj.modules.keephealth.domain.vo.ShowLifeVo;
import team.keephealth.xyj.modules.keephealth.domain.vo.ShowTipListVo;
import team.keephealth.xyj.modules.keephealth.domain.vo.ShowTipVo;
import team.keephealth.xyj.modules.keephealth.mapper.ShowLifeMapper;
import team.keephealth.xyj.modules.keephealth.mapper.ShowTipMapper;
import team.keephealth.xyj.modules.keephealth.mapper.UserMapper;
import team.keephealth.xyj.modules.keephealth.service.ShowTipService;

import java.util.List;
import java.util.Objects;

import static team.keephealth.common.constants.SystemConstants.DESC;
import static team.keephealth.common.constants.SystemConstants.STATUS_NORMAL;

/**
 * 动态举报表(ShowTip)表服务实现类
 *
 * @author xyj
 * @since 2022-08-04 10:34:51
 */
@Service("showTipService")
public class ShowTipServiceImpl extends ServiceImpl<ShowTipMapper, ShowTip> implements ShowTipService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ShowLifeMapper showLifeMapper;
    @Autowired
    private ShowTipMapper showTipMapper;
    @Override
    public ResponseResult addShowLifeTip(TipShowDto tipShowDto) {
        Long kslId = tipShowDto.getKslId();
        String tipContent = tipShowDto.getTipContent();
        if (Objects.isNull(kslId)){
            throw new SystemException(AppHttpCodeEnum.KSLID_NOT_NULL);
        }
        if (!StringUtils.hasText(tipContent)){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        ShowTip showTip = BeanCopyUtils.copeBean(tipShowDto, ShowTip.class);
        save(showTip);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult tipList(GetPageDto getTipDto) {
        Integer pageNum = getTipDto.getPageNum();
        Integer pageSize = getTipDto.getPageSize();
        if (Objects.isNull(pageSize)) {
            throw new SystemException(AppHttpCodeEnum.PAGE_SIZE_NOT_NULL);
        }
        if (Objects.isNull(pageNum)) {
            throw new SystemException(AppHttpCodeEnum.PAGE_NUM_NOT_NULL);
        }
        IPage<ShowTipListVo> page = new Page<>(pageNum, pageSize);

        IPage<ShowTipListVo> tipPage = showTipMapper.tipList(page);
        List<ShowTipListVo> records = tipPage.getRecords();
        PageVo pageVo = new PageVo(records, tipPage.getPages(), tipPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getTipById(Long id) {
        ShowTip tip = getById(id);
        ShowTipVo showTipVo = BeanCopyUtils.copeBean(tip, ShowTipVo.class);
        Long kslId = tip.getKslId();
        //获得对应动态
        ShowLife showLife = showLifeMapper.selectById(kslId);
        //获得发动态者
        User send = userMapper.selectById(showLife.getUserId());
        showTipVo.setKslId(kslId);
        showTipVo.setKslContent(showLife.getText());
        showTipVo.setKslUserName(send.getNickName());
        showTipVo.setTipperName(userMapper.selectById(tip.getCreateBy()).getNickName());
        return ResponseResult.okResult(showTipVo);
    }

    @Override
    public ResponseResult deleteTip(Long kstId) {
        boolean result = removeById(kstId);
        if (!result){
            throw new SystemException(AppHttpCodeEnum.KSTID_ERROR);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTipByShowId(GetTipPageDto tipPageDto) {
        Integer pageNum = tipPageDto.getPageNum();
        Integer pageSize = tipPageDto.getPageSize();
        Long kslId = tipPageDto.getKslId();
        if (Objects.isNull(pageSize)) {
            throw new SystemException(AppHttpCodeEnum.PAGE_SIZE_NOT_NULL);
        }
        if (Objects.isNull(pageNum)) {
            throw new SystemException(AppHttpCodeEnum.PAGE_NUM_NOT_NULL);
        }
        if (Objects.isNull(kslId)) {
            throw new SystemException(AppHttpCodeEnum.KSLID_NOT_NULL);
        }
        IPage<ShowTipVo> page = new Page<>(pageNum, pageSize);
        QueryWrapper<ShowTipVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("kst.ksl_id",kslId);
        IPage<ShowTipVo> tipPage = showTipMapper.getTipPageInfo(page, queryWrapper);
        List<ShowTipVo> records = tipPage.getRecords();
        PageVo pageVo = new PageVo(records, tipPage.getPages(), tipPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult deleteTipByKslId(Long kslId) {
        if (Objects.isNull(kslId)){
            throw new SystemException(AppHttpCodeEnum.KSLID_NOT_NULL);
        }
        LambdaQueryWrapper<ShowTip> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShowTip::getKslId,kslId);
        boolean remove = remove(queryWrapper);
        if (!remove){
            throw new SystemException(AppHttpCodeEnum.KSLID_TIP_ERROR);
        }
        return ResponseResult.okResult();
    }
}

