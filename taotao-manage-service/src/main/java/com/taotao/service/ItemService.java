package com.taotao.service;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
    com.taotao.pojo.TbItem getItemById(long itemId);
    EUDataGridResult getItemList(int page, int rows);
    void saveItem(TbItem item, String desc, String itemParams) throws Exception;
}
