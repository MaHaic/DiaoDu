package cc.c3q.algo.evolve.fms.web.svc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.c3q.algo.evolve.fms.web.bean.Product;
import cc.c3q.algo.evolve.fms.web.bean.Product.Operation;
import cc.c3q.algo.evolve.fms.web.dao.ProductDao;
import cc.c3q.mysql.sqlControl.SqlTool;

@Service
public class ProductSvc
{
	@Autowired
	public ProductDao dao;
	
	public Integer add(String name, Operation[][] process)
	{
		if(hasName(name)) return null;
		
		Product product = new Product(0, name, process, null);
		product.update_at = SqlTool.currentTimestamp();
		
		return (int) dao.insert(product);
	}
	
	public boolean delete(int product_id)
	{
		Product product = new Product();
		product.product_id  = product_id;
		
		int row = dao.delete(product);
		return row == 1;
	}
	public Boolean update(int product_id, String name, Operation[][] process)
	{
		Product product = this.query(product_id);
		if(product == null) return null;
		if(!product.name.equals(name) && hasName(name)) return false;
		
		if(name != null) product.name = name;
		if(process != null) product.process(process);
		product.update_at = SqlTool.currentTimestamp();
		
		int row = dao.update(product);
		return row == 1;
	}
	
	public Product query(int product_id)
	{
		Product product = new Product();
		product.product_id = product_id;
		
		List<Product> products = dao.query(product);
		if(products.size() == 0) return null;
		
		return products.get(0);
	}
	public Product query(String name)
	{
		Product product = new Product();
		product.name = name;
		
		List<Product> products = dao.query(product, null, "name");
		if(products.size() == 0) return null;
		
		return products.get(0);
	}
	public List<Product> list()
	{
		return dao.list();
	}
	
	public boolean hasName(String name)
	{
		Product t = new Product();
		t.name = name;
		return dao.count(t, "name") != 0;
	}
}
