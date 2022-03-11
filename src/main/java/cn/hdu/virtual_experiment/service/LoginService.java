package cn.hdu.virtual_experiment.service;

import cn.hdu.virtual_experiment.vo.LoginVo;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 26074 on 2019/12/30.
 */
public interface LoginService {

    String login(LoginVo loginVo,HttpServletRequest request);
}
