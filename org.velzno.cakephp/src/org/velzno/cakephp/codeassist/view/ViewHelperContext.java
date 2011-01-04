package org.velzno.cakephp.codeassist.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext;

/**
 * complete helper variable
 * 1. $this->Helper
 */
public class ViewHelperContext extends ClassMemberContext{
	public boolean isValid(ISourceModule sourceModule, int offset, CompletionRequestor requestor){
		if(!super.isValid(sourceModule, offset, requestor)) return false;
		if(this.getTriggerType() == Trigger.OBJECT && sourceModule.getElementName().endsWith(".ctp")){
			String statementText = this.getStatementText().toString();
			Matcher m_helper = Pattern.compile("^\\$this->(\\w*)$").matcher(statementText);
			return m_helper.find();
		}
		return false;
	}
}
