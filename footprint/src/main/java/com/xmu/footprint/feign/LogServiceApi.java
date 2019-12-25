package com.xmu.footprint.feign;

import com.xmu.footprint.domain.Log;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(value = "logService",url = "http://121.199.2.219:8843")
@FeignClient(value = "logService",url = "http://47.96.159.71:8846")
@Service
/**
 * @author
 *@RequestMapping("/logService")
*/
public interface LogServiceApi {
    /**
     * 管理员操作日志
     * @param log
     */
    @PostMapping("/log")
    void addLog(@RequestBody Log log);
}
