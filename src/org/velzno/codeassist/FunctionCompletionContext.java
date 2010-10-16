package org.velzno.codeassist;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext;

/**
 * R/C 関数のコード補完を行うかどうか判定する
 * 
 * FunctionGoalEvaluatorFactory::createEvaluator が実行される前に，
 * 必ず実行されるので，ここでクラス名を確保しておく
 * @author yabeken
 */
public class FunctionCompletionContext extends ClassMemberContext{
	private static String className = "";
	private static String functionName = "";
	public boolean isValid(ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		boolean result = false;
		clearNames();
		try {
			String source = sourceModule.getSource().substring(0, offset);
			source = source.substring(0, source.lastIndexOf("->"));
			Pattern p = Pattern.compile("(R|C)\\(((?:[\"\'])?)(?:[a-zA-Z][a-zA-Z0-9-_]*\\.)*([a-zA-Z][a-zA-Z0-9_]*?)\\2\\)$");
			Matcher m = p.matcher(source.toString());
			if(m.find()){
				className = m.group(3);
				functionName = m.group(1);
			}
		} catch (Exception e) {
		}
		if(super.isValid(sourceModule, offset, requestor) && getTriggerType() == Trigger.OBJECT){
			result = true;
		}
		return result;
	}
	public static String getClassName(){
		return className;
	}
	public static String getFunctionName(){
		return functionName;
	}
	public static void clearNames(){
		className = "";
		functionName = "";
	}
}
