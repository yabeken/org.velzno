package org.velzno.rhaco.codeassist.function;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext;

public class FunctionContext extends ClassMemberContext{
	private static String className = "";
	public boolean isValid(ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		try {
			className = null;
			String source = sourceModule.getSource().substring(0, offset);
			source = source.substring(0, source.lastIndexOf("->"));
			//R(Hoge) R("org.rhaco.Hoge")
			Matcher m1 = Pattern.compile("[RrCc]\\s*\\(\\s*((?:[\"\'])?)(?:[a-zA-Z]\\w*\\.)*([a-zA-Z]\\w*?)\\1\\s*\\)\\s*$").matcher(source);
			//R(new Hoge($abc)) R(new Hoge("abc"))
			Matcher m2 = Pattern.compile("[RrCc]\\s*\\(\\s*new\\s+([a-zA-Z][a-zA-Z0-9_]*?)(?:\\s*\\(.*?\\))?\\s*\\)\\s*$").matcher(source);
			//R($hoge)
			Matcher m3 = Pattern.compile("[RrCc]\\s*\\(\\s*(\\$[a-zA-Z]\\w*?)\\s*\\)\\s*$").matcher(source);
			
			if(m1.find()){
				className = m1.group(2);
			}else if(m2.find()){
				className = m2.group(1);
			}else if(m3.find()){
				IType type = CodeAssistUtils.getVariableType(sourceModule, m3.group(1), getOffset())[0];
				className = type.getElementName().toString();
			}
		} catch (Exception e) {
		}
		return false;
	}
	public static String getClassName(){
		return className;
	}
}
