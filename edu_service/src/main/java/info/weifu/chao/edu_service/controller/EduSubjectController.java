package info.weifu.chao.edu_service.controller;


import com.sun.org.apache.xpath.internal.operations.Bool;
import info.weifu.chao.edu_common.R;
import info.weifu.chao.edu_service.pojo.EduSubject;
import info.weifu.chao.edu_service.service.EduSubjectService;
import info.weifu.chao.edu_service.vo.SubjectNestVo;
import org.apache.ibatis.annotations.Delete;
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

    /**
     * 添加一级或二级分类
     * @param eduSubject
     * @return
     */
    @PostMapping()
    public R addSubject(@RequestBody EduSubject eduSubject) {
        Boolean flag = eduSubjectService.addSubject(eduSubject);
        if (flag) {
            return R.OK();
        } else {
            return R.ERROR();
        }
    }

    /**
     * 删除指定分类
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R deleteSubject(@PathVariable("id") String id) {
        Boolean flag = eduSubjectService.deleteSubject(id);
        if (flag) {
            return R.OK();
        } else {
            return R.ERROR();
        }
    }

    /**
     * 获得分类列表
     *
     * @return
     */
    @GetMapping("")
    public R GetNestSubjectList() {
        List<SubjectNestVo> voList = eduSubjectService.GetNestSubjectList();
        for (SubjectNestVo subjectNestVo : voList) {
            System.out.println(subjectNestVo);
        }
        return R.OK().data("items", voList);
    }

    /**
     * 导入Excel数据
     *
     * @param file
     * @return
     */
    @PostMapping("import")
    public R importSubject(@RequestParam("file") MultipartFile file) {
        List<String> message = eduSubjectService.importSubject(file);
        if (message.size() == 0) {
            return R.OK();
        } else {
            return R.ERROR().message("导入成功，警告信息！").data("messageList", message);
        }
    }

}

