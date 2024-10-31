package cc.c3q.algo.evolve.fms.web.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cc.c3q.algo.evolve.fms.web.bean.Product;
import cc.c3q.algo.evolve.fms.web.bean.Product.Operation;
import cc.c3q.algo.evolve.fms.web.svc.ProductSvc;
import cc.c3q.json.jackson.JsonFormatException;

@RestController
@RequestMapping("/api/product")
public class ProductCtrl
{
	@Autowired
	public ProductSvc svc;
	
	@PostMapping("/add/")
	public BaseRet add(@RequestParam String name, @RequestParam String process)
	{
		Operation[][] p = null;
		try {
			p = Product.generateProcess(process);
		} catch (NumberFormatException e) {
			return BaseRet.err("创建失败，数据不合法\n\n"+e.getMessage());
		} catch (JsonFormatException e) {
			return BaseRet.err("创建失败，数据不合法\n\n"+e.getMessage());
		}
		Integer id = svc.add(name, p);
		if(id == null) return BaseRet.err("创建失败，名称已被占用");
		
		return BaseRet.ok(id);
	}
	
	@GetMapping("/list/")
	public BaseRet list()
	{
		List<Product> t = svc.list();
		return BaseRet.ok(t);
	}
	@GetMapping("/name/{name}/")
	public BaseRet query(@PathVariable String name)
	{
		Product t = svc.query(name);
		if(t == null) return BaseRet.err("未找到");
		
		return BaseRet.ok(t);
	}
	@GetMapping("/{id}/")
	public BaseRet query(@PathVariable int id)
	{
		Product t = svc.query(id);
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
	public BaseRet update(@PathVariable int id, String name, String process)
	{
		Operation[][] p = null;
		if(process != null) {
			try {
				p = Product.generateProcess(process);
			} catch (NumberFormatException e) {
				return BaseRet.err("创建失败，数据不合法\n\n"+e.getMessage());
			} catch (JsonFormatException e) {
				return BaseRet.err("创建失败，数据不合法\n\n"+e.getMessage());
			}
		}
		Boolean state = svc.update(id, name, p);
		if(state == null) return BaseRet.err("操作失败，指定ID不存在");
		if(state == false) return BaseRet.err("操作失败，名称已被占用");
		
		return BaseRet.ok();
	}
}
