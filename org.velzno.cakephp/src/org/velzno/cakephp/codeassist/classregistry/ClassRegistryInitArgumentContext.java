package org.velzno.cakephp.codeassist.classregistry;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.codeassist.contexts.QuotesContext;

public class ClassRegistryInitArgumentContext extends QuotesContext{
	public boolean isValid(ISourceModule sourceModule, int offset, CompletionRequestor requestor){
		if(!super.isValid(sourceModule, offset, requestor)) return false;
		String statementText = this.getStatementText().toString();
		Matcher m = Pattern.compile("ClassRegistry::init\\s*\\(\\s*[\"\']\\w*$").matcher(statementText);
		return m.find();
	}
}
