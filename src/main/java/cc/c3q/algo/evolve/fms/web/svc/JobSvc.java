package cc.c3q.algo.evolve.fms.web.svc;

import java.util.List;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.c3q.algo.evolve.fms.genesvc.FixedArrayShortGene;
import cc.c3q.algo.evolve.fms.genesvc.FixedShortGeneImpl;
import cc.c3q.algo.evolve.fms.web.bean.Job;
import cc.c3q.algo.evolve.fms.web.dao.JobDao;
import cc.c3q.mysql.sqlControl.SqlTool;

@Service
public class JobSvc
{
	@Autowired
	public JobDao dao;
	
	public Integer add(String name, String demand)
	{
		if(hasName(name)) return null;
		
		Job job = new Job(0, name, demand, "", -1, null);
		job.update_at = SqlTool.currentTimestamp();
		
		return (int) dao.insert(job);
	}
	public boolean delete(int job_id)
	{
		Job job = new Job();
		job.job_id  = job_id;
		
		int row = dao.delete(job);
		return row == 1;
	}
	
	public Boolean update(int job_id, String name, String demand, String work, Integer state)
	{
		Job job = this.query(job_id);
		if(job == null) return null;
		if(!job.name.equals(name) && hasName(name)) return false;
		
		if(name != null) job.name = name;
		if(demand != null) job.demand = demand;
		if(work != null) job.work = work;
		if(state != null) job.state = state;
		job.update_at = SqlTool.currentTimestamp();
		
		int row = dao.update(job);
		return row == 1;
	}
	
	public Job query(int job_id)
	{
		Job job = new Job();
		job.job_id = job_id;
		
		List<Job> jobs = dao.query(job);
		if(jobs.size() == 0) return null;
		
		return jobs.get(0);
	}
	public Job query(String name)
	{
		Job job = new Job();
		job.name = name;
		
		List<Job> jobs = dao.query(job, null, "name");
		if(jobs.size() == 0) return null;
		
		return jobs.get(0);
	}
	public List<Job> list()
	{
		return dao.list();
	}
	
	public boolean hasName(String name)
	{
		Job t = new Job();
		t.name = name;
		return dao.count(t, "name") != 0;
	}
	
	public static String toWork(Gene[] genes)
	{
		StringBuilder sb = new StringBuilder();
		for(Gene gene : genes)
		{
			short[] arr = ((FixedShortGeneImpl)gene).mappingAllele();
			for(short k : arr) sb.append(k).append(' ');
			sb.setLength(sb.length()-1);
			sb.append('\n');
		}
		sb.setLength(sb.length()-1);
		return sb.toString();
	}
	public static Gene[] toGenes(Configuration conf, String work) throws NumberFormatException, InvalidConfigurationException
	{
		String[] lines = work.trim().split("\n");
		Gene[] genes = new Gene[lines.length];
		for(int i=0; i<genes.length; i++)
		{
			String[] nums = lines[i].trim().split(" ");
			FixedArrayShortGene gene = new FixedArrayShortGene(conf, nums.length);
			short[] arr = gene.mappingAllele();
			for(int j=0; j<arr.length; j++) arr[j] = Short.parseShort(nums[j]);
			genes[i] = gene;
		}
		return genes;
	}
}
