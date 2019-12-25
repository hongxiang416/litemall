package com.xmu.footprint.service.serviceimpl;

import com.xmu.footprint.domain.FootprintItem;
import com.xmu.footprint.domain.FootprintItemPo;
import com.xmu.footprint.mapper.FootprintMapper;
import com.xmu.footprint.service.FootprintService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
/**
 * @author
 */
public class FootprintServiceImpl  implements FootprintService {
    @Resource
    private FootprintMapper footprintMapper;

    @Override
    public List<FootprintItem> listFootprintByCondition(Integer userId, Integer goodsId) {
        return footprintMapper.listFootprintByCondition(userId,goodsId);
    }

    @Override
    public List<FootprintItem> listFootprintByUserId(Integer userId) {
        return footprintMapper.listFootprintByUserId(userId);
    }


    @Override
    public List<FootprintItemPo> findFootprintItem(Integer userId,Integer goodsId) {
        return footprintMapper.findFootprintItem(userId,goodsId);
    }

    @Override
    public int addFootprint(FootprintItemPo footprintItemPo) {
        return footprintMapper.addFootprint(footprintItemPo);
    }

    @Override
    public int updateFootprint(FootprintItemPo footprintItemPo) {
        return footprintMapper.updateFootprint(footprintItemPo);
   }
}
