package team.keephealth.yjj.domain.dto.recipe;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.keephealth.yjj.domain.entity.recipe.RecipePlan;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipePlanInfoDto {

    // 需更改时添加的id
    @ApiModelProperty(value = "需更改时添加的id")
    private Long planId;

    // 碳水化合物摄入量g(数值错误（<0）默认改为0
    @ApiModelProperty(value = "碳水化合物摄入量g(数值错误（<0）默认改为0,下同")
    private Double carbs;
    // 蛋白质摄入量g
    @ApiModelProperty(value = "蛋白质摄入量g")
    private Double pro;
    // 脂肪摄入量g
    @ApiModelProperty(value = "脂肪摄入量g")
    private Double fat;
    // 标题
    @ApiModelProperty(value = "标题")
    private String title;
    // 总卡路里摄入量
    @ApiModelProperty(value = "总卡路里摄入量")
    private double kcal;
    // 描述
    @ApiModelProperty(value = "描述")
    private String msg;
/*
食物内容若为新增
若添加用户为第三方人员，则该食物计入可搜索总库
若添加用户为普通用户，则该食物记为该用户独有
 */
    // 早餐内容(食物名，如包子，沙拉）（或许可以读取食物库的信息填写？
    @ApiModelProperty(value = "早餐内容(食物名，如包子，沙拉）（或许可以读取食物库的信息填写")
    private String breakfast;
    // 早餐图片（可不填，若该餐在食物库有记录，会根据记录补全信息，其他同类信息同
    @ApiModelProperty(value = "早餐图片（可不填，若该餐在食物库有记录，会根据记录补全信息，其他同类信息同")
    private String bfPict;
    // 早餐热量
    @ApiModelProperty(value = "早餐热量")
    private double bfKcal;
    // 早餐介绍
    @ApiModelProperty(value = "早餐介绍")
    private String bfWords;
    // 午餐内容
    @ApiModelProperty(value = "午餐内容")
    private String lunch;
    // 午餐图片
    @ApiModelProperty(value = "午餐图片")
    private String luPict;
    // 午餐热量
    @ApiModelProperty(value = "午餐热量")
    private double luKcal;
    // 午餐介绍
    @ApiModelProperty(value = "午餐介绍")
    private String luWords;
    // 晚餐内容
    @ApiModelProperty(value = "晚餐内容")
    private String dinner;
    // 晚餐图片
    @ApiModelProperty(value = "晚餐图片")
    private String dnPict;
    // 晚餐热量
    @ApiModelProperty(value = "晚餐热量")
    private double dnKcal;
    // 晚餐介绍
    @ApiModelProperty(value = "晚餐介绍")
    private String dnWords;

    public RecipePlanInfoDto(RecipePlan plan){
        this.planId = plan.getId();
        this.title = plan.getTitle();
        this.msg = plan.getMsg();
        this.kcal = plan.getKcal();
        this.carbs = plan.getCarbsWt();;
        this.pro = plan.getProWt();
        this.fat = plan.getFatWt();
    }

}
