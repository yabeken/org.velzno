package org.velzno.cakephp.codeassist;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.core.codeassist.ICompletionStrategyFactory;
import org.velzno.cakephp.codeassist.app.AppImportFirstArgumentContext;
import org.velzno.cakephp.codeassist.app.AppImportFirstArgumentStrategy;
import org.velzno.cakephp.codeassist.app.AppImportSecondArgumentContext;
import org.velzno.cakephp.codeassist.app.AppImportSecondArgumentStrategy;
import org.velzno.cakephp.codeassist.classregistry.ClassRegistryInitArgumentContext;
import org.velzno.cakephp.codeassist.classregistry.ClassRegistryInitArgumentStrategy;
import org.velzno.cakephp.codeassist.controller.ControllerComponentsContext;
import org.velzno.cakephp.codeassist.controller.ControllerComponentsStrategy;
import org.velzno.cakephp.codeassist.controller.ControllerHelpersContext;
import org.velzno.cakephp.codeassist.controller.ControllerHelpersStrategy;
import org.velzno.cakephp.codeassist.controller.ControllerMemberContext;
import org.velzno.cakephp.codeassist.controller.ControllerMemberStrategy;
import org.velzno.cakephp.codeassist.controller.ControllerUsesContext;
import org.velzno.cakephp.codeassist.controller.ControllerUsesStrategy;
import org.velzno.cakephp.codeassist.model.ModelBehaviorContext;
import org.velzno.cakephp.codeassist.model.ModelBehaviorStrategy;
import org.velzno.cakephp.codeassist.model.ModelFindFirstParameterContext;
import org.velzno.cakephp.codeassist.model.ModelFindFirstParameterStrategy;
import org.velzno.cakephp.codeassist.model.ModelFindSecondParameterContext;
import org.velzno.cakephp.codeassist.model.ModelFindSecondParameterStrategy;
import org.velzno.cakephp.codeassist.view.ViewHelperContext;
import org.velzno.cakephp.codeassist.view.ViewHelperStrategy;
import org.velzno.cakephp.codeassist.view.ViewStatementContext;
import org.velzno.cakephp.codeassist.view.ViewStatementStrategy;

public class CakephpCompletionStrategyFactory implements ICompletionStrategyFactory{
	@Override
	public ICompletionStrategy[] create(ICompletionContext[] contexts) {
		List<ICompletionStrategy> result = new LinkedList<ICompletionStrategy>();
		for(ICompletionContext context : contexts){
			if(context instanceof ControllerMemberContext){
				result.add(new ControllerMemberStrategy(context));
			}else if(context instanceof ControllerComponentsContext){
				result.add(new ControllerComponentsStrategy(context));
			}else if(context instanceof ControllerUsesContext){
				result.add(new ControllerUsesStrategy(context));
			}else if(context instanceof ControllerHelpersContext){
				result.add(new ControllerHelpersStrategy(context));
			}else if(context instanceof ModelFindFirstParameterContext){
				result.add(new ModelFindFirstParameterStrategy(context));
			}else if(context instanceof ModelFindSecondParameterContext){
				result.add(new ModelFindSecondParameterStrategy(context));
			}else if(context instanceof ModelBehaviorContext){
				result.add(new ModelBehaviorStrategy(context));
			}else if(context instanceof ViewHelperContext){
				result.add(new ViewHelperStrategy(context));
			}else if(context instanceof ViewStatementContext){
				result.add(new ViewStatementStrategy(context));
			}else if(context instanceof ClassRegistryInitArgumentContext){
				result.add(new ClassRegistryInitArgumentStrategy(context));
			}else if(context instanceof AppImportFirstArgumentContext){
				result.add(new AppImportFirstArgumentStrategy(context));
			}else if(context instanceof AppImportSecondArgumentContext){
				result.add(new AppImportSecondArgumentStrategy(context));
			}
		}
		return (ICompletionStrategy[]) result.toArray(new ICompletionStrategy[result.size()]);
	}

}
