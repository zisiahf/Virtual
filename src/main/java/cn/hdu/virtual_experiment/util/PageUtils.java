package cn.hdu.virtual_experiment.util;

import com.github.pagehelper.PageInfo;

/**
 * Created by 26074 on 2019/12/30.
 */
public class PageUtils {

    /**
     * 将分页信息封装到统一的接口
     * @param pageNum 页号
     * @param pageInfo
     * @return
     */
    public static PageResult getPageResult(PageInfo<?> pageInfo) {

        PageResult pageResult = new PageResult();

        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setTotalPages(pageInfo.getPages());
        pageResult.setContent(pageInfo.getList());

        return pageResult;
    }
}
