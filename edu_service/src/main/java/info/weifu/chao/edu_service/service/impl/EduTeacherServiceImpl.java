package info.weifu.chao.edu_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import info.weifu.chao.edu_service.mapper.EduTeacherMapper;
import info.weifu.chao.edu_service.pojo.EduTeacher;
import info.weifu.chao.edu_service.pojo.query.QueryTeacher;
import info.weifu.chao.edu_service.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author chao
 * @since 2020-02-01
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    /**
     * 条件分页查询
     *
     * @param pageTeacher
     * @param queryTeacher
     */
    @Override
    public void pageListConditio(Page<EduTeacher> pageTeacher, QueryTeacher queryTeacher) {
        if (queryTeacher == null) {
            //无条件，直接查询
            baseMapper.selectPage(pageTeacher, null);
            return;
        }
        //有条件，判断拼接条件
        String name = queryTeacher.getName();
        String level = queryTeacher.getLevel();
        String begin = queryTeacher.getBegin();
        String end = queryTeacher.getEnd();
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.gt("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_modified", end);
        }
        baseMapper.selectPage(pageTeacher,wrapper);
    }

}
