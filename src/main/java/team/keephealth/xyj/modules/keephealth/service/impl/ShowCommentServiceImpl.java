package team.keephealth.xyj.modules.keephealth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import team.keephealth.xyj.modules.keephealth.domain.dto.AddCommentDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.GetCommentDto;
import team.keephealth.xyj.modules.keephealth.domain.entity.ShowComment;
import team.keephealth.xyj.modules.keephealth.domain.vo.PageVo;
import team.keephealth.xyj.modules.keephealth.domain.vo.ShowCommentVo;
import team.keephealth.xyj.modules.keephealth.mapper.ShowCommentMapper;
import team.keephealth.xyj.modules.keephealth.mapper.UserMapper;
import team.keephealth.xyj.modules.keephealth.service.ShowCommentService;

import java.util.List;
import java.util.Objects;

import static team.keephealth.common.constants.SystemConstants.ROOT_ID;

/**
 * 评论表(ShowComment)表服务实现类
 *
 * @author xyj
 * @since 2022-08-01 16:30:14
 */
@Service("showCommentService")
public class ShowCommentServiceImpl extends ServiceImpl<ShowCommentMapper, ShowComment> implements ShowCommentService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public ResponseResult commentList(GetCommentDto commentDto) {
        Integer pageNum = commentDto.getPageNum();
        Integer pageSize = commentDto.getPageSize();
        Long kslId = commentDto.getKslId();
        if (Objects.isNull(pageNum)){
            throw new SystemException(AppHttpCodeEnum.PAGE_NUM_NOT_NULL);
        }
        if (Objects.isNull(pageSize)){
            throw new SystemException(AppHttpCodeEnum.PAGE_SIZE_NOT_NULL);
        }
        if (Objects.isNull(kslId)){
            throw new SystemException(AppHttpCodeEnum.KSLID_NOT_NULL);
        }
        LambdaQueryWrapper<ShowComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShowComment::getShowId,kslId);
        queryWrapper.orderByDesc(ShowComment::getCreateTime);
        //获得根评论
        queryWrapper.eq(ShowComment::getRootId,ROOT_ID);
        IPage<ShowComment> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);

        List<ShowCommentVo> commentVoList = toCommentVo(page.getRecords());

        for (ShowCommentVo showCommentVo : commentVoList){
            List<ShowCommentVo> children = getChildren(showCommentVo.getId());
            showCommentVo.setChildren(children);
        }

        //查询子评论
        PageVo pageVo = new PageVo(commentVoList,page.getPages(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addComment(AddCommentDto addCommentDto) {
        Long toCommentId = addCommentDto.getToCommentId();
        Long toCommentUserId = addCommentDto.getToCommentUserId();
        Long showId = addCommentDto.getShowId();
        String content = addCommentDto.getContent();
        Long rootId = addCommentDto.getRootId();
        if (Objects.isNull(toCommentId)){
            throw new SystemException(AppHttpCodeEnum.COMMENT_ID_NOT_NULL);
        }
        if (Objects.isNull(toCommentUserId)){
            throw new SystemException(AppHttpCodeEnum.USER_ID_NOT_NULL);
        }
        if (Objects.isNull(showId)){
            throw new SystemException(AppHttpCodeEnum.KSLID_NOT_NULL);
        }
        if (!StringUtils.hasText(content)){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        if (Objects.isNull(rootId)){
            throw new SystemException(AppHttpCodeEnum.ROOT_ID_NOT_NULL);
        }
        ShowComment showComment = BeanCopyUtils.copeBean(addCommentDto, ShowComment.class);
        save(showComment);
        return ResponseResult.okResult();
    }

    /**
     * 查询子评论集合
     * @param id
     * @return
     */
    private List<ShowCommentVo> getChildren(Long id) {
        LambdaQueryWrapper<ShowComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShowComment::getRootId,id);
        //按创造时间正序
        queryWrapper.orderByAsc(ShowComment::getCreateTime);
        List<ShowComment> comments = list(queryWrapper);

        List<ShowCommentVo> showCommentVos = toCommentVo(comments);

        return showCommentVos;
    }

    private List<ShowCommentVo> toCommentVo(List<ShowComment> records) {
        List<ShowCommentVo> showCommentVos = BeanCopyUtils.copyBeanList(records, ShowCommentVo.class);
        
        for (ShowCommentVo vo : showCommentVos){
            //查询评论者用户名
            String nickName = userMapper.selectById(vo.getCreateBy()).getNickName();
            vo.setUserName(nickName);
            //查询评论的评论者id
            if (vo.getToCommentUserId() != ROOT_ID){
                String toCommentUserName = userMapper.selectById(vo.getToCommentUserId()).getNickName();
                vo.setToCommentUserName(toCommentUserName);
            }
        }
        return showCommentVos;
    }
}

