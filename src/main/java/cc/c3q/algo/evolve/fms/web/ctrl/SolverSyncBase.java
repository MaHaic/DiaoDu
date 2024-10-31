package cc.c3q.algo.evolve.fms.web.ctrl;

import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import org.jgap.IChromosome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import cc.c3q.algo.evolve.fms.bean.FMSCalcResult;
import cc.c3q.algo.evolve.fms.svc.BgCalcManager;
import cc.c3q.algo.evolve.fms.svc.FMSGeneTool;
import cc.c3q.algo.evolve.fms.svc.FMSJsonConfig;
import cc.c3q.algo.evolve.fms.web.bean.Job;
import cc.c3q.algo.evolve.fms.web.svc.JobSvc;

public abstract class SolverSyncBase
{
	@Autowired
	public JobSvc job;
	public BgCalcManager bgCalc;
	public SolverSyncBase()
	{
		bgCalc = BgCalcManager.getInstance();
	}
	
	@GetMapping("/")
	public Object submitByJid(HttpServletResponse response, @RequestParam int job_id)
	{
		Job t = job.query(job_id);
		if(t == null) {
			response.setStatus(404);
			return null;
		}
		return submit(response, t.demand, job_id);
	}
	
	@PostMapping("/")
	public Object submit(HttpServletResponse response, @RequestBody String jsonText, Integer job_id)
	{
		FMSGeneTool geneTool = FMSJsonConfig.readConfig(jsonText);
		QueueBuffer buffer = generateBuffer(geneTool);
		buffer.job_id = job_id==null ? -1 : job_id;
		
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("odc", geneTool.getPartCount());
		m.put("opc", geneTool.getProcessCount());
		m.put("index", bgCalc.put(buffer));
		return m;
	}
	@PostMapping("/{index}/")
	public Object getState(HttpServletResponse response, @PathVariable int index, @RequestBody int start)
	{
		QueueBuffer buffer = (QueueBuffer) bgCalc.get(index);
		if(buffer == null) {
			response.setStatus(404);
			return null;
		} else {
			if(start < 0) start = 0;
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("state", buffer.calcState);
			m.put("list", buffer.result.getTimeFitness(start));
			return m;
		}
	}
	@GetMapping("/{index}/")
	public Object getResult(HttpServletResponse response, @PathVariable int index)
	{
		QueueBuffer buffer = (QueueBuffer) bgCalc.get(index);
		if(buffer == null) {
			response.setStatus(404);
			return null;
		} else if(buffer.calcState != 2) {
			response.setStatus(403);
			return null;
		} else {
			bgCalc.remove(index);
			IChromosome fittest = buffer.result.data.get(buffer.result.data.size()-1).fittest;
			if(buffer.job_id >= 0) {
				job.update(buffer.job_id, null, null, JobSvc.toWork(fittest.getGenes()), 0);
			}
			return SolverCtrl.view(buffer.getGeneTool(), fittest);
		}
	}
	
	protected abstract QueueBuffer generateBuffer(FMSGeneTool geneTool);
	
	public static abstract class QueueBuffer extends BgCalcManager.QueueBuffer
	{
		public final FMSCalcResult result;
		
		public int job_id = -1;
		public volatile int calcState = 0;
		public QueueBuffer()
		{
			Vector<FMSCalcResult.DataUnit> result = new Vector<FMSCalcResult.DataUnit>(512);
			this.result = new FMSCalcResult(0, -1, 0, result);
		}
		@Override
		protected void run(Object[] cache)
		{
			this.calcState = 1;
			calc();
			getGeneTool().threadManager.destroy();
			this.calcState = 2;
		}
		
		protected abstract void calc();
		public abstract FMSGeneTool getGeneTool();
	}
}
