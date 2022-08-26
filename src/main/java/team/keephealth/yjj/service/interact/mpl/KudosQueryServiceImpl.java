package team.keephealth.yjj.service.interact.mpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.xyj.modules.keephealth.mapper.UserMapper;
import team.keephealth.yjj.domain.entity.articles.Kudos;
import team.keephealth.yjj.domain.vo.articles.ArticleKudosList;
import team.keephealth.yjj.domain.vo.articles.MyKudosList;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.articles.ArticleMapper;
import team.keephealth.yjj.mapper.articles.KudosMapper;
import team.keephealth.yjj.service.interact.KudosQueryService;

import java.util.List;

@Service
public class KudosQueryServiceImpl extends ServiceImpl<KudosMapper, Kudos> implements KudosQueryService {

    @Autowired
    private KudosMapper kudosMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultVo getArticleKudos(Long articleId) {
        if (articleId < 1 || articleMapper.selectById(articleId) == null)
            return new ResultVo(AppHttpCodeEnum.DATA_ARTICLE_ID_ERROR, "id : "+articleId, null);
        List<ArticleKudosList> list = kudosMapper.getArticleKudos(articleId);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", list);
    }

    @Override
    public ResultVo getUserKNum(Long accountId) {
        if (accountId < 1 || userMapper.selectById(accountId) == null)
            return new ResultVo(AppHttpCodeEnum.WATCHED_USER_NOT_EXIST, "id : " + accountId, null);
        QueryWrapper<Kudos> queryWrapper = receiveQuery(accountId);
        int num = kudosMapper.selectCount(queryWrapper);
        if (num < 0)
            return new ResultVo(AppHttpCodeEnum.DATA_WRONG, "number : "+num, null);
        else
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", num);
    }

    @Override
    public ResultVo getMyKNum() {
        QueryWrapper<Kudos> queryWrapper = receiveQuery(SecurityUtils.getUserId());
        int num = kudosMapper.selectCount(queryWrapper);
        if (num < 0)
            return new ResultVo(AppHttpCodeEnum.DATA_WRONG, "number : "+num, null);
        else
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", num);
    }

    @Override
    public ResultVo getMyKList() {
        List<MyKudosList> list = kudosMapper.myArticleKudos(SecurityUtils.getUserId());
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", list);
    }

    @Override
    public ResultVo getMyANum() {
        QueryWrapper<Kudos> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", SecurityUtils.getUserId());
        int num = kudosMapper.selectCount(queryWrapper);
        if (num < 0)
            return new ResultVo(AppHttpCodeEnum.DATA_WRONG, "number : "+num, null);
        else
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", num);
    }

    @Override
    public ResultVo getArticleKNum(Long articleId) {
        if (articleId < 1 || articleMapper.selectById(articleId) == null)
            return new ResultVo(AppHttpCodeEnum.DATA_ARTICLE_ID_ERROR, "id : "+articleId, null);
        QueryWrapper<Kudos> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", articleId);
        int num = kudosMapper.selectCount(queryWrapper);
        if (num < 0)
            return new ResultVo(AppHttpCodeEnum.DATA_WRONG, "number : "+num, null);
        else
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", num);
    }

    public QueryWrapper<Kudos> receiveQuery(Long id){
        QueryWrapper<Kudos> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("visitor_id", id);
        queryWrapper.orderByDesc("id");
        return queryWrapper;
    }
}
