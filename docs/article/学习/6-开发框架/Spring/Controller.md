---
tag:
 - 开发框架
 - Spring
---

# Controller

## 一、Controller的优雅设计

### （一）封装统一的返回类

统一的返回类型是非常有必要的，方便对接接口的开发人员更加清晰地知道这个接口调用是否成功（不能仅仅通过简单地看返回值是否为null就判断成功与否）。例如使用状态码、状态信息就能清楚地了解接口调用情况。

```java
import cn.hutool.core.util.ObjectUtil;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * ClassName: AjaxResult
 * Description: ajax请求返回类封装，返回json格式
 *
 * @Author StartZhao
 * @Create 2024/11/22 20:26
 * @Version 1.0
 */
public class AjaxResult<T> extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    public static final String CODE_TAG = "code";

    /**
     * 提示消息
     */
    public static final String MSG_TAG = "msg";

    /**
     * 数据对象
     */
    public static final String DATA_TAG = "data";

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public AjaxResult() {
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code
     * @param msg
     */
    public AjaxResult(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code
     * @param msg
     * @param data
     */
    public AjaxResult(int code, String msg, T data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (ObjectUtil.isNotEmpty(data)) {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 链式调用，添加键值对
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public AjaxResult<T> put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    /**
     * 返回成功消息
     *
     * @param msg
     * @param data
     * @return
     */
    public static <T> AjaxResult<T> success(String msg, T data) {
        return new AjaxResult<>(HttpStatus.OK.value(), msg, data);
    }

    /**
     * 返回成功消息
     *
     * @param msg
     * @return
     */
    public static <T> AjaxResult<T> success(String msg) {
        return AjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @return
     */
    public static <T> AjaxResult<T> success() {
        return AjaxResult.success("操作成功");
    }

    /**
     * 返回失败消息
     *
     * @param msg
     * @param data
     * @return
     */
    public static <T> AjaxResult<T> error(String msg, T data) {
        return new AjaxResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, data);
    }

    /**
     * 返回失败消息
     *
     * @param msg
     * @return
     */
    public static <T> AjaxResult<T> error(String msg) {
        return AjaxResult.error(msg, null);
    }

    /**
     * 返回失败消息
     *
     * @return
     */
    public static <T> AjaxResult<T> error() {
        return AjaxResult.error("操作失败");
    }

    /**
     * 返回指定状态码的消息
     *
     * @param status
     * @param msg
     * @param data
     * @return
     */
    public static <T> AjaxResult<T> of(HttpStatus status, String msg, T data) {
        return new AjaxResult<>(status.value(), msg, data);
    }

    /**
     * 返回指定状态码的消息
     *
     * @param status
     * @param msg
     * @return
     */
    public static <T> AjaxResult<T> of(HttpStatus status, String msg) {
        return AjaxResult.of(status, msg, null);
    }

    /**
     * 返回指定状态码的消息
     *
     * @param status
     * @return
     */
    public static <T> AjaxResult<T> of(HttpStatus status) {
        return AjaxResult.of(status, status.getReasonPhrase());
    }
}
```

### （二）全局异常处理器

设置全局异常处理器目的一个是为了可以与前面定义下来地统一返回类的结构对应上，另一个是我们希望无论系统发生什么异常，Http 状态码都是 200，尽可能由业务来区分系统的异常。

```java
import com.aotuo.common.domain.AjaxResult;
import com.aotuo.common.exception.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ClassName: GlobalExceptionHandler
 * Description: 全局异常处理器
 *
 * @Author StartZhao
 * @Create 2024/11/22 22:27
 * @Version 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 基础异常
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public AjaxResult handleBaseException(BaseException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址“{}”，发生基础异常：{}", requestURI, e.getDetailMessage(), e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 未知异常
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址“{}”，发生未知异常：{}", requestURI, e.getMessage(), e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 系统异常
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址“{}”，发生系统异常：{}", requestURI, e.getMessage(), e);
        return AjaxResult.error(e.getMessage());
    }
}
```

上述的 BaseException 就是自定义异常，你可以根据自己的业务需求，在自定义设置其他异常，但是需要捕获兜底的异常如：Exception、RuntimeException。

### （三）参数校验

Java API 的规范 `JSR303` 定义了校验的标准 `validation-api`。

使用 `spring validation`，可使得参数校验代码不与业务逻辑代码耦合。

