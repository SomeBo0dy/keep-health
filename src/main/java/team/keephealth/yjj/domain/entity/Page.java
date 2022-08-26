package team.keephealth.yjj.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page {

    // 数据总数
    private Long total;
    // 页数
    private int pageSize;
    // 总页数
    private int totalPage;
    // 当前页数
    private int currentPage;
    // 当前页数据量
    private int currentPageNumber;
    private List list;
}
