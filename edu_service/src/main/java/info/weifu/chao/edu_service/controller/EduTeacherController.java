package info.weifu.chao.edu_service.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import info.weifu.chao.edu_common.R;
import info.weifu.chao.edu_service.pojo.EduTeacher;
import info.weifu.chao.edu_service.pojo.query.QueryTeacher;
import info.weifu.chao.edu_service.service.EduTeacherService;
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
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 根据ID修改
     * @param eduTeacher
     * @return
     */
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody  EduTeacher eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);
        if (b){
            return R.OK();
        }else {
            return R.ERROR();
        }
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping("getTeacherInfo/{id}")
    public R getTeacherInfo(@PathVariable("id") String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.OK().data("eduTeacher",eduTeacher);
    }

    /**
     * 添加讲师
     * @param eduTeacher
     * @return
     */
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody  EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (save){
            return R.OK();
        }else {
            return R.ERROR();
        }
    }

    /**
     * 条件分页查询
     * @param page
     * @param limit
     * @param queryTeacher
     * @return
     */
    @PostMapping("moreConditionPageList/{page}/{limit}")
    public R getMoreCondition(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit,
                              @RequestBody(required = false) QueryTeacher queryTeacher){
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        eduTeacherService.pageListConditio(pageTeacher,queryTeacher);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.OK().data("total",total).data("items",records);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("pageList/{page}/{limit}")
    public R getPageTeacher(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        eduTeacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.OK().data("total",total).data("itmes",records);
    }

    /**
     * 查询全部
     *
     * @return
     */
    @GetMapping
    public R getEduTeacherList() {
       /* try {
            int i =1/0;
        } catch (Exception e) {
            throw new EduException(2001,"自定义异常");
        }*/
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.OK().data("itmes", list);
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R deleteTeacherById(@PathVariable("id") String id) {
        eduTeacherService.removeById(id);
        return R.OK();
    }

}

