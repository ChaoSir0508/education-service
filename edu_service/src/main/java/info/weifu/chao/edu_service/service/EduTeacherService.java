package info.weifu.chao.edu_service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import info.weifu.chao.edu_service.pojo.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import info.weifu.chao.edu_service.pojo.query.QueryTeacher;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author chao
 * @since 2020-02-01
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 条件分页查询
     * @param pageTeacher
     * @param queryTeacher
     */
    void pageListConditio(Page<EduTeacher> pageTeacher, QueryTeacher queryTeacher);

    EduTeacher getByName(String teacherName);
}
