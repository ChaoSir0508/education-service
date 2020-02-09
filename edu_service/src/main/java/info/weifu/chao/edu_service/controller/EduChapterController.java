package info.weifu.chao.edu_service.controller;


import info.weifu.chao.edu_common.R;
import info.weifu.chao.edu_service.pojo.EduChapter;
import info.weifu.chao.edu_service.pojo.vo.ChapterVo;
import info.weifu.chao.edu_service.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author chao
 * @since 2020-02-05
 */
@RestController
@RequestMapping("/edu_service/edu-chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @DeleteMapping("{id}")
    public R deleteChapter(@PathVariable String id){
        eduChapterService.removeById(id);
        return R.OK();
    }

    /**
     * 根据课堂id查询
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public R getChapterByCourserId(@PathVariable String id) {
        List<ChapterVo> list = eduChapterService.getChapterByCourserId(id);
        return R.OK().data("items", list);

    }

    /**
     * 根据id查询
     *
     * @return
     */
    @GetMapping("getChapter/{id}")
    public R getChapter(@PathVariable String id) {
        EduChapter eduChapter = eduChapterService.getById(id);
        return R.OK().data("item", eduChapter);
    }

    /**
     * 保存章节
     *
     * @param eduChapter
     * @return
     */
    @PostMapping()
    private R saveChapter(@RequestBody EduChapter eduChapter) {
        boolean b = eduChapterService.saveOrUpdate(eduChapter);
        if (b) {
            return R.OK();
        } else {
            return R.ERROR();
        }
    }

}

