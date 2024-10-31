package cc.c3q.algo.evolve.fms.web.svc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.c3q.algo.evolve.fms.web.bean.Device;
import cc.c3q.algo.evolve.fms.web.dao.DeviceDao;
import cc.c3q.mysql.sqlControl.SqlTool;

@Service
public class DeviceSvc
{
	@Autowired
	public DeviceDao dao;
	
	public Integer add(String name, String label, int count, int power, String ip)
	{
		if(label == null) label = "";
		if(ip == null) ip = "";
		
		if(count < 0) count = 0;
		if(power < 0) power = 0;
		if(hasName(name)) return null;
		
		Device device = new Device(0, name, label, count, power, ip, null);
		device.update_at = SqlTool.currentTimestamp();
		
		return (int) dao.insert(device);
	}
	
	public boolean delete(int device_id)
	{
		Device device = new Device();
		device.device_id  = device_id;
		
		int row = dao.delete(device);
		return row == 1;
	}
	public Boolean update(int device_id, String name, String label, Integer count, Integer power, String ip)
	{
		Device device = this.query(device_id);
		if(device == null) return null;
		if(!device.name.equals(name) && hasName(name)) return false;
		
		if(name != null) device.name = name;
		if(label != null) device.label = label;
		if(count != null) device.count = count<0 ? 0 : count;
		if(power != null) device.power = power<0 ? 0 : power;
		if(ip != null) device.ip = ip;
		device.update_at = SqlTool.currentTimestamp();
		
		int row = dao.update(device);
		return row == 1;
	}
	
	public Device query(int device_id)
	{
		Device device = new Device();
		device.device_id = device_id;
		
		List<Device> devices = dao.query(device);
		if(devices.size() == 0) return null;
		
		return devices.get(0);
	}
	public Device query(String name)
	{
		Device device = new Device();
		device.name = name;
		
		List<Device> devices = dao.query(device, null, "name");
		if(devices.size() == 0) return null;
		
		return devices.get(0);
	}
	public List<Device> list()
	{
		return dao.list();
	}
	
	public boolean hasName(String name)
	{
		Device t = new Device();
		t.name = name;
		return dao.count(t, "name") != 0;
	}
}
