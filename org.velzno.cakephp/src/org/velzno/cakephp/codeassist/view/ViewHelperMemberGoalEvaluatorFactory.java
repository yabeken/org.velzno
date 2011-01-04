package org.velzno.cakephp.codeassist.view;

import org.eclipse.dltk.
core.IModelElement;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.SourceModule;
import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.typeinference.context.FileContext;
import org.eclipse.php.internal.core.typeinference.goals.ClassVariableDeclarationGoal;
import org.eclipse.php.internal.core.typeinference.goals.GlobalVariableReferencesGoal;
import org.velzno.codeassist.PHPClassTypeGoalEvaluator;

/**
 * helper variable class type evaluator factory
 */
@SuppressWarnings("restriction")
public class ViewHelperMemberGoalEvaluatorFactory implements IGoalEvaluatorFactory {
	
	@Override
	public GoalEvaluator createEvaluator(IGoal goal) {
		if(goal instanceof GlobalVariableReferencesGoal){
			FileContext context = (FileContext) ((GlobalVariableReferencesGoal)goal).getContext();
			String variableName = ((GlobalVariableReferencesGoal)goal).getVariableName().substring(1);
			if(context.getSourceModule().getElementName().endsWith(".ctp") && variableName.equals("this")){
				//TODO costumizable
				return new PHPClassTypeGoalEvaluator(goal, "View");
			}else{
				try {
					for(IScriptFolder folder : context.getSourceModule().getScriptProject().getScriptFolders()){
						if(!folder.getElementName().endsWith("/helpers")) continue;
						for(IModelElement source : folder.getChildren()){
							if(source instanceof SourceModule){
								String sourcePath = source.getElementName();
								if(!sourcePath.endsWith(".php")) continue;
								if(!sourcePath.replaceAll("_", "").equals(variableName + ".php")) continue;
								try{
									IType classType = ((SourceModule) source).getTypes()[0];
									String className = classType.getElementName();
									if(className.toLowerCase().equals((variableName + "Helper").toLowerCase())){
										return new PHPClassTypeGoalEvaluator(goal, className);
									}
								}catch(Exception e){
								}
							}
						}
					}
				} catch (ModelException e) {
				}
			}
		}else if(goal instanceof ClassVariableDeclarationGoal){
			ClassVariableDeclarationGoal g = (ClassVariableDeclarationGoal) goal;
			try{
				if(g.getTypes()[0].getElementName().equals("View")){
					return new PHPClassTypeGoalEvaluator(goal, g.getVariableName().substring(1) + "Helper");
				}
			}catch(Exception e){
			}
		}
		return null;
	}
}
