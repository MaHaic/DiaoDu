package cc.c3q.algo.evolve.fms.web.bean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import cc.c3q.algo.evolve.fms.tool                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .Tool;
import cc.c3q.json.jackson.JsonFormatException;

/*
create table if not exists a_fms_product(
		product_id int primary key auto_increment,
		name nvarchar(127) not null,
		process text not null,
		update_at timestamp not null default CURRENT_TIMESTAMP,
		index(name)
	) character set=utf8;
*/
public class Product
{
	public int product_id;
	public String name;
	public String process;
	public Timestamp update_at;
	
	public Product() {super();}
	public Product(int product_id, String name, Operation[][] process, Timestamp update_at) {
		super();
		this.product_id = product_id;
		this.name = name;
		this.process = strProcess(process);
		this.update_at = update_at;
	}
	
	public Operation[][] process() throws JsonFormatException
	{
		return generateProcess(process);
	}
	public void process(Operation[][] process)
	{
		this.process = strProcess(process);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + product_id;
		result = prime * result + ((process == null) ? 0 : process.hashCode());
		result = prime * result + ((update_at == null) ? 0 : update_at.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		Product other = (Product) obj;
		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (product_id != other.product_id) return false;
		if (process == null) {
			if (other.process != null) return false;
		} else if (!process.equals(other.process)) {
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
		return "Product [product_id=" + product_id + ", name=" + name + ", process=" + process + ", update_at=" + update_at + "]";
	}
	
	public static Operation[][] generateProcess(String str) throws JsonFormatException, NumberFormatException
	{
		if(str == null) return new Operation[0][];
		
		JsonNode arr = Tool.toNode(str);
		Operation[][] ret = new Operation[arr.size()][];
		for(int i=0; i<ret.length; i++)
		{
			JsonNode obj = arr.required(i);
			Operation[] ops = new Operation[obj.size()];
			int k = 0;
			
			Iterator<Map.Entry<String, JsonNode>> fields = obj.fields();
			while(fields.hasNext())
			{
				Map.Entry<String, JsonNode> l = fields.next();
				String device = l.getKey();
				Long time = l.getValue().asLong();
				
				ops[k++] = new Operation(device, time);
			}
			ret[i] = ops;
		}
		return ret;
	}
	public static String strProcess(Operation[][] process)
	{
		List<Map<String,Long>> data = new ArrayList<Map<String,Long>>(process.length);
		for(Operation[] ops : process)
		{
			HashMap<String,Long> obj = new HashMap<String,Long>();
			for(Operation op : ops) obj.put(op.device, op.time);
				
			data.add(obj);
		}
		return Tool.jf.toJson(data);
	}
	
	public static class Operation
	{
		public String device;
		public long time;
		public Operation() {super();}
		public Operation(String device, long time){
			super();
			this.device = device;
			this.time = time;
		}
		@Override
		public String toString() {
			return "Operation [device=" + device + ", time=" + time + "]";
		}
	}
}
