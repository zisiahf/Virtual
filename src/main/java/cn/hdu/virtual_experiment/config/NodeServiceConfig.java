package cn.hdu.virtual_experiment.config;

import cn.hdu.virtual_experiment.service.NodeService;
import cn.hdu.virtual_experiment.service.impl.NodeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * NodeService配置类
 */
@Configuration
public class NodeServiceConfig {

    @Bean
    @Scope("prototype")
    public NodeService getNodeService(){
        return new NodeServiceImpl();
    }
}
