package org.velzno.codeassist;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
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
	private static Pattern p1 = Pattern.compile("(R|C)\\s*\\(\\s*((?:[\"\'])?)(?:[a-zA-Z][a-zA-Z0-9-_]*\\.)*([a-zA-Z][a-zA-Z0-9_]*?)\\s*\\2\\)\\s*$");
	private static Pattern p2 = Pattern.compile("(R)\\s*\\(\\s*new\\s+([a-zA-Z][a-zA-Z0-9_]*?)(?:\\s*\\(.*?\\))?\\s*\\)\\s*$");
	private static Pattern p3 = Pattern.compile("(R|C)\\s*\\(\\s*(\\$[a-zA-Z][a-zA-Z0-9_]*?)\\s*\\)\\s*$");
	public boolean isValid(ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		try {
			clearNames();
			String source = sourceModule.getSource().substring(0, offset);
			source = source.substring(0, source.lastIndexOf("->"));
			//R(Hoge) R("org.rhaco.Hoge")
			Matcher m1 = p1.matcher(source);
			//R(new Hoge($abc)) R(new Hoge("abc"))
			Matcher m2 = p2.matcher(source);
			//R($hoge)
			Matcher m3 = p3.matcher(source);
			if(m1.find()){
				className = m1.group(3);
				functionName = m1.group(1);
			}else if(m2.find()){
				className = m2.group(2);
				functionName = m2.group(1);
			}else if(m3.find()){
				functionName = m3.group(1);
				IType type = CodeAssistUtils.getVariableType(sourceModule, m3.group(2), getOffset())[0];
				className = type.getElementName().toString();
			}else{
				return false;
			}
			if(super.isValid(sourceModule, offset, requestor) && getTriggerType() == Trigger.OBJECT){
				return true;
			}
		} catch (Exception e) {
			clearNames();
		}
		return false;
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
