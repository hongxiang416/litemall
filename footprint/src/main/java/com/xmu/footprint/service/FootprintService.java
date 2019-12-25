package com.xmu.footprint.service;

import com.xmu.footprint.domain.FootprintItem;
import com.xmu.footprint.domain.FootprintItemPo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
/**
 * @author
 */
public interface FootprintService {
    /**
     * 根据用户id或商品id查找足迹
     * @param userId
     * @param goodsId
     * @return
     */
    List<FootprintItem> listFootprintByCondition(Integer userId, Integer goodsId);

    /**
     * 根据用户id查找足迹
     * @param userId
     * @return
     */
    List<FootprintItem> listFootprintByUserId(Integer userId);

//    /**
//     * 删除足迹
//     * @param id
//     * @return
//     */
//    int deleteFootprintById(Integer id);

    /**
     * 根据用户id和商品id查找足迹
     * @param userId
     * @param goodsId
     * @return
     */
    List<FootprintItemPo> findFootprintItem(Integer userId,Integer goodsId);

    /**
     * 添加足迹
     * @param footprintItemPo
     * @return
     */
    int addFootprint(FootprintItemPo footprintItemPo);

//    /**
//     * 根据id找足迹
//     * @param id
//     * @return
//     */
//    FootprintItemPo getFootprintItemPoById(Integer id);

    /**
     * 更新足迹信息
     * @param footprintItemPo
     * @return
     */
    int updateFootprint(FootprintItemPo footprintItemPo);
}
