package team.keephealth.yjj.service.article;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import team.keephealth.yjj.domain.dto.manage.InformDto;
import team.keephealth.yjj.domain.entity.articles.Content;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface ContentService extends IService<Content> {

    // 获取文章内容
    ResultVo<T> getDetail(Long id);

    // 举报文章
    ResultVo<T> complaintArticle(InformDto dto);
}
