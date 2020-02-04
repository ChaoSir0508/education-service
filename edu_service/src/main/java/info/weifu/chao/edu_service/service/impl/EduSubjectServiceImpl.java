package info.weifu.chao.edu_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import info.weifu.chao.edu_service.exception.EduException;
import info.weifu.chao.edu_service.mapper.EduSubjectMapper;
import info.weifu.chao.edu_service.pojo.EduSubject;
import info.weifu.chao.edu_service.service.EduSubjectService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.omg.PortableInterceptor.ServerRequestInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
     * POI操作
     *
     * @param file
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
                EduSubject existSubject = this.existSubject(valueOne);
                String parent_id = null;
                if (existSubject == null) {
                    //保存一级分类数据
                    EduSubject eduSubject = new EduSubject();
                    eduSubject.setTitle(valueOne);
                    eduSubject.setParentId("0");
                    eduSubject.setSort(0);
                    baseMapper.insert(eduSubject);
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
                EduSubject existSubjectTwo = this.existSubjectTwo(valueTwo, parent_id);
                if (existSubjectTwo == null) {
                    //保存二级分类数据
                    EduSubject eduSubject = new EduSubject();
                    eduSubject.setTitle(valueTwo);
                    eduSubject.setParentId(parent_id);
                    eduSubject.setSort(0);
                    baseMapper.insert(eduSubject);
                }
            }
            return message;
        } catch (IOException e) {
            throw new EduException(20001, "数据导入异常");
        }
    }

    private EduSubject existSubject(String title) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title);
        wrapper.eq("parent_id", 0);
        return baseMapper.selectOne(wrapper);
    }

    private EduSubject existSubjectTwo(String title, String parent_id) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title);
        wrapper.eq("parent_id", parent_id);
        return baseMapper.selectOne(wrapper);
    }

}
