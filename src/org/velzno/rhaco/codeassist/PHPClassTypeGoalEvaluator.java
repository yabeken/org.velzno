package org.velzno.rhaco.codeassist;

import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.typeinference.PHPClassType;

public class PHPClassTypeGoalEvaluator extends GoalEvaluator{
	private String className;
	public PHPClassTypeGoalEvaluator(IGoal goal, String className) {
		super(goal);
		this.className = className;
	}

	@Override
	public IGoal[] init() {
		return null;
	}

	@Override
	public Object produceResult() {
		return new PHPClassType(this.className);
	}

	@Override
	public IGoal[] subGoalDone(IGoal subGoal, Object result, GoalState state) {
		return null;
	}
}
