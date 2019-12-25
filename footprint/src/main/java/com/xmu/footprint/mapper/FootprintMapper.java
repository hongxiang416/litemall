package com.xmu.footprint.mapper;

import com.xmu.footprint.domain.FootprintItem;
import com.xmu.footprint.domain.FootprintItemPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
/**
 * @author
 */
public interface FootprintMapper {
    /**
     * 根据用户id或商品id找到足迹
     * @param userId
     * @param goodsId
     * @return
     */
    public List<FootprintItem> listFootprintByCondition(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId);

    /**
     * 根据用户id找到用户的所有评论
     * @param userId
     * @return
     */
    public List<FootprintItem> listFootprintByUserId(Integer userId);

//    /**
//     * 删除足迹
//     * @param id
//     * @return
//     */
//    public int deleteFootprintById(Integer id);

    /**
     * 根据用户id和商品id找到足迹
     * @param userId
     * @param goodsId
     * @return
     */
    public List<FootprintItemPo> findFootprintItem(Integer userId,Integer goodsId);

    /**
     * 添加足迹
     * @param footprintItemPo
     * @return
     */
    public int addFootprint(FootprintItemPo footprintItemPo);

    /**
     * 更新足迹信息
     * @param footprintItemPo
     * @return
     */
    public int updateFootprint(FootprintItemPo footprintItemPo);

//    /**
//     * 根据id找到足迹
//     * @param id
//     * @return
//     */
//    public FootprintItemPo getFootprintItemPoById(Integer id);
}
