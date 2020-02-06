package info.weifu.chao.edu_service.service.impl;

import info.weifu.chao.edu_service.exception.EduException;
import info.weifu.chao.edu_service.pojo.EduCourse;
import info.weifu.chao.edu_service.mapper.EduCourseMapper;
import info.weifu.chao.edu_service.pojo.EduCourseDescription;
import info.weifu.chao.edu_service.pojo.from.CourseInfoForm;
import info.weifu.chao.edu_service.service.EduCourseDescriptionService;
import info.weifu.chao.edu_service.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
