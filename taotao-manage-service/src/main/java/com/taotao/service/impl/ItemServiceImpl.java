package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: taotao
 * @description:
 * @author: mcy
 * @create: 2018-11-08 13:42
 **/
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    public TbItem getItemById(final long itemId) {
        //create example object
        com.taotao.pojo.TbItemExample example = new com.taotao.pojo.TbItemExample();
        //create quey condition
        TbItemExample.Criteria criteria = example.createCriteria();
        //parameters  of the incoming
        criteria.andIdEqualTo(itemId);

        //execution query
        List<com.taotao.pojo.TbItem> list = itemMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            com.taotao.pojo.TbItem item = list.get(0);
            return item;
        }
        return null;
    }

    public EUDataGridResult getItemList(final int page, final int rows) {
        TbItemExample tbItemExample = new TbItemExample();
        //paging process
        PageHelper.startPage(page, rows);
        List<TbItem> list = itemMapper.selectByExample(tbItemExample);
        //creat result object
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(list);
        //get total item
        PageInfo<TbItem> pageInfo = new PageInfo(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }
}
