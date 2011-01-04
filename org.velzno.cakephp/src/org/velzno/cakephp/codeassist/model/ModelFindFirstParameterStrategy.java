package org.velzno.cakephp.codeassist.model;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.AbstractCompletionStrategy;

public class ModelFindFirstParameterStrategy extends AbstractCompletionStrategy implements ICompletionStrategy {
	public ModelFindFirstParameterStrategy(ICompletionContext context) {
		super(context);
	}

	@Override
	public void apply(ICompletionReporter reporter) throws Exception {
		ModelFindFirstParameterContext context = (ModelFindFirstParameterContext) this.getContext();
		Matcher m = Pattern.compile("[\"\'](\\w+)$").matcher(context.getStatementText().toString());
		String param = m.find() ? m.group(1) : "";
		for(String c : new String[]{"first","count","all","list","threaded","neighbors"}){
			if(!c.startsWith(param)) continue;
			reporter.reportKeyword(c, "", this.getReplacementRange(this.getContext()));
		}
	}

}
