package team.keephealth.yjj.service.interact.mpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.entity.articles.Article;
import team.keephealth.yjj.domain.entity.articles.Kudos;
import team.keephealth.yjj.domain.entity.articles.KudosRecord;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.articles.ArticleMapper;
import team.keephealth.yjj.mapper.articles.KudosMapper;
import team.keephealth.yjj.mapper.articles.KudosRecordMapper;
import team.keephealth.yjj.service.interact.KudosRecordService;
import team.keephealth.yjj.service.interact.KudosService;

@Service
public class KudosServiceImpl extends ServiceImpl<KudosMapper, Kudos> implements KudosService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private KudosMapper kudosMapper;
    @Autowired
    private KudosRecordMapper kudosRecordMapper;
    @Autowired
    private KudosRecordService kudosRecordService;

    @Override
    public ResultVo addKudos(Long articleId) {
        if (articleId < 1)
            return new ResultVo(AppHttpCodeEnum.DATA_ARTICLE_ID_ERROR, "id : "+articleId, null);
        Article article = articleMapper.selectById(articleId);
        if (article == null)
            return new ResultVo(AppHttpCodeEnum.DATA_ARTICLE_ID_ERROR, "id : "+articleId, null);
        QueryWrapper<Kudos> queryWrapper = getKudos(articleId);
        Kudos k = kudosMapper.selectOne(queryWrapper);
        if (k != null)
            return new ResultVo(AppHttpCodeEnum.KUDOS_EXIST, JSONObject.toJSONString(k), null);
        Kudos kudos = new Kudos();
        kudos.setVisitorId(SecurityUtils.getUserId());
        kudos.setArticleMes(article);
        if (kudosMapper.insert(kudos) > 0) {
            k = kudosMapper.selectOne(queryWrapper);
            KudosRecord record = new KudosRecord(k);
            kudosRecordMapper.insert(record);
            /*kudosRecordService.save(record);*/
            article.setKudosNumber((article.getKudosNumber()+1));
            articleMapper.updateById(article);
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", null);
        }
        else
            return new ResultVo(AppHttpCodeEnum.ERROR, "id : "+articleId, null);
    }

    @Override
    public ResultVo deleteKudos(Long articleId) {
        if (articleId < 1 || articleMapper.selectById(articleId) == null)
            return new ResultVo(AppHttpCodeEnum.DATA_ARTICLE_ID_ERROR, "id : "+articleId, null);
        QueryWrapper<Kudos> queryWrapper = getKudos(articleId);
        if (kudosMapper.selectOne(queryWrapper) == null)
            return new ResultVo(AppHttpCodeEnum.GET_KUDOS_ERROR, "id : "+articleId, null);
        if (kudosMapper.delete(queryWrapper) > 0) {
            Article article = articleMapper.selectById(articleId);
            article.setKudosNumber(article.getKudosNumber() - 1);
            articleMapper.updateById(article);
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", null);
        }
        else
            return new ResultVo(AppHttpCodeEnum.ERROR, "id : "+articleId, null);
    }

    @Override
    public ResultVo kudosState(Long articleId) {
        if (articleId < 1 || articleMapper.selectById(articleId) == null)
            return new ResultVo(AppHttpCodeEnum.DATA_ARTICLE_ID_ERROR, "id : "+articleId, null);
        QueryWrapper<Kudos> queryWrapper = getKudos(articleId);
        Kudos kudos = kudosMapper.selectOne(queryWrapper);
        if (kudos != null)
            return new ResultVo(AppHttpCodeEnum.KUDOS_EXIST, "", true);
        else
            return new ResultVo(AppHttpCodeEnum.GET_KUDOS_ERROR, "id : "+articleId, false);
    }

    public QueryWrapper<Kudos> getKudos(Long articleId){
        QueryWrapper<Kudos> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", articleId);
        queryWrapper.eq("visitor_id", SecurityUtils.getUserId());
        return queryWrapper;
    }
}
