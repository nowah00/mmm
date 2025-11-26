package com.ssg.mmm.item.service;

import com.ssg.mmm.item.dto.ItemRead;
import java.util.List;

public interface ItemService {

    List<ItemRead> findAll();

    List<ItemRead> findAll(List<Integer> ids);
}
