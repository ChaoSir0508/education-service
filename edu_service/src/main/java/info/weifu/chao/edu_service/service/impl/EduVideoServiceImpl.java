package info.weifu.chao.edu_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import info.weifu.chao.edu_service.pojo.EduChapter;
import info.weifu.chao.edu_service.pojo.EduVideo;
import info.weifu.chao.edu_service.mapper.EduVideoMapper;
import info.weifu.chao.edu_service.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author chao
 * @since 2020-02-05
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    /**
     * 根据课堂id删除
     *
     * @param id
     */
    @Override
    public void deleteByCourseId(String id) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        baseMapper.delete(wrapper);
    }
}
