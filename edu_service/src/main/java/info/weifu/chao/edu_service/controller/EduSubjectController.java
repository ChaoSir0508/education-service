package info.weifu.chao.edu_service.controller;


import info.weifu.chao.edu_common.R;
import info.weifu.chao.edu_service.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author chao
 * @since 2020-02-04
 */
@RestController
@RequestMapping("/service/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("import")
    public R importSubject(@RequestParam("file") MultipartFile file) {
        List<String> message = eduSubjectService.importSubject(file);
//        int i = 1/0;
        if (message.size() == 0) {
            return R.OK();
        }else {
            return R.ERROR().message("导入成功，警告信息！").data("messageList", message);
        }
    }

}

