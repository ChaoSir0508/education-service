package info.weifu.chao.edu_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import info.weifu.chao.edu_service.pojo.EduChapter;
import info.weifu.chao.edu_service.mapper.EduChapterMapper;
import info.weifu.chao.edu_service.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author chao
 * @since 2020-02-05
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    /**
     * 根据课堂id删除
     *
     * @param id
     */
    @Override
    public void deleteByCourseId(String id) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        baseMapper.delete(wrapper);
    }
}
