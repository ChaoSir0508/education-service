package info.weifu.chao.edu_service.pojo.list;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseList {
    private String id;
    private String teacherName;
    private String subjectName;
    private String title;
    private BigDecimal price;
    private Integer lessonNum;
}
