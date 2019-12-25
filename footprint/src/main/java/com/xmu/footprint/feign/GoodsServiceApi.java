package com.xmu.footprint.feign;

import com.xmu.footprint.domain.GoodsPo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="goodsInfoService",url = "http://112.124.5.232:8017")
//@FeignClient(value="goodsInfoService")
@Service
//@RequestMapping("/")
/**
 * @author
 */
public interface GoodsServiceApi {
    /**
     * 根据商品id查找商品
     * @param id
     * @return
     */
    @GetMapping("/inner/goods/{id}")
    GoodsPo getGoodsByIdIn(@PathVariable(value = "id") Integer id);
}
