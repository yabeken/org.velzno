package org.velzno.codeassist;

import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocMethodReturnTypeGoal;

public class FunctionGoalEvaluatorFactory implements IGoalEvaluatorFactory {
	@Override
	public GoalEvaluator createEvaluator(IGoal goal) {
		if(goal instanceof PHPDocMethodReturnTypeGoal){
			if(((PHPDocMethodReturnTypeGoal) goal).getMethodName().equals("R") || ((PHPDocMethodReturnTypeGoal) goal).getMethodName().equals("C")){
				if(FunctionCompletionContext.getClassName().length() != 0){
					return new FunctionGoalEvaluator(goal, FunctionCompletionContext.getClassName());
				}
			}
		}
		return null;
	}
}
