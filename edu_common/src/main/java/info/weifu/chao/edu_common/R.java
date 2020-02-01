package info.weifu.chao.edu_common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义具体数据返回格式
 */
@Data
public class R {

    private Boolean success;
    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    public R() {
    }

    /**
     * 操作成功
     * @return
     */
    public static R OK(){
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("操作成功");
        return r;
    }

    /**
     * 操作失败
     * @return
     */
    public static R ERROR(){
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.ERROR);
        r.setMessage("操作失败");
        return r;
    }

    /**
     * 链式编程
     * @param success
     * @return
     */
    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R data(String key,Object value){
        this.data.put(key,value);
        return this;
    }

    public R data(Map<String ,Object> map){
        this.setData(map);
        return this;
    }

}
