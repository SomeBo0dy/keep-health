package team.keephealth.yjj.service.article.mpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.dto.article.MArticleKeyNumDto;
import team.keephealth.yjj.domain.dto.article.UArticleKeyNumDto;
import team.keephealth.yjj.domain.entity.articles.Article;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.articles.ArticleNumberMapper;
import team.keephealth.yjj.service.article.ArticleNumberService;

@Service
public class ArticleNumberServiceImpl extends ServiceImpl<ArticleNumberMapper, Article> implements ArticleNumberService {

    @Autowired
    private ArticleNumberMapper articleNumberMapper;

    @Override
    public ResultVo Number() {
        //Long num = articleNumberMapper.getDataNumber();
        Long num = getNumber();
        return numberReturn(num);
    }

    @Override
    public ResultVo KeyNumber(String keyword) {
        Long num = getKeyNumber(keyword);
        return numberReturn(num);
    }

    @Override
    public ResultVo UNumber(Long id){
        if (id < 1)
            return new ResultVo(AppHttpCodeEnum.USER_ID_NOT_NULL, "id : " + id, null);
        return numberReturn(getUANum(id, null));
    }

    @Override
    public ResultVo KeyUNumber(UArticleKeyNumDto dto) {
        if (dto.getKeyword() == null || dto.getKeyword().equals(""))
            return new ResultVo(AppHttpCodeEnum.EMPTY_KEYWORD, JSONObject.toJSONString(dto), null);
        if (dto.getId() < 1)
            return new ResultVo(AppHttpCodeEnum.USER_ID_NOT_NULL, "id : " + dto.getId(), null);
        return numberReturn(getUANum(dto.getId(), dto.getKeyword()));
    }

    @Override
    public ResultVo ANumber(int state) {
        if (state < 0 || state > 2)
            return new ResultVo(AppHttpCodeEnum.DATA_STATE_ERROR, "state : "+state, null);
        return numberReturn(getMANum(state, null));
    }

    @Override
    public ResultVo AKeyNum(MArticleKeyNumDto dto) {
        if (dto.getKeyword() == null || dto.getKeyword().equals(""))
            return new ResultVo(AppHttpCodeEnum.EMPTY_KEYWORD, JSONObject.toJSONString(dto), null);
        if (dto.getState() < 0 || dto.getState() > 2)
            return new ResultVo(AppHttpCodeEnum.DATA_STATE_ERROR, JSONObject.toJSONString(dto), null);
        return numberReturn(getMANum(dto.getState(), dto.getKeyword()));
    }

    public ResultVo numberReturn(Long num){
        if (num >= 0 )
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", num);
        else
            return new ResultVo(AppHttpCodeEnum.DATA_WRONG, "", num);
    }

    public long getNumber(){
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_check", 1);
        return (long)articleNumberMapper.selectCount(null);
    }

    public long getKeyNumber(String keyword){
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_check", 1);
        queryWrapper.like("title", keyword).or().like("account", keyword);
        return articleNumberMapper.selectCount(queryWrapper);
    }

    /**
     *  用户文章数量查询
     * @param id : 查询的用户id
     * @param keyword : 关键词，不用时null
     * @return
     */
    public long getUANum(Long id, String keyword){
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_check", 1);
        queryWrapper.eq("account_id", id);
        if (keyword != null)
            queryWrapper.like("title", keyword);
        return articleNumberMapper.selectCount(queryWrapper);
    }

    /**
     *  个人文章数量查询
     * @param state : 文章状态 0未审核， 1通过， 2不通过
     * @param keyword : 关键词，不用时null
     * @return
     */
    public long getMANum(int state, String keyword){
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", SecurityUtils.getUserId());
        queryWrapper.eq("article_check", state);
        if (keyword != null)
            queryWrapper.like("title", keyword);
        return articleNumberMapper.selectCount(queryWrapper);
    }
}
