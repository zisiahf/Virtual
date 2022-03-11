package cn.hdu.virtual_experiment.service.impl;

import cn.hdu.virtual_experiment.dao.StudentMapper;
import cn.hdu.virtual_experiment.domain.Student_Info;
import cn.hdu.virtual_experiment.exception.GlobalException;
import cn.hdu.virtual_experiment.result.CodeMsg;
import cn.hdu.virtual_experiment.service.StudentService;
import cn.hdu.virtual_experiment.util.MD5Util;
import cn.hdu.virtual_experiment.util.PageResult;
import cn.hdu.virtual_experiment.util.PageUtils;
import cn.hdu.virtual_experiment.util.UUIDUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static cn.hdu.virtual_experiment.context.Const.PAGESIZE;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    //根据oid和tid查询学生
    public PageResult getUserByTidAndOid(String tid, String oid, int pageNum) {
        PageHelper.startPage(pageNum, PAGESIZE);
        List<Student_Info> myStudents = studentMapper.selectUserByTidAndOid(tid,oid);

        PageInfo pageInfo = new PageInfo<Student_Info>(myStudents);

        return PageUtils.getPageResult(pageInfo);
    }

    //添加学生
    public void addStudent(String oid, String tid, Student_Info student_info) {
        //判断该用户名是否已经存在 不允许重复添加
        String username = student_info.getUsername();
        Student_Info s = studentMapper.selectStudentByName(username, oid);
        if(s != null){
            throw new GlobalException(CodeMsg.USERNAME_ISUSED);
        }
        System.out.println("学生的密码：" + student_info.getPassword());
        String password = MD5Util.md5(student_info.getPassword());
        student_info.setPassword(password);
        System.out.println("学生经过加密的密码：" + student_info.getPassword());
        student_info.setOid(oid);
        String sid = UUIDUtil.uuid();
        student_info.setSid(sid);
        student_info.setTid(tid);
        studentMapper.insertStudent(student_info);
    }

    //删除学生
    public void deleteStudentBySidAndOidAndTid(String sid, String oid, String tid) {
        studentMapper.deleteStudent(sid,oid,tid);
    }

    //上传学生
    @Transactional
    public void uploadStudent(MultipartFile file, String tid, String oid) {
        String fileName = file.getOriginalFilename();
        //导入Excel
        importExcel(fileName,file,tid,oid);
    }

    //导出模板
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {

        try {
            String fileName = System.currentTimeMillis() +".xls".toString(); // 文件的默认保存名
            InputStream inStream = new FileInputStream("/usr/local/IDEA_Project/virtual_experiment/src/main/resources/static/批量添加学生模板.xls");// 文件的存放路径
            // 设置输出的格式
            response.reset();
            response.setContentType("bin");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            // 循环取出流中的数据
            byte[] b = new byte[100];
            int len;
            while ((len = inStream.read(b)) > 0){
                response.getOutputStream().write(b, 0, len);
            }
            inStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除该预约下所有学生
    public void deleteStudentByOid(String oid) {
        studentMapper.deleteStudentByOid(oid);
    }


    private void importExcel(String fileName,MultipartFile file,String tid, String oid){
        List<Student_Info> studentList = new ArrayList<>();
        //上传文件格式不正确
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new GlobalException(CodeMsg.FORMAT_ERROR);
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        try {
            InputStream is = file.getInputStream();
            Workbook wb = null;
            if (isExcel2003) {
                wb = new HSSFWorkbook(is);
            } else {
                wb = new XSSFWorkbook(is);

            }
            Sheet sheet = wb.getSheetAt(0);
            if(sheet == null){
                throw new GlobalException(CodeMsg.SHEET_ISNULL);
            }
            studentList = getStudentList(sheet,tid,oid);
            for (Student_Info student_info : studentList) {
                //先查询是否有重复学生 根据tid和oid和姓名
                Student_Info s = studentMapper.selectStudentByName(student_info.getUsername(),student_info.getOid());
                if(s != null){
                    continue;
                }
                studentMapper.insertStudent(student_info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //将表格数据封装成list
    private List<Student_Info> getStudentList(Sheet sheet,String tid, String oid) {
        List<Student_Info> studenList = new ArrayList<>();
        Student_Info student_info;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            student_info = new Student_Info();
            Row row = sheet.getRow(i);
            if (row == null){
                continue;
            }
            //数量
            //int num = sheet.getLastRowNum();
            Cell cell0 = row.getCell(0);
            cell0.setCellType(CellType.STRING);
            String username = cell0.getStringCellValue();
            if(username == null || username.isEmpty()){
                throw new GlobalException(CodeMsg.USERNAME_ISNULL);
            }
            Cell cell1 = row.getCell(1);
            cell1.setCellType(CellType.STRING);
            String password = MD5Util.md5(cell1.getStringCellValue());
            if(password == null || password.isEmpty()){
                throw new GlobalException(CodeMsg.PASSWORD_ISNULL);
            }
            Cell cell2 = row.getCell(2);
            cell2.setCellType(CellType.STRING);
            String name = cell2.getStringCellValue();
            if(name == null || name.isEmpty()){
                throw new GlobalException(CodeMsg.NAME_ISNULL);
            }
            Cell cell3 = row.getCell(3);
            cell3.setCellType(CellType.STRING);
            String school = cell3.getStringCellValue();

            Cell cell4 = row.getCell(4);
            cell4.setCellType(CellType.STRING);
            String major = row.getCell(4).getStringCellValue();
            student_info.setSid(UUIDUtil.uuid());
            student_info.setTid(tid);
            student_info.setOid(oid);
            student_info.setUsername(username);
            student_info.setPassword(password);
            student_info.setName(name);
            student_info.setSchool(school);
            student_info.setMajor(major);
            studenList.add(student_info);
        }
        return studenList;
    }
}
