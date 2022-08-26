package team.keephealth.yjj.service.manage.mpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.xyj.modules.keephealth.mapper.UserMapper;
import team.keephealth.yjj.domain.dto.manage.ArticleCheckDto;
import team.keephealth.yjj.domain.dto.manage.ArticleCheckQuery;
import team.keephealth.yjj.domain.entity.articles.Article;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.articles.ArticleMapper;
import team.keephealth.yjj.service.manage.ArticleManageService;

@Service
public class ArticleManagerServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleManageService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultVo<T> updateCheck(ArticleCheckDto dto) {
        System.out.println(dto);
        if (dto.getArticleId() == null || dto.getArticleId() < 1 || articleMapper.selectById(dto.getArticleId()) == null)
            return new ResultVo<T>(AppHttpCodeEnum.DATA_ARTICLE_ID_ERROR, "article id : "+dto.getArticleId(), null);
        if (dto.getCheck() < 0 || dto.getCheck() > 2)
            return new ResultVo<T>(AppHttpCodeEnum.DATA_CHECK_ERROR, "check : "+dto.getCheck(), null);
        if (dto.getCheck() == 0)
            return new ResultVo<T>(AppHttpCodeEnum.GET_CHECK_ERROR, "check : "+dto.getCheck(), null);
        Article article = new Article(dto);
        if (articleMapper.updateById(article) > 0)
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", articleMapper.selectById(dto.getArticleId()));
        else
            return new ResultVo<T>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(dto), null);
    }

    @Override
    public ResultVo<Long> getCheckNum() {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_check", 0);
        return numberReturn((long)articleMapper.selectCount(queryWrapper));
    }

    @Override
    public ResultVo<Long> getUserCNum(Long accountId) {
        if (accountId < 1 || userMapper.selectById(accountId) == null)
            return new ResultVo<>(AppHttpCodeEnum.WATCHED_USER_NOT_EXIST, "account id : "+accountId, null);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_check", 0);
        queryWrapper.eq("account_id", accountId);
        return numberReturn((long)articleMapper.selectCount(queryWrapper));
    }

    @Override
    public ResultVo<Long> getUserPNum(Long accountId) {
        if (accountId < 1 || userMapper.selectById(accountId) == null)
            return new ResultVo<>(AppHttpCodeEnum.WATCHED_USER_NOT_EXIST, "account id : "+accountId, null);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_check", 2);
        queryWrapper.eq("account_id", accountId);
        return numberReturn((long)articleMapper.selectCount(queryWrapper));
    }

    @Override
    public ResultVo getCheckList(ArticleCheckQuery query, boolean isUser) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_check", 0);
        queryWrapper.orderByDesc("id");
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", articleMapper.selectList(queryWrapper));
    }

    @Override
    public ResultVo getPassList(ArticleCheckQuery query, boolean isUser) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_check", 2);
        queryWrapper.orderByDesc("id");
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", articleMapper.selectList(queryWrapper));
    }

    @Override
    public ResultVo<T> getList(int check) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_check", check);
        queryWrapper.orderByDesc("id");
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", articleMapper.selectList(queryWrapper));
    }

    public ResultVo<Long> numberReturn(Long num){
        if (num >= 0 )
            return new ResultVo<Long>(AppHttpCodeEnum.SUCCESS, "", num);
        else
            return new ResultVo<Long>(AppHttpCodeEnum.DATA_WRONG, "", num);
    }
}
