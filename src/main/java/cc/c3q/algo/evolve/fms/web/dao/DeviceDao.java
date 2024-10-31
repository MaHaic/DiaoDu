package cc.c3q.algo.evolve.fms.web.dao;

import org.springframework.stereotype.Repository;

import cc.c3q.algo.evolve.fms.web.bean.Device;

@Repository
public class DeviceDao extends BaseDao<Device>
{
	public DeviceDao()
	{
		super(Device.class, "a_fms_device", "device_id");
	}
}
