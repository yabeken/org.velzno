package org.velzno.cakephp.codeassist.classregistry;

import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocMethodReturnTypeGoal;
import org.velzno.codeassist.PHPClassTypeGoalEvaluator;

public class ClassRegistryGoalEvaluatorFactory implements IGoalEvaluatorFactory{
	@Override
	public GoalEvaluator createEvaluator(IGoal goal) {
		if(goal instanceof PHPDocMethodReturnTypeGoal){
			String className = ClassRegistryInitContext.getClassName();
			if(className != null){
				return new PHPClassTypeGoalEvaluator(goal, className);
			}
		}
		return null;
	}

}
