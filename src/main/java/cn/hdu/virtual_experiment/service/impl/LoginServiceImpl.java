package cn.hdu.virtual_experiment.service.impl;

import cn.hdu.virtual_experiment.Init.NodeInit;
import cn.hdu.virtual_experiment.context.Variant;
import cn.hdu.virtual_experiment.dao.LoginMapper;
import cn.hdu.virtual_experiment.domain.Ex_Info;
import cn.hdu.virtual_experiment.domain.Node;
import cn.hdu.virtual_experiment.domain.Order_Info;
import cn.hdu.virtual_experiment.domain.Student_Info;
import cn.hdu.virtual_experiment.exception.GlobalException;
import cn.hdu.virtual_experiment.result.CodeMsg;
import cn.hdu.virtual_experiment.service.LoginService;
import cn.hdu.virtual_experiment.util.UUIDUtil;
import cn.hdu.virtual_experiment.vo.LoginVo;
import cn.hdu.virtual_experiment.vo.SessionVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Session;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * Created by 26074 on 2019/12/30.
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService{

    @Autowired
    LoginMapper loginMapper;


    @Transactional
    public String login(LoginVo loginVo, HttpServletRequest request) {
        log.info("登录信息：{}",loginVo.toString());
        //登录信息为空
        if(loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        //校验预约时间
        String oid = loginVo.getOid();
        //查询预约信息
        Order_Info order_info= loginMapper.selectOrderByOid(oid);
        System.out.println("查询的预约信息：" + order_info);

        // 1、校验节点状态
        checkNode(loginVo);

        // 2、检验用户名、密码、以及实验的预约情况是否属实
        Student_Info student_info =  checkUser(loginVo);


        // 这句代码逻辑有点蒙
        System.out.println("order_info.getEid():"+order_info.getEid());

        Ex_Info ex_info = loginMapper.selectExByEid(order_info.getEid());


        return setSessionAndReturnToken(student_info,ex_info,loginVo.getNode_number(),request);
    }

    // checkNode好像实现没有实现完整， 就是当前节点可使用以后没有修改节点状态
    private void checkNode(LoginVo loginVo) {
        //遍历node_number 看当前是否被占用 如果没有修改节点状态
        String node_numbers = loginVo.getNode_number();

        if(StringUtils.isEmpty(node_numbers)){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        for(String node : node_numbers.split(",")){
            System.out.println("node: " + node);
            if(Variant.nodeMap.get(node).isUsed()){
                //节点已被使用
                throw new GlobalException(CodeMsg.NODE_ISUSED);
            }
        }
        //修改节点状态
    }

    //用户信息校验
    private Student_Info checkUser(LoginVo loginVo){
        //用户名密码校验
        String username = loginVo.getUsername();
        String password = loginVo.getPassword();

        Student_Info student_info = loginMapper.selectByUserNameAndPassword(username,password);
        if(student_info == null){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        log.info("用户名密码校验通过");

        //预约情况是否符合
        if(!student_info.getOid().equals(loginVo.getOid())){
            throw new GlobalException(CodeMsg.LOGIN_ERROR);
        }
        log.info("该用户属于本次预约用户");
        return student_info;
    }

    //设置session信息
    private String setSessionAndReturnToken(Student_Info student_info, Ex_Info ex_info, String node_number, HttpServletRequest request){
        //封装session
        log.info("设置session并返回token");
        SessionVo sessionVo = new SessionVo();

        System.out.println("student_info.getUsername:"+student_info.getUsername());
        sessionVo.setUsername(student_info.getUsername());

        System.out.println("ex_info:"+ex_info);
        System.out.println("ex_info.getEx_name:"+ex_info.getEx_name());
        sessionVo.setEx_Name(ex_info.getEx_name());
        System.out.println("node_number:"+node_number);
        sessionVo.setNode_number(node_number);
        System.out.println("ex_info.getPage:"+ex_info.getPage());
        sessionVo.setPage(ex_info.getPage());
        System.out.println("ex_info.getLen:"+ex_info.getLen());
        sessionVo.setData_len(ex_info.getLen());

        String token = UUIDUtil.uuid();

        HttpSession session = request.getSession();
        session.setAttribute(token,sessionVo);

        session.setMaxInactiveInterval(1000000);//设置session有效期
        log.info("设置session返回token完成,token = {}",token);
        return token;

    }
}
