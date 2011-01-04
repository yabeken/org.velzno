package org.velzno.cakephp.codeassist.controller;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.internal.core.SourceModule;
import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.typeinference.context.TypeContext;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocClassVariableGoal;
import org.velzno.codeassist.PHPClassTypeGoalEvaluator;

@SuppressWarnings("restriction")
public class ControllerMemberGoalEvaluatorFactory implements IGoalEvaluatorFactory {

	@Override
	public GoalEvaluator createEvaluator(IGoal goal) {
		if(goal instanceof PHPDocClassVariableGoal){
			String variableName = ((PHPDocClassVariableGoal) goal).getVariableName().substring(1);
			try {
				for(IScriptFolder folder : ((TypeContext)goal.getContext()).getSourceModule().getScriptProject().getScriptFolders()){
					String folderPath = folder.getElementName().toString();
					if(!folderPath.endsWith("components") && !folderPath.endsWith("models")) continue;
					for(IModelElement source : folder.getChildren()){
						if(source instanceof SourceModule){
							if(!source.getPath().toString().endsWith("/" + variableName.toLowerCase() + ".php")) continue;
							try{
								String className = ((SourceModule) source).getTypes()[0].getElementName();
								if(className.equals(variableName) || className.equals(variableName + "Component")){
									return new PHPClassTypeGoalEvaluator(goal, className);
								}
							}catch (Exception e) {
							}
						}
					}
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

}
