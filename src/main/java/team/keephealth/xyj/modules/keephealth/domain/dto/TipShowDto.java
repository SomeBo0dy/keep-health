package team.keephealth.xyj.modules.keephealth.domain.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TipShowDto {
    //动态id
    @ApiModelProperty(value = "动态id")
    private Long kslId;
    //举报内容
    @ApiModelProperty(value = "举报内容")
    private String tipContent;
}
