package team.keephealth.yjj.domain.entity.action;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("memory")
@AllArgsConstructor
@NoArgsConstructor
public class Memory {

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private Long pid;

    public Memory(Long uid, Long pid){
        this.userId = uid;
        this.pid = pid;
    }
}
