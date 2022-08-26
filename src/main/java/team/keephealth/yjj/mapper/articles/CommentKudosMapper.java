package team.keephealth.yjj.mapper.articles;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.keephealth.yjj.domain.entity.articles.CommentKudos;

@Mapper
@Repository
public interface CommentKudosMapper extends BaseMapper<CommentKudos> {
}
