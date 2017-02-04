package com.jhd.framework.dao;

import com.jbs.framework.model.BaseModel;
import com.jbs.framework.model.BaseModelImpl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * Dao基类
 * @author justin.jiang
 *
 * @param <T>
 * @param <PK>
 */
public interface BaseDao <T extends BaseModel, PK extends Serializable> {

    /**
     * 保存
     */
    T save(T object);

    /**
     * 保存或者更新数据,根据id来判断
     */
    T saveOrUpdate(T object);

    /**
     * 批量保存
     * @param list
     * @return
     */
    int batchSave(List<T> list);

    /**
     * 更新选中的数据字段
     */
    int updateSelective(T object);

    /**
     * 更新全部字段
     */
    int updateAll(T object);

    /**
     * 通过ID判断该记录是否存在
     */
    boolean exists(PK id);

    /**
     * 通过ID删除记录
     */
    void delete(PK id);

	/**
	 * 获取所有的记录
     */
    List<T> getAll();

    /**
     * 通过ID查询记录
     */
    T getById(PK id);
    
    /**
     * 分页查询
     */
    List<T> getPageList(BaseModelImpl model);

    /**
     * 根据对象查询数据
     *
     * @param model
     * @return
     */
    List<T> getList(BaseModelImpl model);

    /**
     * 获取记录总数
     * @param model
     * @return
     */
    int count(BaseModelImpl model);

    /**
     * 自定义条件查询
     * @param params
     * @return
     */
    List<T> getCustomListPage(Map<String, Object> params);
}
