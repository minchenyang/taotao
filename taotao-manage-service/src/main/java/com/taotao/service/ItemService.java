package com.taotao.service;

import com.taotao.pojo.EUDataGridResult;

public interface ItemService {
    com.taotao.pojo.TbItem getItemById(long itemId);
    EUDataGridResult getItemList(int page, int rows);
}
