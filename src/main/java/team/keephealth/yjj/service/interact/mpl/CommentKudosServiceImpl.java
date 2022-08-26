package team.keephealth.yjj.service.interact.mpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.entity.articles.Comment;
import team.keephealth.yjj.domain.entity.articles.CommentKudos;
import team.keephealth.yjj.domain.entity.articles.KudosRecord;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.articles.CommentKudosMapper;
import team.keephealth.yjj.mapper.articles.CommentMapper;
import team.keephealth.yjj.mapper.articles.KudosRecordMapper;
import team.keephealth.yjj.service.interact.CommentKudosService;

@Service
public class CommentKudosServiceImpl extends ServiceImpl<CommentKudosMapper, CommentKudos> implements CommentKudosService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentKudosMapper commentKudosMapper;
    @Autowired
    private KudosRecordMapper kudosRecordMapper;

    @Override
    public ResultVo addKudos(Long commentId) {
        Comment comment = commentMapper.selectById(commentId);
        if (commentId < 1 || comment == null)
            return new ResultVo(AppHttpCodeEnum.DATA_COMMENT_ID_ERROR, "comment id : "+commentId, null);
        QueryWrapper<CommentKudos> q = new QueryWrapper<>();
        q.eq("visitor_id", SecurityUtils.getUserId());
        q.eq("comment_id", commentId);
        if (commentKudosMapper.selectOne(q) != null)
            return new ResultVo(AppHttpCodeEnum.KUDOS_EXIST, "comment id : "+commentId, null);

        CommentKudos kudos = new CommentKudos(comment);
        if (commentKudosMapper.insert(kudos) > 0){
            QueryWrapper<CommentKudos> queryWrapper = kudosQuery(kudos.getCommentId(), kudos.getVisitorId());
            queryWrapper.orderByDesc("id");
            queryWrapper.last(" LIMIT 1");
            CommentKudos k = commentKudosMapper.selectOne(queryWrapper);
            KudosRecord record = new KudosRecord(k);
            kudosRecordMapper.insert(record);
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", k);
        }else
            return new ResultVo(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(kudos), null);
    }

    @Override
    public ResultVo deleteKudos(Long commentId) {
        if (commentId < 1 || commentMapper.selectById(commentId) == null)
            return new ResultVo(AppHttpCodeEnum.DATA_COMMENT_ID_ERROR, "comment id : "+commentId, null);
        QueryWrapper<CommentKudos> queryWrapper = kudosQuery(commentId, SecurityUtils.getUserId());
        if (commentKudosMapper.selectOne(queryWrapper) == null)
            return new ResultVo(AppHttpCodeEnum.GET_KUDOS_ERROR, "comment id : "+commentId, null);
        if (commentKudosMapper.delete(queryWrapper) > 0)
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", null);
        else
            return new ResultVo(AppHttpCodeEnum.ERROR, "", null);
    }

    @Override
    public ResultVo kudosState(Long commentId) {
        if (commentId < 1 || commentMapper.selectById(commentId) == null)
            return new ResultVo(AppHttpCodeEnum.DATA_COMMENT_ID_ERROR, "comment id : "+commentId, null);
        QueryWrapper<CommentKudos> queryWrapper = kudosQuery(commentId, SecurityUtils.getUserId());
        if (commentKudosMapper.selectOne(queryWrapper) != null)
            return new ResultVo(AppHttpCodeEnum.KUDOS_EXIST, "", true);
        else
            return new ResultVo(AppHttpCodeEnum.GET_KUDOS_ERROR, "", false);
    }

    public QueryWrapper<CommentKudos> kudosQuery(Long commentId, Long visitorId){
        QueryWrapper<CommentKudos> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("visitor_id", visitorId);
        queryWrapper.eq("comment_id", commentId);
        return queryWrapper;
    }
}
