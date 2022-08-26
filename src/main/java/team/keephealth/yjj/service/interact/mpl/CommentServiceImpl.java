package team.keephealth.yjj.service.interact.mpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.dto.CommentInfoDto;
import team.keephealth.yjj.domain.dto.manage.InformDto;
import team.keephealth.yjj.domain.entity.articles.Comment;
import team.keephealth.yjj.domain.entity.articles.CommentRecord;
import team.keephealth.yjj.domain.entity.manage.InformComment;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.domain.vo.articles.VisitCommentVo;
import team.keephealth.yjj.mapper.articles.*;
import team.keephealth.yjj.mapper.manage.InformCommentMapper;
import team.keephealth.yjj.service.interact.CommentService;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private InformCommentMapper informCommentMapper;
    @Autowired
    private CommentRecordMapper commentRecordMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private CommentQueryMapper commentQueryMapper;

    @Override
    public ResultVo addComment(CommentInfoDto dto) {
        if (dto.getArticleId() == null || dto.getArticleId() < 1 || articleMapper.selectById(dto.getArticleId()) == null)
            return new ResultVo(AppHttpCodeEnum.DATA_ARTICLE_ID_ERROR, "articleId : "+dto.getArticleId(), null);
        if (dto.getWords() == null || dto.getWords().equals(""))
            return new ResultVo(AppHttpCodeEnum.EMPTY_COMMENT, "comment : "+dto.getWords(), null);
        Comment comment = new Comment();
        comment.addC(dto);
        if (commentMapper.insert(comment) > 0) {
            QueryWrapper<Comment> query = new QueryWrapper<>();
            query.eq("account_id", comment.getAccountId());
            query.eq("article_id", comment.getArticleId());
            query.eq("words", comment.getWords());
            query.orderByDesc("id");
            query.last("LIMIT 1");
            Comment c = commentMapper.selectOne(query);
            CommentRecord record = new CommentRecord();
            System.out.println(c);
            record.setComment(articleMapper.selectById(dto.getArticleId()).getAccountId(), c.getId());
            commentRecordMapper.insert(record);
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "评论添加成功", c);
        }
        else
            return new ResultVo(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(comment), null);
    }

    @Override
    public ResultVo replyComment(CommentInfoDto dto) {
        if (dto.getWords() == null || dto.getWords().equals(""))
            return new ResultVo(AppHttpCodeEnum.EMPTY_COMMENT, "comment : "+dto.getWords(), null);
        if (dto.getReply() == null || dto.getReply() < 1 || commentMapper.selectById(dto.getReply()) == null)
            return new ResultVo(AppHttpCodeEnum.DATA_REPLY_ID_ERROR, "reply : "+dto.getReply(), null);
        Comment comment = new Comment();
        Comment r = commentMapper.selectById(dto.getReply());
        if (r.getReply() != null)
            return new ResultVo(AppHttpCodeEnum.GET_REPLY_ERROR, "reply : "+r.getReply(), null);
        if (dto.getArticleId() == null)
            dto.setArticleId(r.getArticleId());
        comment.addReply(dto);
        if (commentMapper.insert(comment) > 0) {
            QueryWrapper<Comment> query = new QueryWrapper<>();
            query.eq("account_id", comment.getAccountId());
            query.eq("reply", comment.getReply());
            query.eq("words", comment.getWords());
            query.orderByDesc("id");
            query.last("LIMIT 1");
            Comment c = commentMapper.selectOne(query);
            CommentRecord record = new CommentRecord();
            record.setReply(r.getAccountId(), c.getId());
            commentRecordMapper.insert(record);
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "评论添加成功", c);
        }
        else
            return new ResultVo(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(comment), null);
    }

    @Override
    public ResultVo deleteComment(Long commentId) {
        if (commentId < 1)
            return new ResultVo(AppHttpCodeEnum.DATA_COMMENT_ID_ERROR, "id : "+commentId, null);
        Comment comment = commentMapper.selectById(commentId);
        if (comment == null)
            return new ResultVo(AppHttpCodeEnum.DATA_COMMENT_ID_ERROR, "id : "+commentId, null);
        if (!comment.getAccountId().equals(SecurityUtils.getUserId()))
            return new ResultVo(AppHttpCodeEnum.NO_OPERATOR_AUTH, "user : "+SecurityUtils.getUserId()+
                    " comment user : "+comment.getAccountId(), null);
        if (commentMapper.deleteById(commentId) > 0)
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", null);
        else
            return new ResultVo(AppHttpCodeEnum.ERROR, "id : "+commentId, null);
    }

    @Override
    public ResultVo complaintComment(InformDto dto) {
        if (dto.getInformId() < 1 || commentMapper.selectById(dto.getInformId()) == null)
            return new ResultVo(AppHttpCodeEnum.DATA_COMMENT_ID_ERROR, "id : " + dto.getInformId(), null);
        if (dto.getType() < 0 || dto.getType() > 10)
            return new ResultVo(AppHttpCodeEnum.DATA_KIND_ERROR, "type : " + dto.getType(), null);
        if (dto.getType() == 0 && dto.getMessage() == null || dto.getMessage().equals(""))
            return new ResultVo(AppHttpCodeEnum.EMPTY_MESSAGE, "message : " + dto.getMessage(), null);
        InformComment comment = new InformComment(dto);
        if (informCommentMapper.insert(comment) > 0)
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", null);
        else
            return new ResultVo(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(comment), null);
    }

    @Override
    public ResultVo visitComment(Long commentId) {
        if (commentId < 1 || commentMapper.selectById(commentId) == null)
            return new ResultVo(AppHttpCodeEnum.DATA_COMMENT_ID_ERROR, "comment id : "+commentId, null);
        Comment comment = commentMapper.selectById(commentId);
        VisitCommentVo visitCommentVo = new VisitCommentVo();
        visitCommentVo.setContentVo(contentMapper.contentDetail(comment.getArticleId()));

        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", comment.getArticleId());
        queryWrapper.isNull("reply");
        queryWrapper.ge("kudos_number", 10);
        queryWrapper.orderByDesc("kudos_number", "add_time");
        List<Comment> list = commentQueryMapper.selectList(queryWrapper);
        if (list.size() == 0){
            queryWrapper.clear();
            queryWrapper.eq("article_id", comment.getArticleId());
            queryWrapper.isNull("reply");
            queryWrapper.orderByDesc("add_time");
            list = commentQueryMapper.selectList(queryWrapper);
        }

        visitCommentVo.setComments(list);
        if (comment.getReply() == null || comment.getReply() < 1)
            visitCommentVo.setCommentVo(commentMapper.getAComment(commentId));
        else
            visitCommentVo.setCommentVo(commentMapper.getAComment(comment.getReply()));
        QueryWrapper<Comment> count = new QueryWrapper<>();
        count.eq("reply", visitCommentVo.getCommentVo().getId());
        visitCommentVo.getCommentVo().setCount(commentQueryMapper.selectCount(count));
        visitCommentVo.setReply(commentQueryMapper.queryReply(commentId, 0, 10));
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", visitCommentVo);
    }
}
