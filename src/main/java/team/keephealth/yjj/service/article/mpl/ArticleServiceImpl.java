package team.keephealth.yjj.service.article.mpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.xyj.modules.keephealth.mapper.UserMapper;
import team.keephealth.yjj.domain.dto.article.ArticleInfoDto;
import team.keephealth.yjj.domain.entity.articles.Article;
import team.keephealth.yjj.domain.entity.articles.Content;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.articles.ArticleMapper;
import team.keephealth.yjj.mapper.articles.ContentMapper;
import team.keephealth.yjj.service.article.ContentService;
import team.keephealth.yjj.service.article.ArticleService;

import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ContentService contentService;

    @Override
    public ResultVo addArticle(ArticleInfoDto dto){
        if (dto.getTitle() == null || dto.getTitle().equals(""))
            return new ResultVo(AppHttpCodeEnum.EMPTY_TITLE, "", dto);
        if (dto.getWords() == null || dto.getWords().equals(""))
            return new ResultVo(AppHttpCodeEnum.EMPTY_WORDS, "", dto);
        Long id = SecurityUtils.getUserId();
        String account = userMapper.selectById(id).getNickName();

        QueryWrapper<Article> query_a = new QueryWrapper<>();
        query_a.eq("account_id", id);
        query_a.eq("title", dto.getTitle());
        List<Object> list = articleMapper.selectObjs(query_a);
        if (list.size() != 0)
            return new ResultVo(AppHttpCodeEnum.ARTICLE_EXIST, "", list);

        Date date = new Date();
        Article article = new Article(account, id, dto.getTitle(), dto.getWords(), date);
        Content content = new Content(dto);
        content.setUser(account, id);
        boolean b = contentService.save(content);
        if (!b) {
            contentMapper.deleteById(content.getId());
            return new ResultVo(AppHttpCodeEnum.SET_CONTENT_ERROR, "", content);
        }
        QueryWrapper<Content> query_b = new QueryWrapper<>();
        query_b.eq("account_id", id);
        query_b.eq("title", dto.getTitle());
        article.setContentId(contentMapper.selectOne(query_b).getId());
        boolean a = save(article);

        if (a)
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", articleMapper.selectOne(query_a));
        else{
                articleMapper.delete(query_a);
                return new ResultVo(AppHttpCodeEnum.SET_ARTICLE_ERROR, "文章数据存入错误", content);
            }
    }

    @Override
    public ResultVo deleteArticle(Long id){
        if (id < 1 || articleMapper.selectById(id) == null)
            return new ResultVo(AppHttpCodeEnum.DATA_ARTICLE_ID_ERROR, "id : "+id, null);
        Article article = articleMapper.selectById(id);
        if (!article.getAccountId().equals(SecurityUtils.getLoginUser().getUser().getId()))
            return new ResultVo(AppHttpCodeEnum.NO_OPERATOR_AUTH, "无操作权限，只可删除本人文章", null);
        if (contentMapper.deleteById(article.getContentId()) > 0)
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", "");
        else
            return new ResultVo(AppHttpCodeEnum.ERROR, "", articleMapper.selectById(id));
    }

    @Override
    public ResultVo updateArticle(ArticleInfoDto dto) {
        if (dto.getId() < 1 || dto.getId() == null || articleMapper.selectById(dto.getId()) == null)
            return new ResultVo(AppHttpCodeEnum.DATA_ARTICLE_ID_ERROR, "id :" + dto.getId(), null);

        Article a_return = articleMapper.selectById(dto.getId());
        Long c_id = a_return.getContentId();
        Content c_return = contentMapper.selectById(c_id);
        Long id = SecurityUtils.getUserId();
        Long w_id = a_return.getAccountId();
        if (!w_id.equals(id))
            return new ResultVo(AppHttpCodeEnum.NO_OPERATOR_AUTH, "user :"+id+" writer :"+w_id, null);

        Article article = new Article(dto.getId(), dto.getTitle(), dto.getWords());
        Content content = new Content(dto);
        content.setId(c_id);
        int a = articleMapper.updateById(article);
        int b = contentMapper.updateById(content);
        if (a > 0 && b > 0)
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", contentMapper.selectById(c_id));
        else{
            if (a <= 0 && b <= 0)
                return new ResultVo(AppHttpCodeEnum.SET_ERROR,"", dto);
            else if (a <= 0) {
                contentMapper.updateById(c_return);
                return new ResultVo(AppHttpCodeEnum.SET_ARTICLE_ERROR, "", article);
            }
            else {
                articleMapper.updateById(a_return);
                return new ResultVo(AppHttpCodeEnum.SET_CONTENT_ERROR, "", content);
            }
        }
    }


}
