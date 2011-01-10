package org.velzno.rhaco.codeassist.annotation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;

public class PropertyNameContext extends PropertyAnnotationContext{
	public boolean isValid(ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if(!super.isValid(sourceModule, offset, requestor)) return false;
		String statementText = this.getStatementText().toString();
		Matcher m = Pattern.compile("@var\\s+\\w+\\s+(?:\\$\\w*?)?$").matcher(statementText);
		return m.find();
	}
}
