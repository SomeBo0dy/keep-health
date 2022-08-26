package team.keephealth.yjj.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteListDto {

    // 删除的id list
    @ApiModelProperty(value = "删除的id list")
    private List<Long> ids;
}
