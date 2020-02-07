package info.weifu.chao.edu_service.pojo.query;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class QueryCourse {

    private String TeacherId;

    private String CourseName;

    private BigDecimal price;

    private String SubjectId;


}
