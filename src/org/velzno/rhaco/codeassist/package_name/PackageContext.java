package org.velzno.rhaco.codeassist.package_name;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.codeassist.contexts.QuotesContext;

public class PackageContext extends QuotesContext{
	public boolean isValid(ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) return false;
		String statementText = this.getStatementText().toString();
		Matcher m = Pattern.compile("^(?:(?:Lib::)?import|[Rr]|(?:Object::)?[Cc])\\s*\\(\\s*[\"\'][a-zA-Z0-9-_\\.]*").matcher(statementText);
		return m.find();
	}
}
