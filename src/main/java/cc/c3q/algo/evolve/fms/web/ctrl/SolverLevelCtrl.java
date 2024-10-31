package cc.c3q.algo.evolve.fms.web.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cc.c3q.algo.evolve.fms.nsga.ctrl.NsgaScheduling1;
import cc.c3q.algo.evolve.fms.nsga.svc.NsgaObjective;
import cc.c3q.algo.evolve.fms.nsga.svc.NsgaObjectiveSingleLevel;
import cc.c3q.algo.evolve.fms.svc.FMSGeneTool;

@RestController
@RequestMapping("/api/solver/sync/level")
public class SolverLevelCtrl extends SolverSyncBase
{
	@Override
	protected SolverSyncBase.QueueBuffer generateBuffer(final FMSGeneTool geneTool)
	{
		return new SolverSyncBase.QueueBuffer()
		{
			@Override
			public FMSGeneTool getGeneTool()
			{
				return geneTool;
			}
			@Override
			protected void calc()
			{
				NsgaObjective objective = new NsgaObjectiveSingleLevel(getGeneTool().partManager, getGeneTool().deviceManager);
				NsgaScheduling1 ss = new NsgaScheduling1(geneTool, objective, null);
				ss.calculateDynamicLevel(1.0d, 3000, 100, result);
			}
		};
	}
}
