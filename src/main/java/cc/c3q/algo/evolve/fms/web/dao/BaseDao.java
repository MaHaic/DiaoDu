package cc.c3q.algo.evolve.fms.web.dao;

import org.springframework.beans.factory.annotation.Autowired;

import cc.c3q.mysql.mvc.AbstractDao;
import cc.c3q.mysql.sqlControl.SqlControl;

public class BaseDao<T> extends AbstractDao<T>
{
	@Autowired
	public SqlControl jdbc;
	
	public BaseDao(){super();}
	public BaseDao(Class<T> cls, String tbName, String keyName)
	{
		super(cls, tbName, keyName);
	}
	
	@Override
	protected SqlControl getSqlControl()
	{
		return jdbc;
	}
}
