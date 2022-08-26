package team.keephealth.yjj.service.manage;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import team.keephealth.yjj.domain.dto.manage.DoDeal;
import team.keephealth.yjj.domain.entity.manage.InformComment;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface CommentVioService extends IService<InformComment> {

    // 获取被举报的评论列表
    // 0：未处理， 1：已处理
    ResultVo<T> getAll(int deal);

    // 查看这个评论的所有举报信息
    ResultVo<T> getArticleVio(Long commentId);

    // 处理评论举报
    ResultVo<T> setDeal(DoDeal dto);

}
