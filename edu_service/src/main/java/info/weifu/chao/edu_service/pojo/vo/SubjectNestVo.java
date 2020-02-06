package info.weifu.chao.edu_service.pojo.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubjectNestVo {

    private String id;
    private String title;

    private List<SubjectVo> children  = new ArrayList<>();


}
