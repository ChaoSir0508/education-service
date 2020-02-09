package info.weifu.chao.edu_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import info.weifu.chao.edu_service.mapper.EduVideoMapper;
import info.weifu.chao.edu_service.pojo.EduChapter;
import info.weifu.chao.edu_service.mapper.EduChapterMapper;
import info.weifu.chao.edu_service.pojo.EduVideo;
import info.weifu.chao.edu_service.pojo.vo.ChapterVo;
import info.weifu.chao.edu_service.pojo.vo.VideoVo;
import info.weifu.chao.edu_service.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import info.weifu.chao.edu_service.service.EduVideoService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
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
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoMapper eduVideoMapper;

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

    /**
     * 根据课堂id查询
     *
     * @param id
     * @return
     */
    @Override
    public List<ChapterVo> getChapterByCourserId(String id) {
        //返回结果
        ArrayList<ChapterVo> chapterVos = new ArrayList<>();
        //courseId设置条件
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", id);
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", id);
        //查询
        List<EduChapter> eduChapters = baseMapper.selectList(wrapperChapter);
        List<EduVideo> eduVideos = eduVideoMapper.selectList(wrapperVideo);
        //封装章节对象
        for (EduChapter eduChapter : eduChapters) {
            ChapterVo chapterVo = new ChapterVo();
            //复制属性
            BeanUtils.copyProperties(eduChapter, chapterVo);
            //添加章节
            chapterVos.add(chapterVo);
            //封装小节对象
            List<VideoVo> children = chapterVo.getChildren();
            Iterator<EduVideo> iterator = eduVideos.iterator();
            while (iterator.hasNext()) {
                EduVideo next = iterator.next();
                //是否与章节id相等
                if (next.getChapterId().equals(chapterVo.getId())) {
                    //复制属性
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(next, videoVo);
                    //添加
                    children.add(videoVo);
                    iterator.remove();
                }
            }
        }

        return chapterVos;
    }
}
