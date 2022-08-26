package team.keephealth.yjj.service.interact.mpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.dto.DeleteListDto;
import team.keephealth.yjj.domain.entity.articles.Comment;
import team.keephealth.yjj.domain.entity.articles.CommentRecord;
import team.keephealth.yjj.domain.vo.articles.MyCommentVo;
import team.keephealth.yjj.domain.vo.articles.MyReceiveComment;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.articles.CommentQueryMapper;
import team.keephealth.yjj.mapper.articles.CommentRecordMapper;
import team.keephealth.yjj.service.interact.MyCommentService;

import java.util.List;

@Service
public class MyCommentServiceImpl extends ServiceImpl<CommentQueryMapper, Comment> implements MyCommentService {

    @Autowired
    private CommentQueryMapper commentQueryMapper;
    @Autowired
    private CommentRecordMapper commentRecordMapper;

    @Override
    public ResultVo mineSendAll() {
        Long id = SecurityUtils.getUserId();
        List<MyCommentVo> list = commentQueryMapper.mySend(id);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", list);
    }

    @Override
    public ResultVo mineReceiveAll() {
        Long id = SecurityUtils.getUserId();
        List<MyReceiveComment> list = commentQueryMapper.myReceive(id);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", list);
    }

    @Override
    public ResultVo deleteByList(DeleteListDto dto) {
        if (commentQueryMapper.deleteBatchIds(dto.getIds()) > 0)
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", null);
        else
            return new ResultVo(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(dto.getIds()), null);
    }

    @Override
    public ResultVo newCommentNum() {
        Long id = SecurityUtils.getUserId();
        QueryWrapper<CommentRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", id);
        queryWrapper.eq("msg_read", 0);
        Long num = (long)commentRecordMapper.selectCount(queryWrapper);
        if (num < 0)
            return new ResultVo(AppHttpCodeEnum.DATA_WRONG, "num : "+num, null);
        else
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", num);
    }

    @Override
    public ResultVo newCommentList() {
        Long id = SecurityUtils.getUserId();
        List<MyReceiveComment> list = commentQueryMapper.myReceiveNew(id);
        commentQueryMapper.updateRead(id);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", list);
    }
}
