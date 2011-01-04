package org.velzno.cakephp.codeassist.app;

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
public class AppImportSecondArgumentStrategy extends AbstractCompletionStrategy implements ICompletionStrategy {

	public AppImportSecondArgumentStrategy(ICompletionContext context) {
		super(context);
	}

	@Override
	public void apply(ICompletionReporter reporter) throws Exception {
		AppImportSecondArgumentContext context = (AppImportSecondArgumentContext) this.getContext();
		String statementText = context.getStatementText().toString();
		Matcher m = Pattern.compile("([\"\'])(Core|Controller|Model|Component|Behavior|Helper|Vendor)\\1\\s*,\\s*[\"\']([\\w\\.]*)$").matcher(statementText);
		String param = "";
		String importType = "";
		if(m.find()){
			param = m.group(3) == null ?  "" : m.group(3);
			importType = m.group(2);
		}
		for(IScriptFolder folder : context.getSourceModule().getScriptProject().getScriptFolders()){
			//TODO cake core configuration
			String folderPath = folder.getElementName();
			if(importType.equals("Core") && !folderPath.endsWith("cake/libs")) continue;
			if(importType.equals("Controller") && !folderPath.endsWith("controllers")) continue;
			if(importType.equals("Model") && !folderPath.endsWith("models")) continue;
			if(importType.equals("Component") && !folderPath.endsWith("components")) continue;
			if(importType.equals("Behavior") && !folderPath.endsWith("behaviors")) continue;
			if(importType.equals("Helper") && !folderPath.endsWith("helpers")) continue;
			if(importType.equals("Vendor") && !folderPath.contains("vendors")) continue;
			
			for(IModelElement source : folder.getChildren()){
				if(source instanceof SourceModule){
					String sourcePath = source.getElementName();
					if(!sourcePath.endsWith(".php")) continue;
					try{
						for(IType classType : ((SourceModule) source).getTypes()){
							String className = classType.getElementName();
							if(className.contains(importType)) className = className.substring(0, className.lastIndexOf(importType));
							if(className.toLowerCase().startsWith(param.toLowerCase())){
								reporter.reportKeyword(className, "", this.getReplacementRange(context));
							}
						}
					}catch(Exception e){
					}
				}
			}
		}
		
	}


}
