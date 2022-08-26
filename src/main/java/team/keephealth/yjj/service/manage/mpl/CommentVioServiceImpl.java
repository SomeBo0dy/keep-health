package team.keephealth.yjj.service.manage.mpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.enums.Inform;
import team.keephealth.yjj.domain.dto.manage.DoDeal;
import team.keephealth.yjj.domain.entity.articles.Comment;
import team.keephealth.yjj.domain.entity.manage.InformComment;
import team.keephealth.yjj.domain.entity.manage.UserViolation;
import team.keephealth.yjj.domain.vo.VioVo;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.articles.CommentMapper;
import team.keephealth.yjj.mapper.manage.InformCommentMapper;
import team.keephealth.yjj.mapper.manage.UserViolationMapper;
import team.keephealth.yjj.service.manage.CommentVioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentVioServiceImpl extends ServiceImpl<InformCommentMapper, InformComment> implements CommentVioService {

    @Autowired
    private InformCommentMapper informCommentMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserViolationMapper violationMapper;
    @Autowired
    private UserVioServiceImpl vioService;

    @Override
    public ResultVo<T> getAll(int deal) {
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", informCommentMapper.getList(deal));
    }

    @Override
    public ResultVo<T> getArticleVio(Long commentId) {
        if (commentId < 1 || commentMapper.selectById(commentId) == null)
            return new ResultVo<>(AppHttpCodeEnum.DATA_COMMENT_ID_ERROR, "id : "+commentId, null);
        List<Integer> types = informCommentMapper.getType(commentId);
        Map<String, Integer> count = new HashMap<>();
        Map<String, String> msg = new HashMap<>();
        for (Integer type : types) {
            QueryWrapper<InformComment> wrapper = new QueryWrapper<>();
            wrapper.eq("comment_id", commentId);
            wrapper.eq("type", type);
            count.put(Inform.getName(type), informCommentMapper.selectCount(wrapper));
        }
        QueryWrapper<InformComment> wrapper = new QueryWrapper<>();
        wrapper.eq("comment_id", commentId);
        wrapper.isNotNull("message");
        wrapper.orderByDesc("id");
        for (InformComment inform : informCommentMapper.selectList(wrapper))
            msg.put(Inform.getName(inform.getType()), inform.getMessage());
        VioVo vio = new VioVo(count, msg);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", vio);
    }

    @Override
    public ResultVo<T> setDeal(DoDeal dto) {
        if (dto.getId() == null || commentMapper.selectById(dto.getId()) == null)
            return new ResultVo<>(AppHttpCodeEnum.DATA_COMMENT_ID_ERROR, "id : "+ dto.getId(), null);
        if (dto.getDeal() == null || dto.getDeal() < 0 || dto.getDeal() > 3)
            return new ResultVo<>(AppHttpCodeEnum.ERROR, "deal : "+ dto.getDeal(), null);
        if (dto.getType() == null || dto.getType() < 0 || dto.getType() > 10)
            return new ResultVo<>(AppHttpCodeEnum.ERROR, "type : "+dto.getType(), null);

        informCommentMapper.haveDeal(dto.getId());
        Comment comment = commentMapper.selectById(dto.getId());
        UserViolation userViolation = vioService.getUserVio(comment.getAccountId());
        if (dto.getDeal() == 0 || dto.getDeal() == 1){
            userViolation = setType(userViolation, dto.getType());
            commentMapper.deleteById(comment.getId());
        }
        if (dto.getDeal() == 3){
            QueryWrapper<InformComment> wrapper = new QueryWrapper<>();
            wrapper.eq("comment_id", dto.getId());
            informCommentMapper.delete(wrapper);
        }
        violationMapper.updateById(userViolation);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", vioService.countVio(userViolation));
    }

    public UserViolation setType(UserViolation userViolation, int type){
        if (type >= 1 && type <= 3) userViolation.setCommentOne(userViolation.getCommentOne() + 1);
        else if (type == 4 || type == 5) userViolation.setCommentTwo(userViolation.getCommentTwo() + 1);
        else userViolation.setCommentThree(userViolation.getCommentThree() + 1);
        return userViolation;
    }
}
