package org.velzno.cakephp.codeassist.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.internal.core.SourceModule;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.AbstractCompletionStrategy;

@SuppressWarnings("restriction")
public class ControllerUsesStrategy extends AbstractCompletionStrategy  implements ICompletionStrategy {
	public ControllerUsesStrategy(ICompletionContext context) {
		super(context);
	}

	@Override
	public void apply(ICompletionReporter reporter) throws Exception {
		ControllerUsesContext context = (ControllerUsesContext) this.getContext();
		String statementText = context.getStatementText().toString();
		Matcher m = Pattern.compile("[\"\'](\\w+)$").matcher(statementText);
		String param = m.find() ? m.group(1) : "";
		for(IScriptFolder folder : context.getSourceModule().getScriptProject().getScriptFolders()){
			if(!folder.getPath().toString().endsWith("/models")) continue;
			for(IModelElement source : folder.getChildren()){
				if(source instanceof SourceModule){
					String sourcePath = source.getElementName();
					if(!sourcePath.endsWith(".php")) continue;
					if(!sourcePath.replaceAll("_", "").contains(param.toLowerCase())) continue;
					try{
						IType classType = ((SourceModule) source).getTypes()[0];
						String className = classType.getElementName();
						if(className.toLowerCase().startsWith(param.toLowerCase())){
							reporter.reportKeyword(className, "", this.getReplacementRange(context));
						}
					}catch(Exception e){
					}
				}
			}
		}
	}
}
