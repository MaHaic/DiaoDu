package cc.c3q.algo.evolve.fms.web.ctrl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cc.c3q.algo.evolve.fms.ctrl.ClassicScheduling;
import cc.c3q.algo.evolve.fms.ctrl.DynamicScheduling;
import cc.c3q.algo.evolve.fms.svc.FMSGeneTool;
import cc.c3q.algo.evolve.fms.svc.FMSJsonConfig;
import cc.c3q.algo.evolve.fms.web.bean.Job;
import cc.c3q.algo.evolve.fms.web.svc.JobSvc;

@RestController
@RequestMapping("/api/solver")
public class SolverCtrl
{
	@Autowired
	public JobSvc job;
	
	@GetMapping("/snapshot/{job_id}/{time}/")
	public Object jobSnapshot(HttpServletResponse response, @PathVariable int job_id, @PathVariable long time) throws NumberFormatException, InvalidConfigurationException
	{
		Job t = job.query(job_id);
		if(t == null) {
			response.setStatus(404);
			return null;
		}
		Configuration.reset();
		Configuration conf = new Configuration();
		FMSGeneTool geneTool = FMSJsonConfig.readConfig(t.demand);
		Chromosome chromosome = new Chromosome(conf, JobSvc.toGenes(conf, t.work));
		
		DynamicScheduling dynamic = new DynamicScheduling(geneTool);
		String shot =  dynamic.snapshot(chromosome, time);
		geneTool.threadManager.destroy();
		
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("odc", geneTool.getPartCount());
		m.put("opc", geneTool.getProcessCount());
		m.put("tuse", null);
		m.put("roffset", geneTool.offset);
		m.put("shot", shot);
		return m;
	}
	
	@GetMapping("/view/{job_id}/")
	public Object getResult(HttpServletResponse response, @PathVariable int job_id) throws NumberFormatException, InvalidConfigurationException
	{
		Job t = job.query(job_id);
		if(t == null) {
			response.setStatus(404);
			return null;
		}
		Configuration.reset();
		Configuration conf = new Configuration();
		FMSGeneTool geneTool = FMSJsonConfig.readConfig(t.demand);
		Chromosome chromosome = new Chromosome(conf, JobSvc.toGenes(conf, t.work));
		
		geneTool.threadManager.destroy();
		return view(geneTool, chromosome);
	}
	
	@GetMapping("/fifo/")
	public Object fifoMapping(HttpServletResponse response, @RequestParam int job_id) throws IOException
	{
		Job t = job.query(job_id);
		if(t == null) {
			response.setStatus(404);
			return null;
		}
		return fifoMapping(response, t.demand, job_id);
	}
	@GetMapping("/edf/")
	public Object edfMapping(HttpServletResponse response, @RequestParam int job_id) throws IOException
	{
		Job t = job.query(job_id);
		if(t == null) {
			response.setStatus(404);
			return null;
		}
		return edfMapping(response, t.demand, job_id);
	}
	
	@PostMapping("/fifo/")
	public Object fifoMapping(HttpServletResponse response, @RequestBody String jsonText, Integer job_id) throws IOException
	{
		FMSGeneTool geneTool = FMSJsonConfig.readConfig(jsonText);
		System.out.println("订单总数："+geneTool.getPartCount());
		System.out.println("工序总数："+geneTool.getProcessCount());
		
		ClassicScheduling fs = new ClassicScheduling(geneTool);
		IChromosome chromosome = fs.FIFO();
		if(job_id != null) {
			job.update(job_id, null, null, JobSvc.toWork(chromosome.getGenes()), 0);
		}
		geneTool.threadManager.destroy();
		return view(geneTool, chromosome);
	}
	@PostMapping("/edf/")
	public Object edfMapping(HttpServletResponse response, @RequestBody String jsonText, Integer job_id) throws IOException
	{
		FMSGeneTool geneTool = FMSJsonConfig.readConfig(jsonText);
		System.out.println("订单总数："+geneTool.getPartCount());
		System.out.println("工序总数："+geneTool.getProcessCount());
		
		ClassicScheduling fs = new ClassicScheduling(geneTool);
		IChromosome chromosome = fs.EDF();
		if(job_id != null) {
			job.update(job_id, null, null, JobSvc.toWork(chromosome.getGenes()), 0);
		}
		geneTool.threadManager.destroy();
		return view(geneTool, chromosome);
	}
	
	public static Map<String, Object> view(FMSGeneTool geneTool, IChromosome fittest)
	{
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("odc", geneTool.getPartCount());
		m.put("opc", geneTool.getProcessCount());
		m.put("tuse", geneTool.getTotalTime2(fittest));
		m.put("roffset", geneTool.offset);
		m.put("list", geneTool.getTasktable(fittest));
		
		return m;
	}
}
