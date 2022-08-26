package team.keephealth.yjj.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class VioVo {

    @ApiModelProperty(value = "举报类别以及该类别的举报数量")
    private Map<String, Integer> count;

    @ApiModelProperty(value = "有附加信息的举报信息")
    private Map<String, String> msg;

    public VioVo(Map<String, Integer> count, Map<String, String> msg){
        this.count = count;
        this.msg = msg;
    }
}
