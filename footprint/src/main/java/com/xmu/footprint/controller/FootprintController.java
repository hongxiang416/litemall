package com.xmu.footprint.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xmu.footprint.domain.FootprintItem;
import com.xmu.footprint.domain.FootprintItemPo;
import com.xmu.footprint.domain.Log;
import com.xmu.footprint.feign.GoodsServiceApi;
import com.xmu.footprint.feign.LogServiceApi;
import com.xmu.footprint.service.FootprintService;
import com.xmu.footprint.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/")
/**
 * @author
 */
public class FootprintController {
    @Autowired
    private FootprintService footprintService;

    @Autowired
    GoodsServiceApi goodsServiceApi;

    @Autowired
    LogServiceApi logServiceApi;

    List<FootprintItem> footprintItemLists;
    FootprintItem footprintItem;

    /**
     * 管理员获取足迹列表/list
     *
     * @param page 分页页数
     * @param limit 分页大小
     * @return List<FootprintItem>，即获取的足迹列表
     */
    @GetMapping("/admin/footprints")
    public Object listFootprintByCondition(Integer userId, Integer goodsId,
                                           @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit) {
        Object retObj;
        Log log = new Log();
        log.setType(0);
        log.setActions("获取足迹列表");
        boolean legal;
        if(page<=0 || limit<=0) {
            legal=false;
        } else {
            if(userId != null && goodsId != null) {
                legal= (userId>0 && goodsId>0);
            } else if(userId != null && goodsId == null) {
                legal= (userId>0);
            } else if (userId == null && goodsId != null) {
                legal= (goodsId>0);
            } else if(userId==null && goodsId==null){
                legal= true;
            } else {
                legal=true;
            }
        }
        if(legal) {
            PageHelper.startPage(page,limit);
            List<FootprintItem> footprintItemList=footprintService.listFootprintByCondition(userId,goodsId);
            PageInfo<FootprintItem> footprintItemPageInfo=new PageInfo<>(footprintItemList);
            List<FootprintItem> pageList=footprintItemPageInfo.getList();

            footprintItemLists=new ArrayList<>();
            for(int i=0;i<pageList.size();i++) {
                footprintItem=pageList.get(i);
                footprintItem.setGoodsPo(goodsServiceApi.getGoodsByIdIn(footprintItem.getGoodsId()));
                footprintItemLists.add(footprintItem);
            }

            if(footprintItemLists.size()>0) {
                retObj=ResponseUtil.ok(footprintItemLists);
                log.setStatusCode(1);
            }
            else {
                retObj=ResponseUtil.nofootprint();
                log.setStatusCode(1);
            }
        } else {
            retObj=ResponseUtil.badValue();
            log.setStatusCode(0);
        }
        logServiceApi.addLog(log);
        return retObj;
    }

    /**
     * 用户获取足迹列表/list
     *
     * @param page 分页页数
     * @param limit 分页大小
     * @return List<FootprintItem>，即用户的足迹列表
     * HttpServletRequest request
     */
    @GetMapping("/footprints")
    public Object listFootprintByUserId(HttpServletRequest request,
                                        @RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer limit) {
        Object retObj;
        String s=request.getHeader("id");

        if(s==null) {
            retObj=ResponseUtil.unlogin();
        }
        else {
            Integer userId=Integer.valueOf(s);
            if(userId<=0 || page<=0 || limit<=0)
            {
                retObj=ResponseUtil.badValue();
            }
            else {
                PageHelper.startPage(page,limit);
                List<FootprintItem> footprintItemList=footprintService.listFootprintByUserId(Integer.valueOf(userId));
                PageInfo<FootprintItem> footprintItemPageInfo=new PageInfo<>(footprintItemList);
                List<FootprintItem> pageList=footprintItemPageInfo.getList();

                footprintItemLists=new ArrayList<>();
                for(int i=0;i<pageList.size();i++) {
                    footprintItem=pageList.get(i);
                    footprintItem.setGoodsPo(goodsServiceApi.getGoodsByIdIn(footprintItem.getGoodsId()));
                    footprintItemLists.add(footprintItem);
                }

                if(footprintItemLists.size()>0) {
                    retObj=ResponseUtil.ok(footprintItemLists);
                }
                else {
                    retObj=ResponseUtil.nofootprint();
                }
            }
        }
        return retObj;
    }

    /**
     * 内部接口：添加足迹信息/add
     *
     * @param footprintItemPo
     * @return FootprintItemPo，即添加的足迹信息
     */
    @PostMapping("/footprints")
    public Object addFootprint(@RequestBody FootprintItemPo footprintItemPo) {
        Object retObj;
        boolean legal=footprintItemPo.getGoodsId()!=null && footprintItemPo.getUserId() !=null;
        if(legal) {
            int size=footprintService.findFootprintItem(footprintItemPo.getUserId(),footprintItemPo.getGoodsId()).size();
            if(size>0) {
                if(footprintService.updateFootprint(footprintItemPo)==1) {
                    retObj=ResponseUtil.ok(footprintService.findFootprintItem(footprintItemPo.getUserId(),footprintItemPo.getGoodsId()).get(0));
                }
                else {
                    retObj=ResponseUtil.addfootprintfail();
                }
                return retObj;
            }//用户浏览过商品，更改上次浏览时间
            //用户首次浏览商品，添加足迹信息
            if((footprintService.addFootprint(footprintItemPo))==1){
                retObj=ResponseUtil.ok(footprintService.findFootprintItem(footprintItemPo.getUserId(),footprintItemPo.getGoodsId()).get(0));
            }
            else {
                retObj=ResponseUtil.addfootprintfail();
            }
        }
        else {
            retObj=ResponseUtil.badValue();
        }
        return retObj;
    }
}
