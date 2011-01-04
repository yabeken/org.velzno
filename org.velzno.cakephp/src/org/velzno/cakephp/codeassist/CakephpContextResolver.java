package org.velzno.cakephp.codeassist;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionContextResolver;
import org.eclipse.php.internal.core.codeassist.contexts.CompletionContextResolver;
import org.velzno.cakephp.codeassist.app.AppImportFirstArgumentContext;
import org.velzno.cakephp.codeassist.app.AppImportSecondArgumentContext;
import org.velzno.cakephp.codeassist.classregistry.ClassRegistryInitArgumentContext;
import org.velzno.cakephp.codeassist.classregistry.ClassRegistryInitContext;
import org.velzno.cakephp.codeassist.controller.ControllerComponentsContext;
import org.velzno.cakephp.codeassist.controller.ControllerHelpersContext;
import org.velzno.cakephp.codeassist.controller.ControllerMemberContext;
import org.velzno.cakephp.codeassist.controller.ControllerUsesContext;
import org.velzno.cakephp.codeassist.model.ModelBehaviorContext;
import org.velzno.cakephp.codeassist.model.ModelFindFirstParameterContext;
import org.velzno.cakephp.codeassist.model.ModelFindSecondParameterContext;
import org.velzno.cakephp.codeassist.view.ViewHelperContext;
import org.velzno.cakephp.codeassist.view.ViewStatementContext;

public class CakephpContextResolver extends CompletionContextResolver implements ICompletionContextResolver{
	public ICompletionContext[] createContexts() {
	    return new ICompletionContext[] {
	    		new ControllerMemberContext(),
	    		new ControllerComponentsContext(),
	    		new ControllerHelpersContext(),
	    		new ControllerUsesContext(),
	    		new ClassRegistryInitContext(),
	    		new ClassRegistryInitArgumentContext(),
	    		new ModelFindFirstParameterContext(),
	    		new ModelFindSecondParameterContext(),
	    		new ModelBehaviorContext(),
	    		new ViewStatementContext(),
	    		new ViewHelperContext(),
	    		new AppImportFirstArgumentContext(),
	    		new AppImportSecondArgumentContext()
	    };
	}
}
