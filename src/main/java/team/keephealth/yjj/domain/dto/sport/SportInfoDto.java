package team.keephealth.yjj.domain.dto.sport;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SportInfoDto {

    // 运动计划信息

    // 更改需添加运动id
    @ApiModelProperty(value = "更改需添加运动id")
    private Long sportId;

    // 计划标题,空则为默认格式
    @ApiModelProperty(value = "计划标题", notes = "空则为默认格式")
    private String title;
    // 留言
    @ApiModelProperty(value = "留言")
    private String words;
    // 运动类型
    @ApiModelProperty(value = "运动类型")
    private Long sportType;
    // 运动时长
    @ApiModelProperty(value = "运动时长")
    private int sportTime;
    // 运动消耗能量（也是千卡）,可以不写，运动项目里有记录，若该数据传递错误信息则直接根据记录修改
    @ApiModelProperty(value = "运动消耗能量 千卡", notes = "可以不写，运动项目里有记录，若该数据传递错误信息则直接根据记录修改")
    private double energy;
    // 运动图片
    @ApiModelProperty(value = "运动图片")
    private String pict;

    // 计划开始时间，直接传递年月日即可yyyy-mm-dd
    /*
    当前设定：
    若只有开始时间无结束时间，开始时间在今日或今日之后者，结束时间与开始时间同（即计划实施日期只有一天）；
                         开始时间在今日之前者，结束时间为今日日期
    若只有结束时间无开始时间，结束时间在今日或今日之后者，开始时间为今日日期；
                         结束时间在今日之前者，开始时间与结束时间同
    （以上只在添加操作中有，更改操作中不予修改）
    开始时间在结束时间之后，返回报错
     */
    @ApiModelProperty(value = "计划开始时间", notes = "当前设定：\n" +
            "    若只有开始时间无结束时间，开始时间在今日或今日之后者，结束时间与开始时间同（即计划实施日期只有一天）；\n" +
            "                         开始时间在今日之前者，结束时间为今日日期\n" +
            "    若只有结束时间无开始时间，结束时间在今日或今日之后者，开始时间为今日日期；\n" +
            "                         结束时间在今日之前者，开始时间与结束时间同\n" +
            "    （以上只在添加操作中有，更改操作中不予修改）\n" +
            "    开始时间在结束时间之后，返回报错")
    private String beginTime;
    // 计划结束时间
    @ApiModelProperty(value = "计划结束时间")
    private String endTime;
}
