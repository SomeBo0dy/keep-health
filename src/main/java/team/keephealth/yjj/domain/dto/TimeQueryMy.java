package team.keephealth.yjj.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeQueryMy {

    @ApiModelProperty(value = "开始时间  格式（yyyy-MM-dd）")
    private String beginTime;
    @ApiModelProperty(value = "结束时间  格式（yyyy-MM-dd）")
    private String endTime;
}
