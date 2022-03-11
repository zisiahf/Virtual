package cn.hdu.virtual_experiment.config;

import cn.hdu.virtual_experiment.util.SerialComm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Created by 26074 on 2020/1/7.
 */
@Configuration
public class SerialConfig {

    @Bean
    @Scope("prototype")
    public SerialComm getSerialComm(){
        return new SerialComm();
    }
}
