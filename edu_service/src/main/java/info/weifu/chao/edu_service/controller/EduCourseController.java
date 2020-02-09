package info.weifu.chao.edu_service.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.sun.org.apache.xpath.internal.operations.Bool;
import info.weifu.chao.edu_common.R;
import info.weifu.chao.edu_service.pojo.EduCourse;
import info.weifu.chao.edu_service.pojo.from.CourseInfoForm;
import info.weifu.chao.edu_service.pojo.list.CourseList;
import info.weifu.chao.edu_service.pojo.query.QueryCourse;
import info.weifu.chao.edu_service.pojo.vo.ChapterVo;
import info.weifu.chao.edu_service.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/service/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R deleteCourseById(@PathVariable String id) {
        Boolean flag = eduCourseService.deleteCourseById(id);
        if (flag) {
            return R.OK();
        } else {
            return R.ERROR();
        }
    }


    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping
    public R getCourseList() {
        try {
            List<EduCourse> list = eduCourseService.getCourseList();
            return R.OK().data("items", list);
        } catch (Exception e) {
            e.printStackTrace();
            return R.ERROR();
        }
    }

    /**
     * 条件分页查询
     *
     * @param page
     * @param limit
     * @param queryCourse
     * @return
     */
    @PostMapping("{page}/{limit}")
    public R getMoreCondition(@PathVariable Integer page, @PathVariable Integer limit,
                              @RequestBody(required = false) QueryCourse queryCourse) {
        Page<EduCourse> eduCoursePage = new Page<>(page, limit);
        try {
            List<CourseList> courses = eduCourseService.getMoreCondition(eduCoursePage, queryCourse);
            return R.OK().data("items", courses).data("total", eduCoursePage.getTotal());
        } catch (Exception e) {
            e.printStackTrace();
            return R.ERROR();
        }
    }

    /**
     * 根据id查询课程
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public R getCourseById(@PathVariable("id") String id) {
        CourseInfoForm eduCourse = eduCourseService.getCourseById(id);
        if (eduCourse != null) {
            return R.OK().data("item", eduCourse);
        } else {
            return R.ERROR();
        }
    }

    /**
     * 添加课程信息
     *
     * @param courseInfoForm
     * @return
     */
    @PostMapping
    public R saveOrUpdateCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {
        String CourseId = eduCourseService.saveOrUpdateCourseInfo(courseInfoForm);
        if (StringUtils.isEmpty(CourseId)) {
            return R.ERROR();
        }
        return R.OK().data("courseId", CourseId);
    }

}

