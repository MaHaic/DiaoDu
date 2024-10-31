package cc.c3q.algo.evolve.fms.web.svc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.c3q.algo.evolve.fms.web.bean.User;
import cc.c3q.algo.evolve.fms.web.dao.UserDao;
import cc.c3q.mysql.sqlControl.SqlTool;

@Service
public class UserSvc
{
	@Autowired
	public UserDao dao;
	
	public Integer access(String name, String pswd)
	{
		User user = new User();
		user.name = name;
		user.pswd = pswd;

		List<User> uids = dao.query(user, new String[] {"user_id"}, "name", "pswd");
		if(uids.size() == 0) return null;

		return uids.get(0).user_id;
	}
	public Integer create(String name, String pswd, Integer type)
	{
		if(type == null) type = 0;
		if(name.length() == 0) return null;
		if(hasName(name)) return null;
		
		User user = new User(0, name, pswd, type, null);
		user.update_at = SqlTool.currentTimestamp();
		
		return (int) dao.insert(user);
	}

	public boolean delete(int user_id)
	{
		User user = new User();
		user.user_id  = user_id;
		
		int row = dao.delete(user);
		return row == 1;
	}
	public Boolean update(int user_id, String name, String pswd, Integer type)
	{
		User user = this.query(user_id);
		if(user == null) return null;
		if(!user.name.equals(name) && hasName(name)) return false;
		
		if(name != null) user.name = name;
		if(pswd != null) user.pswd = pswd;
		if(type != null) user.type = type;
		user.update_at = SqlTool.currentTimestamp();
		
		int row = dao.update(user);
		return row == 1;
	}
	
	public User query(int user_id)
	{
		User user = new User();
		user.user_id  = user_id;
		
		List<User> users = dao.query(user);
		if(users.size() == 0) return null;
		
		return users.get(0);
	}
	public User query(String name)
	{
		User user = new User();
		user.name = name;
		
		List<User> users = dao.query(user, null, "name");
		if(users.size() == 0) return null;
		
		return users.get(0);
	}
	public List<User> list()
	{
		return dao.list();
	}
	
	public boolean hasName(String name)
	{
		User t = new User();
		t.name = name;
		return dao.count(t, "name") != 0;
	}
}
