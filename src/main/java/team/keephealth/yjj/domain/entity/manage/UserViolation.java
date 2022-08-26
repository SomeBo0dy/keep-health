package team.keephealth.yjj.domain.entity.manage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("adm_user_gol")
@AllArgsConstructor
@NoArgsConstructor
public class UserViolation {

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    // 违规账号
    @ApiModelProperty(value = "违规账号id")
    @Column(name = "account_id")
    private Long accountId;

    /**
     * 文章违规：
     * 一级违规：（1: 违法违规， 2: 色情低俗， 3: 赌博诈骗）记 100
     * 二级违规：（4: 人身攻击， 5: 侵犯隐私）记 70
     * 三级违规：（0: 其他， 6: 广告， 7: 引战， 8: 不良信息， 9: 内容不相关， 10: 刷屏）记 30
     */
    @ApiModelProperty(value = "文章一级违规")
    @Column(name = "art_one")
    private int articleOne;
    @ApiModelProperty(value = "文章二级违规")
    @Column(name = "art_two")
    private int articleTwo;
    @ApiModelProperty(value = "文章三级违规")
    @Column(name = "art_three")
    private int articleThree;

    /**
     * 评论违规：
     * 一级违规：（1: 违法违规， 2: 色情低俗， 3: 赌博诈骗）记 50
     * 二级违规：（4: 人身攻击， 5: 侵犯隐私）记 30
     * 三级违规：（0: 其他， 6: 广告， 7: 引战， 8: 不良信息， 9: 内容不相关， 10: 刷屏）记 10
     */
    @ApiModelProperty(value = "评论一级违规")
    @Column(name = "com_one")
    private int commentOne;
    @ApiModelProperty(value = "评论二级违规")
    @Column(name = "com_two")
    private int commentTwo;
    @ApiModelProperty(value = "评论三级违规")
    @Column(name = "com_three")
    private int commentThree;


}
