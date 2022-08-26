package team.keephealth.yjj.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SportPlanVo {

    // 运动计划id
    @ApiModelProperty(name = "运动计划id")
    private Long pid;
    // 运动计划作者id
    @ApiModelProperty(name = "运动计划作者id")
    private Long uid;
    // 该计划作者昵称
    @ApiModelProperty(name = "该计划作者昵称")
    private String name;
    // 该计划作者头像
    @ApiModelProperty(name = "该计划作者头像")
    private String photo;
    // 该计划目标
    @ApiModelProperty(name = "该计划目标")
    private String aim;
    // 该计划标题
    @ApiModelProperty(name = "该计划标题")
    private String title;
    // 该计划简介
    @ApiModelProperty(name = "该计划简介")
    private String words;
    // 该计划消耗总kcal量
    @ApiModelProperty(name = "该计划消耗总kcal量")
    private double kcal;
    // 该计划发布时间
    @ApiModelProperty(name = "该计划发布时间")
    private Date time;

    // 该计划阶段详细信息
    @ApiModelProperty(name = "该计划阶段详细信息")
    List<SportPlanDetailVo> list;
}
