package cc.c3q.algo.evolve.fms.web.dao;

import org.springframework.stereotype.Repository;
import cc.c3q.algo.evolve.fms.web.bean.Job;

@Repository
public class JobDao extends BaseDao<Job>
{
	public JobDao()
	{
		super(Job.class, "a_fms_job", "job_id");
	}
}
