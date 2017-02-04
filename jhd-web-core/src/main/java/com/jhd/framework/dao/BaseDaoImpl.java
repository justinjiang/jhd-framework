package com.jhd.framework.dao;

import com.jbs.framework.exception.JbsException;
import com.jbs.framework.model.BaseModel;
import com.jbs.framework.model.BaseModelImpl;
import com.jbs.framework.model.Pagination;
import com.jbs.framework.util.BeanUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.util.ClassUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class BaseDaoImpl<T extends BaseModel, PK extends Serializable>
		extends SqlSessionDaoSupport implements BaseDao<T, PK> {

	protected final Log log = LogFactory.getLog(getClass());

	private Class<T> persistentClass;

	public String getClassName(){
		return ClassUtils.getShortName(this.persistentClass);
	}

	public BaseDaoImpl(final Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	/**
	 * 检查主键值是否存在
	 * @param pk
	 * @return
     */
	private boolean existsPKValue(Object pk){
		if (pk instanceof Integer){
			return ((Integer) pk).intValue()>0;
		}else if (pk instanceof Long){
			return ((Long) pk).longValue()>0;
		}else if (pk instanceof String){
			return ((String) pk).length()>0;
		}
		return true;
	}
	private String getPKValue(Object pk){
		if (pk instanceof Integer){
			return ((Integer) pk)+"";
		}else if (pk instanceof Long){
			return ((Long) pk)+"";
		}else if (pk instanceof String){
			return ((String) pk);
		}
		return "";
	}

	/**
	 * 保存对象
	 * @param object
	 * @return
     */
	public T save(T object) {
		String className = getClassName();
		Object primaryKey = IBatisDaoUtils.getPrimaryKeyValue(object);
		String keyId = null;
		// 判断id是否有值
		if (primaryKey != null && existsPKValue(primaryKey)) {
			keyId = getPKValue(primaryKey);
		}
		if (StringUtils.isBlank(keyId)) {
			//自增字段更新
			IBatisDaoUtils.prepareObjectForSaveOrUpdate(object);
			//IBatisDaoUtils.setPrimaryKey(object, primaryKey);
		}
		getSqlSession().insert(
				IBatisDaoUtils.getInsertQuery(className), object);

		if (IBatisDaoUtils.getPrimaryKeyValue(object) == null) {
			log.error("sorry, '" + this.persistentClass + "' object with id '"
					+ primaryKey + "' not found...");
			JbsException e = new JbsException("对不起,保存失败......");
			e.addParam(getClassName());
			String parma = (primaryKey == null) ? null : getPKValue(primaryKey);
			e.addParam(parma);
			throw e;
		} else {
			return object;
		}
	}

	/**
	 * 保存或更新对象
	 * @param object
	 * @return
	 */
	@Deprecated
	public T saveOrUpdate(final T object) {
		String className = getClassName();
		Object primaryKey = IBatisDaoUtils.getPrimaryKeyValue(object);
		int affectRecNum = 0;
		String keyId = null;
		// 判断id是否有值
		if (primaryKey != null && existsPKValue(primaryKey)) {
			keyId = getPKValue(primaryKey);
		}
		if (StringUtils.isBlank(keyId)) {
			// 保存
			IBatisDaoUtils.prepareObjectForSaveOrUpdate(object);
			getSqlSession().insert(
					IBatisDaoUtils.getInsertQuery(className), object);
			//IBatisDaoUtils.setPrimaryKey(object, primaryKey);
		} else {
			//否则更新
			IBatisDaoUtils.prepareObjectForSaveOrUpdate(object);
			affectRecNum = getSqlSession().update(
					IBatisDaoUtils.getUpdateQuery(className), object);
			if (affectRecNum == 0) {
				JbsException e = new JbsException("对不起,更新的条数为空........");
				String parma = (primaryKey == null) ? null : getPKValue(primaryKey);
				e.addParam(parma);
				throw e;
			}
		}
		// 判断id是否有值
		if (IBatisDaoUtils.getPrimaryKeyValue(object) == null) {
			log.warn("sorry, '" + this.persistentClass + "' object with id '"
					+ primaryKey + "' not found...");
			JbsException e = new JbsException("对不起,保存或者更新失败");
			e.addParam(getClassName());
			String parma = (primaryKey == null) ? null : getPKValue(primaryKey);
			e.addParam(parma);
			throw e;
		} else {
			return object;
		}
	}

	/**
	 * 批量保存
	 * @param list
	 * @return
	 */
	public int batchSave(List<T> list){
		if (list==null || list.size()==0){
			log.error("batch save objects not found...");
			return 0;
		}
		int affectRecNum = 0;
		String className = getClassName();
		for (T object : list){
			Object primaryKey = IBatisDaoUtils.getPrimaryKeyValue(object);
			String keyId = null;
			// 判断id是否有值
			if (primaryKey != null && existsPKValue(primaryKey)) {
				keyId = getPKValue(primaryKey);
			}
			if (StringUtils.isBlank(keyId)) {
				IBatisDaoUtils.prepareObjectForSaveOrUpdate(object);
			}
		}
		affectRecNum = getSqlSession().insert(
				IBatisDaoUtils.getBatchInsertQuery(className), list);

		if (affectRecNum == 0) {
			JbsException e = new JbsException("对不起,批量保存失败......");
			e.addParam(getClassName());
//			String parma = (primaryKey == null) ? null : getPKValue(primaryKey);
//			e.addParam(parma);
			throw e;
		}
		return affectRecNum;
	}

	/**
	 * 更新指定条件的对象
	 * @param object
	 * @return
	 */
	public int updateSelective(T object) {
		String className = getClassName();
		Object primaryKey = IBatisDaoUtils.getPrimaryKeyValue(object);
		int affectRecNum = 0;
		// 判断id是否有值
		if (primaryKey != null && existsPKValue(primaryKey)) {
			IBatisDaoUtils.prepareObjectForSaveOrUpdate(object);
			affectRecNum = getSqlSession().update(
					IBatisDaoUtils.getUpdateSelectiveQuery(className), object);
			if(affectRecNum == 0){
				JbsException e = new JbsException("对不起,更新失败,更新的条数为空........");
				e.addParam(getClassName());
				String parma = (primaryKey == null) ? null : getPKValue(primaryKey);
				e.addParam(parma);
				throw e;
			}
		} else {
			log.warn("sorry, '" + this.persistentClass + "' object with id '"
					+ primaryKey + "' not found...");
			JbsException e = new JbsException("对不起,不存在该记录,更新失败");
			e.addParam(getClassName());
			String parma = (primaryKey == null) ? null : getPKValue(primaryKey);
			e.addParam(parma);
			throw e;
		}
		return affectRecNum;
	}

	/**
	 * 更新完整对象
	 *
	 * @param object
	 * @return
     */
	public int updateAll(T object){
		String className = getClassName();
		Object primaryKey = IBatisDaoUtils.getPrimaryKeyValue(object);
		String keyId = null;
		int affectRecNum = 0;
		if (primaryKey != null && existsPKValue(primaryKey)) {
			keyId = primaryKey.toString();
		}
		if(StringUtils.isNotBlank(keyId)){
			IBatisDaoUtils.prepareObjectForSaveOrUpdate(object);
			affectRecNum = getSqlSession().update(
					IBatisDaoUtils.getUpdateQuery(className), object);
			if (affectRecNum == 0) {
				JbsException e = new JbsException("对不起,更新失败,更新的条数为空........");
				e.addParam(getClassName());
				String parma = (primaryKey == null) ? null : getPKValue(primaryKey);
				e.addParam(parma);
				throw e;
			}
		}else{
			JbsException e = new JbsException("对不起,更新失败,对象主键为空........");
			e.addParam(getClassName());
			String parma = keyId;
			e.addParam(parma);
			throw e;
		}
		return affectRecNum;
	}

	/**
	 * 对象是否存在
	 * @param id
	 * @return
	 */
	public boolean exists(PK id) {
		T object = (T) getSqlSession().selectOne(
				IBatisDaoUtils.getObjectQuery(getClassName()), id);
		return object != null;
	}

	/**
	 * 删除对象
	 * @param id
	 */
	public void delete(PK id) {
		getSqlSession().delete(IBatisDaoUtils.getDeleteQuery(getClassName()), id);
	}

	/**
	 * 查询所有记录
	 *
	 * @return
     */
	public List<T> getAll() {
		return getSqlSession().selectList(
				IBatisDaoUtils.getAllObjectQuery(getClassName()), null);
	}

	/**
	 * 根据ID取得对象
	 *
	 * @param id
     * @return
     */
	public T getById(PK id) {
		T object = (T) getSqlSession().selectOne(
				IBatisDaoUtils.getObjectQuery(getClassName()), id);
		return object;
	}

	public Pagination getPagedObjects(String statementName, BaseModelImpl dto) {
		Pagination pg = new Pagination();
		//dto.setNeedCount(true);
		Object count = getSqlSession().selectOne(
				IBatisDaoUtils.getCountQuery(statementName), dto);
		int totalCount = Integer.parseInt(count.toString());

		dto.setTotalCount(totalCount);
		dto.calStart();
		//dto.setNeedCount(false);		
		List rstList = getSqlSession().selectList(statementName, dto);
		BeanUtil.copyProperties(pg, dto);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);
		return pg;
	}
	
	/**
     * 用ibatis自带的分页查询
     */
	public List<T> getPageList(BaseModelImpl dto){
		String statementName = IBatisDaoUtils.getListWithPg(getClassName());
		String queryConutName = IBatisDaoUtils.getCountWithPg(getClassName());
		if( (dto.getTotalCount()==0) || (dto.getTotalCount()>0 && dto.getNeedCount()) ){
			dto.setTotalCount((Integer)getSqlSession().selectOne(queryConutName,dto));
		}
		RowBounds rowBounds = new RowBounds(dto.getStart(), dto.getLimit());
		return getSqlSession().selectList(statementName, dto, rowBounds);
	}

	/**
	 * 不带分页的查询
	 * @param dto
	 * @return
     */
	public List<T> getList(BaseModelImpl dto){
		String statementName = IBatisDaoUtils.getListWithPg(getClassName());
		return getSqlSession().selectList(statementName, dto);
	}

	/**
	 * 获得查询记录数
	 * @param dto
	 * @return
     */
	public int count(BaseModelImpl dto) {
		String queryConutName = IBatisDaoUtils.getCountWithPg(getClassName());
		return (Integer) getSqlSession().selectOne(queryConutName, dto);

	}

	/**
	 * 自定义条件的分页查询
	 * @param params
	 * @return
     */
	public List<T> getCustomListPage(Map<String,Object> params){
		String statementName = IBatisDaoUtils.getCustomList(getClassName());
		RowBounds rowBounds = null;
		Integer start = (Integer) params.get("start");
		Integer limit = (Integer) params.get("limit");
		if (start!=null && limit!=null) {
			rowBounds = new RowBounds(start, limit);
		}
		return getSqlSession().selectList(statementName, params, rowBounds);
	}
}
