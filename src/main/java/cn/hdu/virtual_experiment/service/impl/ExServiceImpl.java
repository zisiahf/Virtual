package cn.hdu.virtual_experiment.service.impl;

import cn.hdu.virtual_experiment.dao.ExMapper;
import cn.hdu.virtual_experiment.domain.Ex_Info;
import cn.hdu.virtual_experiment.service.ExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExServiceImpl implements ExService {

    @Autowired
    ExMapper exMapper;

    @Override
    public List<Ex_Info> getEx() {
        return exMapper.selectEx();
    }
}
