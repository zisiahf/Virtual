package cn.hdu.virtual_experiment.controller;

import cn.hdu.virtual_experiment.result.Result;
import cn.hdu.virtual_experiment.service.IndexService;
import cn.hdu.virtual_experiment.util.PageResult;
import cn.hdu.virtual_experiment.vo.OrderList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by 26074 on 2019/12/27.
 */
@Controller
@Slf4j
public class IndexController {

    @Autowired
    IndexService indexService;

    //返回首页实验列表页面
    @RequestMapping("/")
    public String showIndex() throws Exception{
        return "index";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Result<PageResult> list(int pageNum){
        PageResult pageResult = indexService.list(pageNum);
        return Result.success(pageResult);
    }

//    @RequestMapping("/{pages}")
//    public String showPage(@PathVariable String pages) {
//        return pages;
//    }
}
