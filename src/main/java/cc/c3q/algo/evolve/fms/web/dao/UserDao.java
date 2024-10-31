package cc.c3q.algo.evolve.fms.web.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import cc.c3q.algo.evolve.fms.web.bean.User;

@Repository
public class UserDao extends BaseDao<User>
{
	protected String[] noPswdFields;
	public UserDao()
	{
		super(User.class, "a_fms_user", "user_id");
		
		int p = 0;
		noPswdFields = new String[fieldNames.length-1];
		for(String col : fieldNames) if(!"pswd".equals(col)) noPswdFields[p++] = col;
	}
	
	protected String[] filterPswd(String[] cols)
	{
		//屏蔽密码
		if(cols == null) {
			cols = Arrays.copyOf(noPswdFields, noPswdFields.length);
		} else {
			ArrayList<String> list = new ArrayList<String>(cols.length);
			for(String col : cols) if(!"pswd".equals(col)) list.add(col);
			
			cols = new String[list.size()];
			cols = list.toArray(cols);
			list.clear();
		}
		return cols;
	}
	
	@Override
	public List<User> query(User t, String[] colsQuery, String... colsWhere)
	{
		return super.query(t, filterPswd(colsQuery), colsWhere);
	}
	
	@Override
	public int update(User t, String[] colsUpdate, String... colsWhere)
	{
		if(t.pswd == null) colsUpdate = filterPswd(colsUpdate);
		
		return super.update(t, colsUpdate, colsWhere);
	}
	
}
