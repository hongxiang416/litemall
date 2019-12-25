package com.xmu.footprint.footprint;


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
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class getFootprints {
    @Value("http://112.124.5.232:8012/footprintService/footprints")
    String url;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void detailRight() throws Exception {
        /*可以查询出相同user的多个footprints*/
        /*设置请求头部*/
        URI uri=new URI(url);
        HttpHeaders httpHeaders=testRestTemplate.headForHeaders(uri);
        HttpEntity httpEntity=new HttpEntity(httpHeaders);

        /*exchange方法模拟HTTP请求*/
        ResponseEntity<String> response=testRestTemplate.exchange(uri, HttpMethod.GET,httpEntity,String.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());

        /*取得响应体*/
        String body=response.getBody();
        Integer errno= JacksonUtil.parseInteger(body,"errno");

        Integer status=JacksonUtil.parseInteger(body,"status");
        assertNotEquals(500,status);
        List<HashMap> lists=JacksonUtil.parseObject(body,"data",List.class);
        for(HashMap item : lists) {
            assertNotEquals(null,item.get("id"));
        }
    }
}
