package team.keephealth.yjj.service.article.mpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.yjj.domain.dto.manage.InformDto;
import team.keephealth.yjj.domain.entity.articles.Content;
import team.keephealth.yjj.domain.entity.manage.InformArticle;
import team.keephealth.yjj.domain.vo.articles.ContentVo;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.articles.ArticleMapper;
import team.keephealth.yjj.mapper.articles.ContentMapper;
import team.keephealth.yjj.mapper.manage.InformArticleMapper;
import team.keephealth.yjj.service.article.ContentService;

@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements ContentService {

    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private InformArticleMapper informArticleMapper;

    @Override
    public ResultVo getDetail(Long id) {
        if (id < 1 || articleMapper.selectById(id) == null)
            return new ResultVo<>(AppHttpCodeEnum.DATA_ARTICLE_ID_ERROR, "id : "+id, null);
        ContentVo content = contentMapper.contentDetail(id);
        if (content != null)
            return new ResultVo<>(AppHttpCodeEnum.SUCCESS, "", content);
        else
            return new ResultVo<>(AppHttpCodeEnum.GET_CONTENT_ERROR, "id : " + id, null);
    }

    @Override
    public ResultVo<T> complaintArticle(InformDto dto) {
        if (dto.getInformId() < 1 || articleMapper.selectById(dto.getInformId()) == null)
            return new ResultVo<>(AppHttpCodeEnum.DATA_ARTICLE_ID_ERROR, "id : "+dto.getInformId(), null);
        if (dto.getType() == 0 && dto.getMessage() == null || dto.getMessage().equals(""))
            return new ResultVo<>(AppHttpCodeEnum.EMPTY_MESSAGE, "message : " + dto.getMessage(), null);
        if (dto.getType() < 0 || dto.getType() > 10)
            return new ResultVo<>(AppHttpCodeEnum.DATA_KIND_ERROR, "type : " + dto.getType(), null);
        InformArticle inform = new InformArticle(dto);
        if (informArticleMapper.insert(inform) > 0)
            return new ResultVo<>(AppHttpCodeEnum.SUCCESS, "", null);
        else
            return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(inform), null);
    }
}
