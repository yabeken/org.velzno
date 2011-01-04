package org.velzno.cakephp.codeassist.view;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.codeassist.contexts.StatementContext;

/**
 * complete helper variable
 * 
 * 1. $helper
 * 2. $this->Helper
 */
public class ViewStatementContext extends StatementContext{
	public boolean isValid(ISourceModule sourceModule, int offset, CompletionRequestor requestor){
		if(!super.isValid(sourceModule, offset, requestor)) return false;
		if(!this.getSourceModule().getElementName().endsWith(".ctp")) return false;
		String statementText = this.getStatementText().toString();
		if(statementText.equals("") || statementText.matches("^\\$[a-z0-9_]*$")){
			return true;
		}
		return false;
	}
}
