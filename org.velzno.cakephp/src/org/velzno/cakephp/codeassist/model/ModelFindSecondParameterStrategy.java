package org.velzno.cakephp.codeassist.model;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.AbstractCompletionStrategy;

public class ModelFindSecondParameterStrategy extends AbstractCompletionStrategy implements ICompletionStrategy {
	public ModelFindSecondParameterStrategy(ICompletionContext context) {
		super(context);
	}

	@Override
	public void apply(ICompletionReporter reporter) throws Exception {
		ModelFindSecondParameterContext context = (ModelFindSecondParameterContext) this.getContext();
		Matcher m = Pattern.compile("[\"\'](\\w+)$").matcher(context.getStatementText().toString());
		String param = m.find() ? m.group(1) : "";
		for(String c : new String[]{"conditions","recursive","fields","order","group","limit","page","offset","callbacks"}){
			if(!c.startsWith(param)) continue;
			reporter.reportKeyword(c, "", this.getReplacementRange(this.getContext()));
		}
	}

}
