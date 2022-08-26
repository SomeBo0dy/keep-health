package team.keephealth.yjj.service.article.mpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.yjj.domain.dto.article.ArticleQKeyDto;
import team.keephealth.yjj.domain.dto.article.ArticleQueryDto;
import team.keephealth.yjj.domain.entity.articles.Article;
import team.keephealth.yjj.domain.entity.articles.Kudos;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.domain.vo.articles.ArticleVo;
import team.keephealth.yjj.mapper.articles.ArticleQueryMapper;
import team.keephealth.yjj.mapper.articles.KudosMapper;
import team.keephealth.yjj.service.article.ArticleQueryService;
import team.keephealth.yjj.service.interact.mpl.KudosServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleQueryServiceImpl extends ServiceImpl<ArticleQueryMapper, Article> implements ArticleQueryService  {

    @Autowired
    private ArticleQueryMapper articleQueryMapper;
    @Autowired
    private ArticleNumberServiceImpl articleNumberService;
    @Autowired
    private KudosServiceImpl kudosService;
    @Autowired
    private KudosMapper kudosMapper;

    @Override
    public ResultVo queryByUser(ArticleQueryDto dto){
        return queryList(dto, true);
    }

    @Override
    public ResultVo queryByAll(ArticleQueryDto dto){
        return queryList(dto, false);
    }

    @Override
    public ResultVo queryByKeyword(ArticleQKeyDto dto) {
        return queryKeyword(dto, false);
    }

    @Override
    public ResultVo queryByUKey(ArticleQKeyDto dto) {
        return queryKeyword(dto, true);
    }


    public ResultVo queryList(ArticleQueryDto dto, boolean byUser){
        if (byUser && (dto.getId() == null || dto.getId() <= 0))
            return new ResultVo(AppHttpCodeEnum.USER_ID_NOT_NULL, JSONObject.toJSONString(dto), null);
        if (dto.getKind() > 2 || dto.getKind() < 0)
            return new ResultVo(AppHttpCodeEnum.DATA_KIND_ERROR, JSONObject.toJSONString(dto), null);
        if (dto.getDirection() > 1 || dto.getDirection() < 0)
            return new ResultVo(AppHttpCodeEnum.DATA_DIRECTION_ERROR, JSONObject.toJSONString(dto), null);
        if (dto.getPageSize() < 1)
            return new ResultVo(AppHttpCodeEnum.DATA_PAGESIZE_ERROR, "page size : "+dto.getPageSize(), null);
        if (dto.getCurrentPage() < 1 || isPageRight(dto.getPageSize(), dto.getCurrentPage(), null))
            return new ResultVo(AppHttpCodeEnum.GET_PAGE_ERROR, "current page : "+dto.getCurrentPage(), null);

        //List<Article> list = articleQueryMapper.queryByUser(kind, direction, id);
        QueryWrapper<Article> queryWrapper = sortQuery(dto.getKind(), dto.getDirection());
        if (byUser) queryWrapper.eq("account_id", dto.getId());
        queryWrapper.apply(StringUtils.hasText(dto.getStartTime()),
                "date_format (update_time, '%Y-%m-%d') >= date_format('" + dto.getStartTime() + "','%Y-%m-%d')")
                .apply(StringUtils.hasText(dto.getEndTime()),
                        "date_format (update_time, '%Y-%m-%d') <= date_format('" + dto.getEndTime() + "','%Y-%m-%d')");

        Page<Article> page = new Page<Article>(dto.getCurrentPage(), dto.getPageSize());
        //page.setSearchCount(false);
        Page<Article> list = articleQueryMapper.selectPage(page, queryWrapper);
        if (list != null)
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", setKudos(page));
        else
            return new ResultVo(AppHttpCodeEnum.DATA_WRONG, JSONObject.toJSONString(dto), null);
    }

    public ResultVo queryKeyword(ArticleQKeyDto dto, boolean byUser){
        if (dto.getKeyword() == null || dto.getKeyword().equals(""))
            return new ResultVo(AppHttpCodeEnum.EMPTY_KEYWORD, JSONObject.toJSONString(dto), null);
        if (dto.getPageSize() < 1)
            return new ResultVo(AppHttpCodeEnum.DATA_PAGESIZE_ERROR, "page size : "+dto.getPageSize(), null);
        if (dto.getCurrentPage() < 1 || isPageRight(dto.getPageSize(), dto.getCurrentPage(), dto.getKeyword()))
            return new ResultVo(AppHttpCodeEnum.GET_PAGE_ERROR, "current page : "+dto.getCurrentPage(), null);
        if (byUser && (dto.getId() == null || dto.getId() <= 0))
            return new ResultVo(AppHttpCodeEnum.USER_ID_NOT_NULL, JSONObject.toJSONString(dto), null);
        if (dto.getKind() > 2 || dto.getKind() < 0)
            return new ResultVo(AppHttpCodeEnum.DATA_KIND_ERROR, JSONObject.toJSONString(dto), null);
        if (dto.getDirection() > 1 || dto.getDirection() < 0)
            return new ResultVo(AppHttpCodeEnum.DATA_DIRECTION_ERROR, JSONObject.toJSONString(dto), null);

        QueryWrapper<Article> queryWrapper = sortQuery(dto.getKind(), dto.getDirection());
        if (byUser) queryWrapper.eq("account_id", dto.getId());
        queryWrapper.like("title", dto.getKeyword()).or().like("account", dto.getKeyword());
        Page<Article> page = new Page<Article>(dto.getCurrentPage(), dto.getPageSize());
        Page<Article> list = articleQueryMapper.selectPage(page, queryWrapper);
        if (list != null)
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", setKudos(list));
        else
            return new ResultVo(AppHttpCodeEnum.DATA_WRONG, JSONObject.toJSONString(dto), null);
    }

    public boolean isPageRight(int size, int page, String keyword){
        long num;
        if (keyword == null) num = articleNumberService.getNumber();
        else num = articleNumberService.getKeyNumber(keyword);
        return page > num/size + (num%size == 0? 0 : 1);
    }

    public QueryWrapper<Article> sortQuery(int kind, int direction){
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if (direction == 1){
            if (kind == 1) queryWrapper.orderByAsc("create_time");
            else if (kind == 2) queryWrapper.orderByAsc("kudos_number");
            else queryWrapper.orderByAsc("update_time");
        }
        else{
            if (kind == 1) queryWrapper.orderByDesc("create_time");
            else if (kind == 2) queryWrapper.orderByDesc("kudos_number");
            else queryWrapper.orderByDesc("update_time");
        }
        queryWrapper.eq("article_check", 1);
        return queryWrapper;
    }

    public List<ArticleVo> setKudos(Page<Article> page){
        /*Page<ArticleVo> voPage = new Page<>();*/
        List<ArticleVo> vos = new ArrayList<>();
        /*BeanUtils.copyProperties(page, vos);
        for (int i = 0; i < voPage.getRecords().size(); ++i) {
            QueryWrapper<Kudos> queryWrapper = kudosService.getKudos(voPage.getRecords().get(i).getId());
            voPage.getRecords().get(i).setIsKudos(kudosMapper.selectOne(queryWrapper) != null);
        }*/
        for (Article a : page.getRecords()) {
            ArticleVo vo = new ArticleVo();
            BeanUtils.copyProperties(a, vo);
            QueryWrapper<Kudos> queryWrapper = kudosService.getKudos(vo.getId());
            vo.setIsKudos(kudosMapper.selectOne(queryWrapper) != null);
            vos.add(vo);
        }
        return vos;
    }
}