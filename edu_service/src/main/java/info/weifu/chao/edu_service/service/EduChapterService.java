package info.weifu.chao.edu_service.service;

import info.weifu.chao.edu_service.pojo.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import info.weifu.chao.edu_service.pojo.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author chao
 * @since 2020-02-05
 */
public interface EduChapterService extends IService<EduChapter> {

    void deleteByCourseId(String id);

    List<ChapterVo> getChapterByCourserId(String id);
}
