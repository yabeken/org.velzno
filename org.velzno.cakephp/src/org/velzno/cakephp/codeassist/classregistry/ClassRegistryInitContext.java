package org.velzno.cakephp.codeassist.classregistry;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext;
/**
 * comple ClassRegistry::init()
 * 1. ClassRegistry::init('Model')->method()
 */
public class ClassRegistryInitContext extends ClassMemberContext{
	private static String className;
	public boolean isValid(ISourceModule sourceModule, int offset, CompletionRequestor requestor){
		try {
			className = null;
			String source;
			source = sourceModule.getSource().substring(0, offset);
			source = source.substring(source.lastIndexOf("ClassRegistry::init"), source.lastIndexOf(")")) + ")";

			//ClassRegistry::init('Hoge');
			Matcher m = Pattern.compile("ClassRegistry::init\\s*\\(\\s*([\"\'])([A-Z]\\w*).?\\1\\)").matcher(source);
			if(m.find()) className = m.group(2);
		} catch (Exception e) {
		}
		return false;
	}
	public static String getClassName(){
		return className;
	}
}
