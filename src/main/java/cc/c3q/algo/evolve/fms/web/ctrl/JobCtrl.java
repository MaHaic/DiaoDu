package cc.c3q.algo.evolve.fms.web.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cc.c3q.algo.evolve.fms.web.bean.Job;
import cc.c3q.algo.evolve.fms.web.svc.JobSvc;

@RestController
@RequestMapping("/api/job")
public class JobCtrl
{
	@Autowired
	public JobSvc svc;
	
	@PostMapping("/add/")
	public BaseRet add(@RequestParam String name, String demand)
	{
		Integer id = svc.add(name, demand);
		if(id == null) return BaseRet.err("创建失败，名称已被占用");
		
		return BaseRet.ok(id);
	}
	
	@GetMapping("/list/")
	public BaseRet list()
	{
		List<Job> t = svc.list();
		return BaseRet.ok(t);
	}
	@GetMapping("/name/{name}/")
	public BaseRet query(@PathVariable String name)
	{
		Job t = svc.query(name);
		if(t == null) return BaseRet.err("未找到");
		
		return BaseRet.ok(t);
	}
	@GetMapping("/{id}/")
	public BaseRet query(@PathVariable int id)
	{
		Job t = svc.query(id);
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
	public BaseRet update(@PathVariable int id, String name, String demand, String work, Integer state)
	{
		Boolean r = svc.update(id, name, demand, work, state);
		if(r == null) return BaseRet.err("操作失败，指定ID不存在");
		if(r == false) return BaseRet.err("操作失败，名称已被占用");
		
		return BaseRet.ok();
	}
}
