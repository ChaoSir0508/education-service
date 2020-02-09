package info.weifu.chao.edu_service.pojo.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVo {
    private Integer id;
    private String title;


    private List<VideoVo> children = new ArrayList<>();
}
