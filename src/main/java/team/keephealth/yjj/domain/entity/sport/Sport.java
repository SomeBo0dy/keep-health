package team.keephealth.yjj.domain.entity.sport;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.dto.sport.SportInfoDto;
import team.keephealth.yjj.domain.vo.SportPlanDetailVo;
import team.keephealth.yjj.mapper.sport.WWEMapper;

import javax.persistence.Column;
import javax.persistence.Id;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_sport")
@AllArgsConstructor
@NoArgsConstructor
public class Sport {

    // 我的运动计划

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    // 计划隶属用户
    @Column(name = "account_id")
    private Long accountId;
    // 计划标题
    private String title;
    // 留言
    private String words;

    // 运动类型
    @Column(name = "sport_type")
    private Long sportType;
    // 运动时长(分钟)
    @Column(name = "sport_time")
    private int sportTime;
    // 运动消耗能量（也是千卡）
    private double energy;
    // 运动图片
    private String pict;
    // 隶属的推荐计划，无则null
    @Column(name = "plan_id")
    private Long planId;

    // 计划开始时间
    @Column(name = "begin_time")
    private Date beginTime;
    // 计划结束时间
    @Column(name = "end_time")
    private Date endTime;

    // 添加计划
    public Sport(SportInfoDto dto){
        this.accountId = SecurityUtils.getUserId();
        this.title = dto.getTitle();
        this.words = dto.getWords();
        this.sportType = dto.getSportType();
        this.sportTime = dto.getSportTime();
        this.energy = dto.getEnergy();
        this.pict = dto.getPict();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.beginTime = format.parse(dto.getBeginTime()+" 00:00:00");
            this.endTime = format.parse(dto.getEndTime()+" 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // 选择推荐计划
    public Sport(SportPlanDetailVo vo, String begin, String end){
        this.accountId = SecurityUtils.getUserId();
        this.title = vo.getName();
        this.words = vo.getMsg();
        this.sportTime = vo.getTime();
        this.energy = vo.getEnergy();
        this.pict = vo.getPict();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.beginTime = format.parse(begin+" 00:00:00");
            this.endTime = format.parse(end+" 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
