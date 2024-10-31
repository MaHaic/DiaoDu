package cc.c3q.algo.evolve.fms.web.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cc.c3q.algo.evolve.fms.web.bean.Device;
import cc.c3q.algo.evolve.fms.web.svc.DeviceSvc;

@RestController
@RequestMapping("/api/device")
public class DeviceCtrl
{
	@Autowired
	public DeviceSvc svc;
	
	@PostMapping("/add/")
	public BaseRet add(@RequestParam String name, String label, Integer count, Integer power, String ip)
	{
		if(power == null) power = 0;
		if(count == null) count = 0;
		if(count < 0) return BaseRet.err("创建失败，数量必须为非负整数");
		
		Integer id = svc.add(name, label, count, power, ip);
		if(id == null) return BaseRet.err("创建失败，名称已被占用");
		
		return BaseRet.ok(id);
	}
	
	@GetMapping("/list/")
	public BaseRet list()
	{
		List<Device> t = svc.list();
		return BaseRet.ok(t);
	}
	@GetMapping("/name/{name}/")
	public BaseRet query(@PathVariable String name)
	{
		Device t = svc.query(name);
		if(t == null) return BaseRet.err("未找到");
		
		return BaseRet.ok(t);
	}
	@GetMapping("/{id}/")
	public BaseRet query(@PathVariable int id)
	{
		Device t = svc.query(id);
		if(t == null) return BaseRet.err("未找到");
		
		return BaseRet.ok(t);
	}
	
	@GetMapping("/{id}/delete/")
	public BaseRet delete(@PathVariable int id)
	{
		boolean state = svc.delete(id);
		if(!state) return BaseRet.err("操作失败，指定ID不存在");
		
		return BaseRet.ok();
	}
	
	@PostMapping("/{id}/update/")
	public BaseRet update(@PathVariable int id, String name, String label, Integer count, Integer power, String ip)
	{
		if(count!=null && count < 0) return BaseRet.err("操作失败，数量必须为非负整数");
		if(power!=null && power < 0) return BaseRet.err("操作失败，能耗必须为非负整数");
		
		Boolean state = svc.update(id, name, label, count, power, ip);
		if(state == null) return BaseRet.err("操作失败，指定ID不存在");
		if(state == false) return BaseRet.err("操作失败，名称已被占用");
		
		return BaseRet.ok();
	}
}
