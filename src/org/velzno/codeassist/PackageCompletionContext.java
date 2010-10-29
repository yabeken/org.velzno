package org.velzno.codeassist;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.php.internal.core.codeassist.contexts.QuotesContext;

@SuppressWarnings("restriction")
public class PackageCompletionContext extends QuotesContext{
	private String functionName;
	private String argument;
	private String rootPath;
	private SourceRange replaceRange;
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
		if (super.isValid(sourceModule, offset, requestor)) {
			Matcher m = Pattern.compile("(import|module|R|C)\\s*\\(\\s*[\"\']([a-zA-Z0-9-_\\.]*)")
						.matcher(getStatementText().toString());
			if(m.find()){
				functionName = m.group(1);
				argument = m.group(2) == null ? "" : m.group(2);
				rootPath = "";
				if(functionName.equals("module")){
					String path = sourceModule.getPath().toString();
					if(path.contains("vendors")){
						path = path.substring(path.indexOf("vendors") + "vendors".length());
					}else if(path.contains("libs")){
						path = path.substring(path.indexOf("libs") + "libs".length());
					}
					Matcher m2 = Pattern.compile("(/[A-Z][a-zA-Z0-9-_]*/)").matcher(path);
					if(m2.find()){
						rootPath = (path.substring(1,path.indexOf(m2.group(1))) + m2.group(1)).replace('/', '.');
					}else{
						return false;
					}
				}
				replaceRange = new SourceRange(getOffset() - argument.length(), argument.length());
				return true;
			}
		}
		return false;
	}
	public String getFunctionName(){
		return functionName;
	}
	public String getArgument(){
		return argument;
	}
	public SourceRange getReplaceRange(){
		return replaceRange;
	}
	public String getRootPath(){
		return rootPath;
	}
}
