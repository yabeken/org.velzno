package org.velzno.cakephp.codeassist.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.AbstractCompletionStrategy;
import org.eclipse.php.internal.core.typeinference.FakeField;

@SuppressWarnings("restriction")
public class ControllerMemberStrategy extends AbstractCompletionStrategy  implements ICompletionStrategy {
	public ControllerMemberStrategy(ICompletionContext context) {
		super(context);
	}
	
	@Override
	public void apply(ICompletionReporter reporter) throws Exception {
		ControllerMemberContext context = (ControllerMemberContext) this.getContext();
		String statementText = context.getStatementText().toString();
		String param = statementText.substring(statementText.lastIndexOf("->") + 2);
		String sourceCode = context.getSourceModule().getSource();
		
		Pattern p_className = Pattern.compile("(?ms)([\"\'])([A-Z]\\w*)\\1");
		Matcher m_className;
		
		//$components
		Matcher m_component = Pattern.compile("(?ms)\\svar\\s+\\$components\\s*=\\s*array\\s*\\((.+?)\\)\\s*;").matcher(sourceCode);
		if(m_component.find()){
			m_className = p_className.matcher(m_component.group(1));
			while(m_className.find()){
				if(m_className.group(2).startsWith(param)){
					reporter.reportField(new FakeField((ModelElement) context.getLhsTypes()[0], m_className.group(2), Modifiers.AccPublic), "->", this.getReplacementRange(context), true);
				}
			}
		}
		//$uses
		Matcher m_model = Pattern.compile("(?ms)\\svar\\s+\\$uses\\s*=\\s*array\\s*\\((.+?)\\)\\s*;").matcher(sourceCode);
		if(m_model.find()){
			m_className = p_className.matcher(m_model.group(1));
			while(m_className.find()){
				if(m_className.group(2).startsWith(param)){
					reporter.reportField(new FakeField((ModelElement) context.getLhsTypes()[0], m_className.group(2), Modifiers.AccPublic), "->", this.getReplacementRange(context), true);
				}
			}
		}
		//$this->loadModel('');
		String function = sourceCode.substring(0, context.getOffset());
		function = function.substring(function.lastIndexOf("function"));
		Matcher m_loadModel = Pattern.compile("(?ms)\\$this\\s*->\\s*loadModel\\s*\\(\\s*([\"\'])([A-Z]\\w*)\\1\\s*\\)").matcher(function);
		while(m_loadModel.find()){
			reporter.reportField(new FakeField((ModelElement) context.getLhsTypes()[0], m_loadModel.group(2), Modifiers.AccPublic), "->", this.getReplacementRange(context), true);
		}
	}
}
