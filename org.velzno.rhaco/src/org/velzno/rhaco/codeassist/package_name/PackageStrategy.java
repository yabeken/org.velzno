package org.velzno.rhaco.codeassist.package_name;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.internal.core.SourceModule;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.AbstractCompletionStrategy;

@SuppressWarnings("restriction")
public class PackageStrategy extends AbstractCompletionStrategy implements ICompletionStrategy {
	public PackageStrategy(ICompletionContext context) {
		super(context);
	}

	@Override
	public void apply(ICompletionReporter reporter) throws Exception {
		PackageContext context = (PackageContext) this.getContext();
		String statementText = context.getStatementText().toString();
		String param = "";
		Matcher m = Pattern.compile("(?:(?:Lib::)?import|[Rr]|(?:Object::)?[Cc])\\s*\\(\\s*[\"\']([a-zA-Z0-9-_\\.]*)").matcher(statementText);
		if(m.find()){
			param = m.group(1) == null ? "" : m.group(1);
		}
		for(IScriptFolder folder : context.getSourceModule().getScriptProject().getScriptFolders()){
			String path = folder.getPath().toString();
			if(path.contains("/vendors")){
				path = path.substring(path.indexOf("/vendors") + "/vendors".length());
			}else if(path.contains("/libs")){
				path = path.substring(path.indexOf("/libs") + "/libs".length());
			}else{
				continue;
			}
			path = path.replace("/", ".");
			if(path.startsWith(".")) path = path.substring(1);
			if(Pattern.compile("\\.[A-Z]¥¥w*\\.").matcher(path).find()) continue;
			if(Pattern.compile("\\.[A-Z]\\w*?$").matcher(path).find()){
				if(path.startsWith(param) && !path.equals(param)){
					reporter.reportKeyword(path, "", new SourceRange(context.getOffset() - param.length(), path.length()));
				}
				continue;
			}
			for(IModelElement source : folder.getChildren()){
				if(source instanceof SourceModule){
					if(!source.getPath().getFileExtension().equals("php")) continue;
					String filename = source.getElementName();
					if(!"ABCDEFGHIJKLMNOPQRSTUVWXYZ".contains(filename.substring(0, 1))) continue;
					filename = filename.substring(0, filename.indexOf('.'));
					String filepath = path.length() == 0 ? filename : path + "." + filename;
					if(filepath.startsWith(param) && !filepath.equals(param)){
						reporter.reportKeyword(filepath, "", new SourceRange(context.getOffset() - param.length(), path.length()));
					}
				}
			}
		}
	}
}
