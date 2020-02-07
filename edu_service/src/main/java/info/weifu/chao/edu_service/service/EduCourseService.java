package info.weifu.chao.edu_service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import info.weifu.chao.edu_service.pojo.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import info.weifu.chao.edu_service.pojo.from.CourseInfoForm;
import info.weifu.chao.edu_service.pojo.list.CourseList;
import info.weifu.chao.edu_service.pojo.query.QueryCourse;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author chao
 * @since 2020-02-05
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveOrUpdateCourseInfo(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseById(String id);

    List<CourseList> getMoreCondition(Page eduCoursePage, QueryCourse queryCourse);

    List<EduCourse> getCourseList();
}
