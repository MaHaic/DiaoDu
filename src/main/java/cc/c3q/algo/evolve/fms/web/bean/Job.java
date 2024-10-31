package cc.c3q.algo.evolve.fms.web.bean;

import java.sql.Timestamp;

/*
create table if not exists a_fms_job(
		job_id int primary key auto_increment,
		name nvarchar(127) not null,
		demand text not null,
		work text not null,
		state smallint not null,
		update_at timestamp not null default CURRENT_TIMESTAMP,
		index(name)
	) character set=utf8;
*/
public class Job
{
	public int job_id;
	public String name;
	public String demand;
	public String work;
	public int state;
	public Timestamp update_at;
	
	public Job() {super();}
	public Job(int job_id, String name, String demand, String work, int state, Timestamp update_at) {
		super();
		this.job_id = job_id;
		this.name = name;
		this.demand = demand;
		this.work = work;
		this.state = state;
		this.update_at = update_at;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((demand == null) ? 0 : demand.hashCode());
		result = prime * result + job_id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + state;
		result = prime * result + ((update_at == null) ? 0 : update_at.hashCode());
		result = prime * result + ((work == null) ? 0 : work.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		Job other = (Job) obj;
		if (job_id != other.job_id) return false;
		if (state != other.state) return false;
		
		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (demand == null) {
			if (other.demand != null) return false;
		} else if (!demand.equals(other.demand)) {
			return false;
		}
		if (work == null) {
			if (other.work != null) return false;
		} else if (!work.equals(other.work)) {
			return false;
		}
		if (update_at == null) {
			if (other.update_at != null) return false;
		} else if (!update_at.equals(other.update_at)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "Job [job_id=" + job_id + ", name=" + name + ", demand=" + demand + ", work=" + work + ", state=" + state
				+ ", update_at=" + update_at + "]";
	}
}
