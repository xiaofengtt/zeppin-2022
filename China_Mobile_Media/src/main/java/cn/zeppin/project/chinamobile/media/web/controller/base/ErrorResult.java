package cn.zeppin.project.chinamobile.media.web.controller.base;

import java.util.HashMap;
import java.util.Map;

/**
 * ErrorResult : 用于响应错误的请求的对象
 *
 * @author Clark_Rong
 * @since 2016-03-23 11:17
 */
public class ErrorResult extends BaseResult {
    private static final long serialVersionUID = 8567221653356186674L;

    /**
     * 封装多个 错误信息
     */
    private Map<String, Object> errors = new HashMap<>();

    public Map<String, Object> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, Object> errors) {
        this.errors = errors;
    }

    public ErrorResult() {

    }
}
