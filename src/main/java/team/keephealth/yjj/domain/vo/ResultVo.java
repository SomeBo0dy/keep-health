package team.keephealth.yjj.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.keephealth.common.enums.AppHttpCodeEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVo<T> {

    @ApiModelProperty(value = "响应状态码",dataType = "int")
    private int code;

    @ApiModelProperty("响应提示信息")
    private String message;
    //Map<String, Object> data = new HashMap<>(); // 数据

    @ApiModelProperty("响应数据")
    private T data;

    public ResultVo(AppHttpCodeEnum app, String message, T data){
        this.code = app.getCode();
        this.message = message;
        this.data = data;
    }

    public static ResultVo successResult(Object data){
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", data);
    }

    public static ResultVo successResult(){
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", null);
    }

}
