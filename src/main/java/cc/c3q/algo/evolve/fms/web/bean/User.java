package cc.c3q.algo.evolve.fms.web.bean;

import java.sql.Timestamp;

/*
create table if not exists a_fms_user(
		user_id int primary key auto_increment,
		name nvarchar(127) not null,
		pswd nvarchar(127) not null,
		type int not null default 0,
		update_at timestamp not null default CURRENT_TIMESTAMP,
		index(name)
	) character set=utf8;
*/

public class User
{
	public int user_id;
	public String name;
	public String pswd;
	public int type;		//0.user 1.admin
	public Timestamp update_at;
	
	public User() {super();}
	public User(int user_id, String name, String pswd, int type, Timestamp update_at)
	{
		super();
		this.user_id = user_id;
		this.name = name;
		this.pswd = pswd;
		this.type = type;
		this.update_at = update_at;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + user_id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pswd == null) ? 0 : pswd.hashCode());
		result = prime * result + type;
		result = prime * result + ((update_at == null) ? 0 : update_at.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		User other = (User) obj;
		if (user_id != other.user_id) return false;
		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (pswd == null) {
			if (other.pswd != null) return false;
		} else if (!pswd.equals(other.pswd)) {
			return false;
		}
		if (type != other.type) return false;
		if (update_at == null) {
			if (other.update_at != null) return false;
		} else if (!update_at.equals(other.update_at)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", name=" + name + ", pswd=" + pswd + ", type=" + type + ", update_at=" + update_at
				+ "]";
	}
}
