package team.keephealth.yjj.service.manage.mpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.enums.Inform;
import team.keephealth.yjj.domain.dto.manage.DoDeal;
import team.keephealth.yjj.domain.entity.articles.Article;
import team.keephealth.yjj.domain.entity.manage.InformArticle;
import team.keephealth.yjj.domain.entity.manage.UserViolation;
import team.keephealth.yjj.domain.vo.VioVo;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.articles.ArticleMapper;
import team.keephealth.yjj.mapper.manage.InformArticleMapper;
import team.keephealth.yjj.mapper.manage.UserViolationMapper;
import team.keephealth.yjj.service.manage.ArticleVioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleVioServiceImpl extends ServiceImpl<InformArticleMapper, InformArticle> implements ArticleVioService {

    @Autowired
    private InformArticleMapper informArticleMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserViolationMapper violationMapper;
    @Autowired
    private UserVioServiceImpl vioService;

    @Override
    public ResultVo<T> getAll(int deal) {
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", informArticleMapper.getList(deal));
    }

    @Override
    public ResultVo<T> getArticleVio(Long articleId) {
        if (articleId < 1 || articleMapper.selectById(articleId) == null)
            return new ResultVo<>(AppHttpCodeEnum.DATA_ARTICLE_ID_ERROR, "id : "+articleId, null);
        List<Integer> types = informArticleMapper.getType(articleId);
        Map<String, Integer> count = new HashMap<>();
        Map<String, String> msg = new HashMap<>();
        for (Integer type : types) {
            QueryWrapper<InformArticle> wrapper = new QueryWrapper<>();
            wrapper.eq("article_id", articleId);
            wrapper.eq("type", type);
            count.put(Inform.getName(type), informArticleMapper.selectCount(wrapper));
        }
        QueryWrapper<InformArticle> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId);
        wrapper.isNotNull("message");
        wrapper.orderByDesc("id");
        for (InformArticle inform : informArticleMapper.selectList(wrapper))
            msg.put(Inform.getName(inform.getType()), inform.getMessage());
        VioVo vio = new VioVo(count, msg);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", vio);
    }

    @Override
    public ResultVo<T> setDeal(DoDeal dto) {
        if (dto.getId() == null || articleMapper.selectById(dto.getId()) == null)
            return new ResultVo<>(AppHttpCodeEnum.DATA_ARTICLE_ID_ERROR, "id : "+ dto.getId(), null);
        if (dto.getDeal() == null || dto.getDeal() < 0 || dto.getDeal() > 3)
            return new ResultVo<>(AppHttpCodeEnum.ERROR, "deal : "+ dto.getDeal(), null);
        if (dto.getType() == null || dto.getType() < 0 || dto.getType() > 10)
            return new ResultVo<>(AppHttpCodeEnum.ERROR, "type : "+dto.getType(), null);

        informArticleMapper.haveDeal(dto.getId());
        Article article = articleMapper.selectById(dto.getId());
        UserViolation userViolation = vioService.getUserVio(article.getAccountId());
        if (dto.getDeal() == 0){
            article.setArticleCheck(2);
            article.setCheckOpinion(Inform.getName(dto.getType()));
            userViolation = setType(userViolation, dto.getType());
            articleMapper.updateById(article);
        }
        if (dto.getDeal() == 1){
            userViolation = setType(userViolation, dto.getType());
            articleMapper.deleteById(article.getId());
        }
        if (dto.getDeal() == 3){
            QueryWrapper<InformArticle> wrapper = new QueryWrapper<>();
            wrapper.eq("article_id", dto.getId());
            informArticleMapper.delete(wrapper);
        }
        violationMapper.updateById(userViolation);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", vioService.countVio(userViolation));
    }

    public UserViolation setType(UserViolation userViolation, int type){
        if (type >= 1 && type <= 3) userViolation.setArticleOne(userViolation.getArticleOne() + 1);
        else if (type == 4 || type == 5) userViolation.setArticleTwo(userViolation.getArticleTwo() + 1);
        else userViolation.setArticleThree(userViolation.getArticleThree() + 1);
        return userViolation;
    }
}
