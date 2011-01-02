package org.velzno.rhaco.codeassist.function;

import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocMethodReturnTypeGoal;
import org.velzno.rhaco.codeassist.PHPClassTypeGoalEvaluator;

public class FunctionGoalEvaluatorFactory implements IGoalEvaluatorFactory {
	@Override
	public GoalEvaluator createEvaluator(IGoal goal) {
		if(goal instanceof PHPDocMethodReturnTypeGoal){
			if(((PHPDocMethodReturnTypeGoal) goal).getMethodName().toLowerCase().equals("r")
					|| ((PHPDocMethodReturnTypeGoal) goal).getMethodName().toLowerCase().equals("c")){
				if(FunctionContext.getClassName() != null){
					return new PHPClassTypeGoalEvaluator(goal, FunctionContext.getClassName());
				}
			}
		}
		return null;
	}
}
