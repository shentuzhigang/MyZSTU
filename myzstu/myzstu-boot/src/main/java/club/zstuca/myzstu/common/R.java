package club.zstuca.myzstu.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-05-23 00:04
 */
@Data
@Builder
@ApiModel(value = "基础响应对象类", description = "基础响应对象类")
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    @ApiModelProperty(example = "20000")
    private Integer code;
    /**
     * 业务提示语
     */
    @ApiModelProperty(example = "成功")
    private String msg;
    /**
     * 数据
     */
    @ApiModelProperty(example = "{}")
    private T data;

    private R() {
    }

    public R(Integer status) {
        this.code = status;
    }

    public R(Integer status, String msg) {
        this.code = status;
        this.msg = msg;
    }

    private R(Integer status, String msg, T obj) {
        this.code = status;
        this.msg = msg;
        this.data = obj;
    }

    public static R<?> success() {
        return build(BizCode.SUCCESS, null);
    }

    public static <T> R<T> success(T obj) {
        return build(BizCode.SUCCESS, obj);
    }

    public static R<?> error() {
        return build(BizCode.ERROR, null);
    }

    public static <T> R<T> error(T obj) {
        return build(BizCode.ERROR, obj);
    }

    public static <T> R<T> build(BizCode biz, T obj) {
        return R.<T>builder()
                .code(biz.getCode())
                .msg(biz.getMsg())
                .data(obj)
                .build();
    }

}
