package cn.hdu.virtual_experiment.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 26074 on 2019/12/30.
 */
@Service
@Slf4j
public class AccessInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        try {
            response.setContentType("utf-8");
            response.getWriter().print("不可访问");
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
