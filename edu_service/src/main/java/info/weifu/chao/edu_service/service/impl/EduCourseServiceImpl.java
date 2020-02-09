package info.weifu.chao.edu_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import info.weifu.chao.edu_service.exception.EduException;
import info.weifu.chao.edu_service.pojo.*;
import info.weifu.chao.edu_service.mapper.EduCourseMapper;
import info.weifu.chao.edu_service.pojo.from.CourseInfoForm;
import info.weifu.chao.edu_service.pojo.list.CourseList;
import info.weifu.chao.edu_service.pojo.query.QueryCourse;
import info.weifu.chao.edu_service.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author chao
 * @since 2020-02-05
 */
@Service
@Transactional
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduSubjectService eduSubjectService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 添加课程信息
     *
     * @param courseInfoForm
     * @return
     */
    @Override
    public String saveOrUpdateCourseInfo(CourseInfoForm courseInfoForm) {
        try {
            EduCourse eduCourse = new EduCourse();
            BeanUtils.copyProperties(courseInfoForm, eduCourse);
            if (StringUtils.isEmpty(courseInfoForm.getId())) {//添加
                int insert = baseMapper.insert(eduCourse);
                if (insert == 0) {
                    throw new EduException(20001, "添加课程失败");
                }
                EduCourseDescription eduCourseDescription = this.getEduCourseDescription(courseInfoForm, eduCourse);
                eduCourseDescriptionService.save(eduCourseDescription);
            } else {//修改
                baseMapper.updateById(eduCourse);
                EduCourseDescription eduCourseDescription = this.getEduCourseDescription(courseInfoForm, eduCourse);
                eduCourseDescriptionService.updateById(eduCourseDescription);
            }
            return eduCourse.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据id查询课程
     *
     * @param id
     * @return
     */
    @Override
    public CourseInfoForm getCourseById(String id) {
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        EduCourse eduCourse = baseMapper.selectById(id);
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(id);
        BeanUtils.copyProperties(eduCourse, courseInfoForm);
        BeanUtils.copyProperties(courseDescription, courseInfoForm);
        return courseInfoForm;
    }

    /**
     * 条件分页查询
     *
     * @param pageLimit
     * @param queryCourse
     * @return
     */
    @Override
    public List<CourseList> getMoreCondition(Page pageLimit, QueryCourse queryCourse) {

        //不包含查询条件
        if (queryCourse == null) {
            List<EduCourse> courseList = baseMapper.selectPage(pageLimit, null).getRecords();
            return this.convertToCourserList(courseList);
        }

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        String teacherId = queryCourse.getTeacherId();
        String courseName = queryCourse.getCourseName();
        String subjectId = queryCourse.getSubjectId();
        BigDecimal price = queryCourse.getPrice();
        if (!StringUtils.isEmpty(teacherId)) {//教师名为条件
            queryWrapper.eq("teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(subjectId)) {//分类名为条件
            queryWrapper.eq("subject_id", subjectId);
        }
        if (!StringUtils.isEmpty(courseName)) {//课堂名为条件
            queryWrapper.like("title", courseName);
        }
        if (!StringUtils.isEmpty(price)) {//价格为条件
            queryWrapper.eq("price", price);
        }
        List<EduCourse> courseList = baseMapper.selectPage(pageLimit, queryWrapper).getRecords();
        return this.convertToCourserList(courseList);
    }

    /**
     * 查询全部
     *
     * @return
     */
    @Override
    public List<EduCourse> getCourseList() {
        return baseMapper.selectList(null);
    }

    /**
     * 删除课堂
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteCourseById(String id) {
        //删除小节
        eduVideoService.deleteByCourseId(id);
        //删除章节
        eduChapterService.deleteByCourseId(id);
        //删除课堂
        int i = baseMapper.deleteById(id);
        //删除课堂描述
        eduCourseDescriptionService.removeById(id);
        return i>0;
    }

    /**
     * 将数据库对象转换为列表可视化对象
     *
     * @param eduCourses
     * @return
     */
    private List<CourseList> convertToCourserList(List<EduCourse> eduCourses) {
        ArrayList<CourseList> courseLists = new ArrayList<>();
        for (EduCourse eduCourse : eduCourses) {
            System.out.println(eduCourse);
            CourseList courseList = new CourseList();
            //根据教师id获取教师对象，通过教师对象获得名称
            courseList.setTeacherName(eduTeacherService.getById(eduCourse.getTeacherId()).getName());
            //根据分类id获取教师对象，通过分类对象获得名称
            courseList.setSubjectName(eduSubjectService.getById(eduCourse.getSubjectId()).getTitle());
            BeanUtils.copyProperties(eduCourse, courseList);
            courseLists.add(courseList);
        }
        return courseLists;
    }


    /**
     * 创建课程描述信息
     *
     * @param courseInfoForm
     * @param eduCourse
     * @return
     */
    private EduCourseDescription getEduCourseDescription(CourseInfoForm courseInfoForm, EduCourse eduCourse) {
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        //获得描述信息
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        //获得课程id
        String courseId = eduCourse.getId();
        eduCourseDescription.setId(courseId);
        return eduCourseDescription;
    }

}
