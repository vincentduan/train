package com.train.service;

import java.util.List;
import java.util.Map;

import com.train.entity.BaseQuery;
import com.train.entity.Page;
import com.train.exception.TrainException;

public interface BaseService<T extends BaseQuery<T>> {
	/**
	 * 分页
	 * 
	 * @param t
	 * @return
	 * @throws TrainException
	 * @author 段丁阳
	 */
	public Page<T> selectByPage(T t) throws TrainException;

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 * @throws TrainException
	 * @author 段丁阳
	 */
	public T findById(Integer id) throws TrainException;

	/**
	 * 插入T类型的对象
	 * 
	 * @param t
	 * @return
	 * @throws TrainException
	 * @author 段丁阳
	 */
	public int insert(T t) throws TrainException;

	/**
	 * 未经分页处理
	 * 
	 * @param t
	 * @return
	 * @throws TrainException
	 * @author 段丁阳
	 */
	public List<Map<String, Object>> selectMapList(T t) throws TrainException;

	/**
	 * 分页处理
	 * 
	 * @param t
	 * @return
	 * @throws TrainException
	 * @author 段丁阳
	 */
	public Page<Map<String, Object>> selectMapPage(T t) throws TrainException;
}
