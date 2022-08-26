package team.keephealth.yjj.service.interact.mpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.yjj.domain.dto.CommentQueryDto;
import team.keephealth.yjj.domain.entity.articles.Comment;
import team.keephealth.yjj.domain.vo.articles.CommentVo;
import team.keephealth.yjj.domain.vo.articles.ReplyVo;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.articles.ArticleMapper;
import team.keephealth.yjj.mapper.articles.CommentMapper;
import team.keephealth.yjj.mapper.articles.CommentQueryMapper;
import team.keephealth.yjj.service.interact.CommentQueryService;

import java.util.List;

@Service
public class CommentQueryServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentQueryService {

    @Autowired
    private CommentQueryMapper commentQueryMapper;
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public ResultVo highComment(Long articleId) {
        if (articleId < 1 || articleMapper.selectById(articleId) == null)
            return new ResultVo(AppHttpCodeEnum.DATA_ARTICLE_ID_ERROR, "articleId : "+articleId, null);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", articleId);
        queryWrapper.ge("kudos_number", 10);
        queryWrapper.isNull("reply");
        queryWrapper.orderByDesc("kudos_number", "add_time");
        List<Comment> list = commentQueryMapper.selectList(queryWrapper);
        if (list.size() == 0){
            queryWrapper.clear();
            queryWrapper.eq("article_id", articleId);
            queryWrapper.isNull("reply");
            queryWrapper.orderByDesc("add_time");
            list = commentQueryMapper.selectList(queryWrapper);
        }
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", list);
    }

    @Override
    public ResultVo queryByHeat(CommentQueryDto dto) {
        if (dto.getId() < 1 || articleMapper.selectById(dto.getId()) == null)
            return new ResultVo(AppHttpCodeEnum.DATA_ARTICLE_ID_ERROR, "articleId : "+dto.getId(), null);
        if (dto.getPageSize() < 1)
            return new ResultVo(AppHttpCodeEnum.DATA_PAGESIZE_ERROR, "page size : "+dto.getPageSize(), null);
        if (dto.getCurrentPage() < 1 || isPageRight(dto))
            return new ResultVo(AppHttpCodeEnum.GET_PAGE_ERROR, "current page : "+dto.getCurrentPage(), null);
        List<CommentVo> list = commentQueryMapper.queryByHeat(dto.getId(), (dto.getCurrentPage()-1) * dto.getPageSize(), dto.getPageSize());
        list = addCount(list);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", list);
    }

    @Override
    public ResultVo queryByTime(CommentQueryDto dto) {
        if (dto.getId() < 1 || articleMapper.selectById(dto.getId()) == null)
            return new ResultVo(AppHttpCodeEnum.DATA_ARTICLE_ID_ERROR, "articleId : "+dto.getId(), null);
        if (dto.getPageSize() < 1)
            return new ResultVo(AppHttpCodeEnum.DATA_PAGESIZE_ERROR, "page size : "+dto.getPageSize(), null);
        if (dto.getCurrentPage() < 1 || isPageRight(dto))
            return new ResultVo(AppHttpCodeEnum.GET_PAGE_ERROR, "current page : "+dto.getCurrentPage(), null);
        List<CommentVo> list = commentQueryMapper.queryByTime(dto.getId(), (dto.getCurrentPage()-1) * dto.getPageSize(), dto.getPageSize());
        list = addCount(list);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", list);
    }

    @Override
    public ResultVo queryReply(CommentQueryDto dto) {
        if (dto.getId() < 1 || commentQueryMapper.selectById(dto.getId()) == null)
            return new ResultVo(AppHttpCodeEnum.DATA_COMMENT_ID_ERROR, "commentId : "+dto.getId(), null);
        if (dto.getPageSize() < 1)
            return new ResultVo(AppHttpCodeEnum.DATA_PAGESIZE_ERROR, "page size : "+dto.getPageSize(), null);
        long num = replyNumber(dto.getId());
        if (dto.getCurrentPage() < 1 || (dto.getCurrentPage() > num/dto.getPageSize() + (num%dto.getPageSize() == 0? 0 : 1)))
            return new ResultVo(AppHttpCodeEnum.GET_PAGE_ERROR, "current page : "+dto.getCurrentPage(), null);
        List<ReplyVo> list = commentQueryMapper.queryReply(dto.getId(), (dto.getCurrentPage()-1) * dto.getPageSize(), dto.getPageSize());
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", list);
    }

    @Override
    public ResultVo getNumber(Long articleId) {
        if (articleId < 1 || articleMapper.selectById(articleId) == null)
            return new ResultVo(AppHttpCodeEnum.DATA_ARTICLE_ID_ERROR, "articleId : "+articleId, null);
        long number = allNumber(articleId);
        return numberReturn(number);
    }

    @Override
    public ResultVo getNumberA(Long articleId) {
        if (articleId < 1 || articleMapper.selectById(articleId) == null)
            return new ResultVo(AppHttpCodeEnum.DATA_ARTICLE_ID_ERROR, "articleId : "+articleId, null);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", articleId);
        long number = commentQueryMapper.selectCount(queryWrapper);
        return numberReturn(number);
    }

    @Override
    public ResultVo getReplyNumber(Long commentId) {
        if (commentId < 1 || commentQueryMapper.selectById(commentId) == null)
            return new ResultVo(AppHttpCodeEnum.DATA_COMMENT_ID_ERROR, "commentId : "+commentId, null);
        long number = replyNumber(commentId);
        return numberReturn(number);
    }

    // 不包含回复的评论数
    public long allNumber(Long articleId){
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", articleId);
        queryWrapper.isNull("reply");
        return (long) commentQueryMapper.selectCount(queryWrapper);
    }

    // 该评论的全部回复数
    public long replyNumber(Long commentId){
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("reply", commentId);
        return (long) commentQueryMapper.selectCount(queryWrapper);
    }

    public ResultVo numberReturn(long number){
        if (number < 0)
            return new ResultVo(AppHttpCodeEnum.DATA_WRONG, "number : "+number, null);
        else
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", number);
    }

    public boolean isPageRight(CommentQueryDto dto){
        long num = allNumber(dto.getId());
        return dto.getCurrentPage() > num/dto.getPageSize() + (num%dto.getPageSize() == 0? 0 : 1);
    }

    public List<CommentVo> addCount(List<CommentVo> list){
        for (CommentVo vo: list) {
            QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("reply", vo.getId());
            vo.setCount(commentQueryMapper.selectCount(queryWrapper));
        }
        return list;
    }
}
