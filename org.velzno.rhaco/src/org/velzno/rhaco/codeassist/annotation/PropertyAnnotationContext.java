package org.velzno.rhaco.codeassist.annotation;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.php.internal.core.codeassist.contexts.PHPDocTagContext;

public class PropertyAnnotationContext extends PHPDocTagContext{
	public boolean isValid(ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if(!super.isValid(sourceModule, offset, requestor)) return false;
		if(!Pattern.compile("@var\\s+.*$").matcher(this.getStatementText().toString()).find()) return false;
		try {
			return !this.getClassName().equals("");
		} catch (Exception e) {
		}
		return false;
	}
	private String getClassName() throws Exception{
		String sourceCode = this.getSourceModule().getSource().substring(this.getOffset());
		Matcher m = Pattern.compile("(?is)\\*/.*?class\\s*?(\\w+)").matcher(sourceCode);
		return m.find() ? m.group(1) : "";
	}
	public IType getClassType() throws Exception{
		String className = this.getClassName();
		for(IType type : this.getSourceModule().getAllTypes()){
			if(type.getElementName().equals(className)) return type;
		}
		return null;
	}
}
