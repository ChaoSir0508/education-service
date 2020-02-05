package info.weifu.chao.edu_service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import info.weifu.chao.edu_service.pojo.EduSubject;
import info.weifu.chao.edu_service.vo.SubjectNestVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author chao
 * @since 2020-02-04
 */
public interface EduSubjectService extends IService<EduSubject> {

    List<String> importSubject(MultipartFile file);

    List<SubjectNestVo> GetNestSubjectList();
}
