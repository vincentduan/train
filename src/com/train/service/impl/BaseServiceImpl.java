package com.train.service.impl;

import java.util.List;
import java.util.Map;

import com.train.entity.BaseQuery;
import com.train.entity.Page;
import com.train.exception.TrainException;
import com.train.service.BaseService;

public class BaseServiceImpl<T extends BaseQuery<T>> implements BaseService<T> {

	@Override
	public Page<T> selectByPage(T t) throws TrainException {
		return null;
	}

	@Override
	public T findById(Integer id) throws TrainException {
		return null;
	}

	@Override
	public int insert(T t) throws TrainException {
		return 0;
	}

	@Override
	public List<Map<String, Object>> selectMapList(T t) throws TrainException {
		return null;
	}

	@Override
	public Page<Map<String, Object>> selectMapPage(T t) throws TrainException {
		return null;
	}

}
