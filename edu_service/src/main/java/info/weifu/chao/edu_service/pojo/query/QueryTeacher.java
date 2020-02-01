package info.weifu.chao.edu_service.pojo.query;

import lombok.Data;

@Data
/**
 * 封装条件查询
 */
public class QueryTeacher {

    private String name;
    private String level;
    private String begin;//开始时间
    private String end;//结束时间


}
