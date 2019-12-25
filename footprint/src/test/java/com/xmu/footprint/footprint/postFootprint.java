package com.xmu.footprint.footprint;

import com.xmu.footprint.domain.FootprintItemPo;
import com.xmu.footprint.util.JacksonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author
 * @date
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class postFootprint {
    @Value("http://112.124.5.232:8012/footprintService/footprints")
    String url;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void postRight() throws Exception {
        /*准备数据*/
        FootprintItemPo footprintItemPo=new FootprintItemPo();
        footprintItemPo.setUserId(1);
        footprintItemPo.setGoodsId(4);

        /*设置请求头部*/
        URI uri=new URI(url);
        HttpHeaders httpHeaders=testRestTemplate.headForHeaders(uri);
        HttpEntity httpEntity=new HttpEntity(footprintItemPo,httpHeaders);

        /*exchange方式模拟HTTP请求*/
        ResponseEntity<String> response=testRestTemplate.exchange(uri, HttpMethod.POST,httpEntity,String.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());

        /*取得响应体*/
        String body=response.getBody();
        Integer errno= JacksonUtil.parseInteger(body,"errno");
        assertEquals(0,errno);

        /*assert判断*/
        FootprintItemPo testFootprintItemPo=JacksonUtil.parseObject(body,"data",FootprintItemPo.class);
        assertEquals(footprintItemPo.getUserId(),testFootprintItemPo.getUserId());
        assertEquals(footprintItemPo.getGoodsId(),testFootprintItemPo.getGoodsId());
    }
}
