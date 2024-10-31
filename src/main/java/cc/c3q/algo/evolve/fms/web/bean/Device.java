package cc.c3q.algo.evolve.fms.web.bean;

import java.sql.Timestamp;

/*
create table if not exists a_fms_device(
		device_id int primary key auto_increment,
		name nvarchar(127) not null,
		label nvarchar(127) not null default '',
		count int not null default 0,
		power int not null default 0,
		ip nvarchar(64) not null default '',
		update_at timestamp not null default CURRENT_TIMESTAMP,
		index(name)
	) character set=utf8;
*/
public class Device
{
	public int device_id;
	public String name;
	public String label;
	public int count;
	public int power;
	public String ip;
	public Timestamp update_at;
	
	public Device() {super();}
	public Device(int device_id, String name, String label, int count, int power, String ip, Timestamp update_at) {
		super();
		this.device_id = device_id;
		this.name = name;
		this.label = label;
		this.count = count;
		this.power = power;
		this.ip = ip;
		this.update_at = update_at;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		result = prime * result + device_id;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + power;
		result = prime * result + ((update_at == null) ? 0 : update_at.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		Device other = (Device) obj;
		if (count != other.count) return false;
		if (device_id != other.device_id) return false;
		if (ip == null) {
			if (other.ip != null) return false;
		} else if (!ip.equals(other.ip)) {
			return false;
		}
		if (label == null) {
			if (other.label != null) return false;
		} else if (!label.equals(other.label)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (power != other.power) return false;
		if (update_at == null) {
			if (other.update_at != null) return false;
		} else if (!update_at.equals(other.update_at)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "Device [device_id=" + device_id + ", name=" + name + ", label=" + label + ", count=" + count
				+ ", power=" + power + ", ip=" + ip + ", update_at=" + update_at + "]";
	}
}
