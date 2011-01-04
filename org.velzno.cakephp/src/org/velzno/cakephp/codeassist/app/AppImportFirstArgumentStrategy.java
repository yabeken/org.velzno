package org.velzno.cakephp.codeassist.app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.AbstractCompletionStrategy;

public class AppImportFirstArgumentStrategy extends AbstractCompletionStrategy implements ICompletionStrategy {
	public AppImportFirstArgumentStrategy(ICompletionContext context) {
		super(context);
	}

	@Override
	public void apply(ICompletionReporter reporter) throws Exception {
		AppImportFirstArgumentContext context = (AppImportFirstArgumentContext) this.getContext();
		String statementText = context.getStatementText().toString();
		Matcher m = Pattern.compile("[\"\'](\\w+)$").matcher(statementText);
		String param = m.find() ? m.group(1) : "";
		for(String complete : new String[]{"Core","Controller","Model","Component","Behavior","Helper","Vendor"}){
			if(complete.startsWith(param)){
				reporter.reportKeyword(complete, "", this.getReplacementRange(context));
			}
		}
	}
}
