package cc.c3q.algo.evolve.fms.web.dao;

import org.springframework.stereotype.Repository;

import cc.c3q.algo.evolve.fms.web.bean.Product;

@Repository
public class ProductDao extends BaseDao<Product>
{
	public ProductDao()
	{
		super(Product.class, "a_fms_product", "product_id");
	}
}
