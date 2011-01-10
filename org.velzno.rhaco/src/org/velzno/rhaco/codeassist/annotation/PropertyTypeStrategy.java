package org.velzno.rhaco.codeassist.annotation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.PHPDocTagStrategy;

@SuppressWarnings("restriction")
public class PropertyTypeStrategy extends PHPDocTagStrategy implements ICompletionStrategy{
	public PropertyTypeStrategy(ICompletionContext context) {
		super(context);
	}
	public void apply(ICompletionReporter reporter) {
		PropertyTypeContext context = (PropertyTypeContext) this.getContext();
		String statementText = context.getStatementText().toString();
		Matcher m = Pattern.compile("@var\\s+(.*?)$").matcher(statementText);
		if(!m.find()) return;
		String param = m.group(1);
		for(String c : new String[]{"string","text","number","serial","integer","boolean","timestamp","date","time","intdate","email","alnum","choice","mixed"}){
			if(c.startsWith(param.toLowerCase())){
				reporter.reportKeyword(c, " ", new SourceRange(context.getOffset() - param.length(), param.length()));
			}
		}
	}
}
