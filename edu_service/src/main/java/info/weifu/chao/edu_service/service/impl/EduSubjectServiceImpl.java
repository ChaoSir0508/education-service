package info.weifu.chao.edu_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import info.weifu.chao.edu_service.exception.EduException;
import info.weifu.chao.edu_service.mapper.EduSubjectMapper;
import info.weifu.chao.edu_service.pojo.EduSubject;
import info.weifu.chao.edu_service.service.EduSubjectService;
import info.weifu.chao.edu_service.pojo.vo.SubjectNestVo;
import info.weifu.chao.edu_service.pojo.vo.SubjectVo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author chao
 * @since 2020-02-04
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 导入Excel数据
     *
     * @param file
     * @return
     */
    @Override
    public List<String> importSubject(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            //存储错误信息
            List<String> message = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                HSSFRow row = sheet.getRow(i);
                if (row == null) {
                    String str = "第" + (i + 1) + "行为空";
                    message.add(str);
                    continue;
                }
                //获得第一列
                HSSFCell cellOne = row.getCell(0);
                if (cellOne == null) {
                    String str = "第" + (i + 1) + "行1数据为空";
                    message.add(str);
                    continue;
                }
                //一级分类值
                String valueOne = cellOne.getStringCellValue();
                //判断一级分类是否存在
                EduSubject existSubject = this.existSubject(null, valueOne);
                String parent_id = null;
                if (existSubject == null) {
                    //保存一级分类数据
                    EduSubject eduSubject = this.addEduSubject(null, valueOne);
                    parent_id = eduSubject.getId();
                } else {
                    parent_id = existSubject.getId();
                }

                //获得第二列
                HSSFCell cellTwo = row.getCell(1);
                if (cellTwo == null) {
                    String str = "第" + (i + 1) + "行2数据为空";
                    message.add(str);
                    continue;
                }
                //二级分类值
                String valueTwo = cellTwo.getStringCellValue();
                //判断二级分类是否存在
                EduSubject existSubjectTwo = this.existSubject(valueTwo, parent_id);
                if (existSubjectTwo == null) {
                    //保存二级分类数据
                    this.addEduSubject(parent_id, valueTwo);
                }
            }
            return message;
        } catch (IOException e) {
            throw new EduException(20001, "数据导入异常");
        }
    }


    /**
     * 获取分类信息
     *
     * @return
     */
    @Override
    public List<SubjectNestVo> GetNestSubjectList() {

        //最终返回结果
        ArrayList<SubjectNestVo> subjectNestVos = new ArrayList<>();

        //查询所有一级分类
        List<EduSubject> eduSubjectsOne = this.getEduSubjects("one");

        //查询所有二级分类
        List<EduSubject> eduSubjectsTwo = this.getEduSubjects("two");

        //将所有一级分类添加到返回结果中
        for (int i = 0; i < eduSubjectsOne.size(); i++) {
            SubjectNestVo subjectNestVo = new SubjectNestVo();
            BeanUtils.copyProperties(eduSubjectsOne.get(i), subjectNestVo);
            subjectNestVos.add(subjectNestVo);

            //将二级分类添加到对应的一级分类中
            ArrayList<SubjectVo> subjectVos = new ArrayList<>();
            Iterator<EduSubject> iterator = eduSubjectsTwo.iterator();
            while (iterator.hasNext()) {
                EduSubject next = iterator.next();
                SubjectVo subjectVo = new SubjectVo();
                if (subjectNestVo.getId().equals(next.getParentId())) {
                    BeanUtils.copyProperties(next, subjectVo);
                    subjectVos.add(subjectVo);
                    iterator.remove();
                }
            }
            subjectNestVo.setChildren(subjectVos);
        }

        //返回结果
        return subjectNestVos;
    }

    /**
     * 删除指定分类
     *
     * @param id
     * @return
     */
    @Override
    public Boolean deleteSubject(String id) {
        QueryWrapper<EduSubject> eduSubjectQueryWrapper = new QueryWrapper<>();
        eduSubjectQueryWrapper.eq("parent_id", id);
        List<EduSubject> eduSubjects = baseMapper.selectList(eduSubjectQueryWrapper);
        if (eduSubjects.size() != 0) {//存在二级
            return false;
        } else {//不存在二级
            int i = baseMapper.deleteById(id);
            return 1 > 0;
        }
    }

    /**
     * 添加节点
     *
     * @param eduSubject
     * @return
     */
    @Override
    public Boolean addSubject(EduSubject eduSubject) {
        try {
            this.addEduSubject(eduSubject.getParentId(), eduSubject.getTitle());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加一级或二级分类
     *
     * @param parentId
     * @param title
     * @return
     */
    private EduSubject addEduSubject(String parentId, String title) {
        EduSubject eduSubject = new EduSubject();
        eduSubject.setTitle(title);
        if (StringUtils.isEmpty(parentId)) {//一级
            eduSubject.setParentId("0");
        } else {
            eduSubject.setParentId(parentId);
        }
        eduSubject.setSort(0);
        baseMapper.insert(eduSubject);
        return eduSubject;
    }

    /**
     * 获得一级或二级分类
     *
     * @param rank
     * @return
     */
    private List<EduSubject> getEduSubjects(String rank) {
        QueryWrapper<EduSubject> queryWrapperOne = new QueryWrapper<>();
        if ("one".equals(rank)) {
            queryWrapperOne.eq("parent_id", 0);
        } else {
            queryWrapperOne.ne("parent_id", 0);
        }
        queryWrapperOne.orderByAsc("sort", "id");
        List<EduSubject> eduSubjects = baseMapper.selectList(queryWrapperOne);
        return eduSubjects;
    }

    /**
     * 判断一级或二级分类是否存在
     *
     * @param title
     * @param parent_id
     * @return
     */
    private EduSubject existSubject(String parent_id, String title) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title);
        if (StringUtils.isEmpty(parent_id)) {//一级
            wrapper.eq("parent_id", 0);
        } else {
            wrapper.eq("parent_id", parent_id);
        }
        return baseMapper.selectOne(wrapper);
    }

}
