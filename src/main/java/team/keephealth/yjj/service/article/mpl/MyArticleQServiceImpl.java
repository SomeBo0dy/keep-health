package team.keephealth.yjj.service.article.mpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.dto.article.MArticleQKeyDto;
import team.keephealth.yjj.domain.dto.article.MArticleQueryDto;
import team.keephealth.yjj.domain.entity.articles.Article;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.articles.ArticleQueryMapper;
import team.keephealth.yjj.service.article.MyArticleQueService;

@Service
public class MyArticleQServiceImpl extends ServiceImpl<ArticleQueryMapper, Article> implements MyArticleQueService {

    @Autowired
    private ArticleQueryMapper articleQueryMapper;
    @Autowired
    private ArticleNumberServiceImpl articleNumberService;
    @Autowired
    private ArticleQueryServiceImpl articleQueryService;

    @Override
    public ResultVo queryMAAll(MArticleQueryDto dto) {
        if (dto.getKind() > 2 || dto.getKind() < 0)
            return new ResultVo(AppHttpCodeEnum.DATA_KIND_ERROR, JSONObject.toJSONString(dto), null);
        if (dto.getDirection() > 1 || dto.getDirection() < 0)
            return new ResultVo(AppHttpCodeEnum.DATA_DIRECTION_ERROR, JSONObject.toJSONString(dto), null);
        if (dto.getState() < 0 || dto.getState() > 2)
            return new ResultVo(AppHttpCodeEnum.DATA_STATE_ERROR, JSONObject.toJSONString(dto), null);
        if (dto.getPageSize() < 1)
            return new ResultVo(AppHttpCodeEnum.DATA_PAGESIZE_ERROR, "page size : "+dto.getPageSize(), null);
        if (dto.getCurrentPage() < 1 || isPageRight(dto, null))
            return new ResultVo(AppHttpCodeEnum.GET_PAGE_ERROR, "current page : "+dto.getCurrentPage(), null);

        Long id = SecurityUtils.getUserId();
        QueryWrapper<Article> queryWrapper = sortQuery(dto, id);

        queryWrapper.apply(StringUtils.hasText(dto.getStartTime()),
                "date_format (update_time, '%Y-%m-%d') >= date_format('" + dto.getStartTime() + "','%Y-%m-%d')")
                .apply(StringUtils.hasText(dto.getEndTime()),
                        "date_format (update_time, '%Y-%m-%d') <= date_format('" + dto.getEndTime() + "','%Y-%m-%d')");
        Page<Article> page = new Page<Article>(dto.getCurrentPage(), dto.getPageSize());
        Page<Article> list = articleQueryMapper.selectPage(page, queryWrapper);
        if (list != null)
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", articleQueryService.setKudos(list));
        else
            return new ResultVo(AppHttpCodeEnum.DATA_WRONG, JSONObject.toJSONString(dto), null);
    }

    @Override
    public ResultVo queryMAKey(MArticleQKeyDto dto) {
        if (dto.getKind() > 2 || dto.getKind() < 0)
            return new ResultVo(AppHttpCodeEnum.DATA_KIND_ERROR, JSONObject.toJSONString(dto), null);
        if (dto.getDirection() > 1 || dto.getDirection() < 0)
            return new ResultVo(AppHttpCodeEnum.DATA_DIRECTION_ERROR, JSONObject.toJSONString(dto), null);
        if (dto.getState() < 0 || dto.getState() > 2)
            return new ResultVo(AppHttpCodeEnum.DATA_STATE_ERROR, JSONObject.toJSONString(dto), null);
        if (dto.getPageSize() < 1)
            return new ResultVo(AppHttpCodeEnum.DATA_PAGESIZE_ERROR, "page size : "+dto.getPageSize(), null);
        if (dto.getCurrentPage() < 1 || isPageRight(dto, dto.getKeyword()))
            return new ResultVo(AppHttpCodeEnum.GET_PAGE_ERROR, "current page : "+dto.getCurrentPage(), null);

        Long id = SecurityUtils.getUserId();
        QueryWrapper<Article> queryWrapper = sortQuery(dto, id);
        queryWrapper.like("title", dto.getKeyword());
        Page<Article> page = new Page<Article>(dto.getCurrentPage(), dto.getPageSize());
        Page<Article> list = articleQueryMapper.selectPage(page, queryWrapper);
        if (list != null)
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", articleQueryService.setKudos(list));
        else
            return new ResultVo(AppHttpCodeEnum.DATA_WRONG, JSONObject.toJSONString(dto), null);
    }

    public QueryWrapper<Article> sortQuery(MArticleQueryDto dto, Long id){
        QueryWrapper<Article> queryWrapper = articleQueryService.sortQuery(dto.getKind(),  dto.getDirection());
        queryWrapper.eq("account_id", id);
        queryWrapper.eq("article_check", dto.getState());
        return queryWrapper;
    }

    public boolean isPageRight(MArticleQueryDto dto, String keyword){
        long num;
        if (keyword != null) num = articleNumberService.getMANum(dto.getState(), keyword);
        else num = articleNumberService.getMANum(dto.getState(), null);
        return dto.getCurrentPage() > num/dto.getPageSize() + (num%dto.getPageSize() == 0? 0 : 1);
    }
}
