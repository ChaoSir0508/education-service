package info.weifu.chao.edu_service.controller;


import info.weifu.chao.edu_service.pojo.EduTeacher;
import info.weifu.chao.edu_service.service.EduTeacherService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author chao
 * @since 2020-02-01
 */
@RestController
@RequestMapping("/edu_service/edu-teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 查询全部
     * @return
     */
    @GetMapping
    public List<EduTeacher> getEduTeacherList(){

        List<EduTeacher> list = eduTeacherService.list(null);

        return list;
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public boolean deleteTeacherById(@PathVariable("id") String id){
        boolean b = eduTeacherService.removeById(id);
        return b;
    }

}

