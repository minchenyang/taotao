package com.taotao.service;

import com.taotao.pojo.TbItemCat;

import java.util.List;

/**
 * @program: taotao
 * @description:
 * @author: mcy
 * @create: 2018-11-08 19:21
 **/
public interface ItemCatService {
    List<TbItemCat> getItemCatList(Long parentId);
}
