package org.velzno.codeassist;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ProjectFragment;
import org.eclipse.dltk.internal.core.ScriptProject;
import org.eclipse.dltk.internal.core.SourceModule;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.internal.core.codeassist.CompletionCompanion;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.AbstractCompletionStrategy;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * import/module 関数の補完を行う
 * @author yabeken
 */
public class PackageCompletionStrategy extends AbstractCompletionStrategy implements ICompletionStrategy {
	public PackageCompletionStrategy(ICompletionContext context) {
		super(context);
	}

	@Override
	public void init(CompletionCompanion companion) {
	}

	@SuppressWarnings("restriction")
	@Override
	public void apply(ICompletionReporter reporter) throws Exception {
		PackageCompletionContext context = (PackageCompletionContext) this.getContext();
		for(IScriptFolder folder : context.getSourceModule().getScriptProject().getScriptFolders()){
			String path = folder.getPath().toString();
			if(path.contains("vendors")){
				path = path.substring(path.indexOf("vendors") + "vendors".length());
			}else if(path.contains("libs")){
				path = path.substring(path.indexOf("libs") + "libs".length());
			}else{
				continue;
			}
			path = path.replace('/', '.');
			if(path.startsWith(".")) path = path.substring(1);
			if(!context.getFunctionName().equals("module")){
				if(Pattern.compile("\\.[A-Z].*?\\.").matcher(path).find()) continue;
				if(Pattern.compile("\\.[A-Z][a-zA-Z0-9_]*?$").matcher(path).find()){
					if(path.startsWith(context.getArgument())){
						reporter.reportKeyword(path, "", context.getReplaceRange());
					}
					continue;
				}
			}
			for(IModelElement source : folder.getChildren()){
				if(source instanceof SourceModule){
					if(!source.getPath().getFileExtension().equals("php")) continue;
					String filename = source.getElementName();
					if(!"ABCDEFGHIJKLMNOPQRSTUVWXYZ".contains(filename.substring(0, 1))) continue;
					filename = filename.substring(0, filename.indexOf('.'));
					String filepath = path + "." + filename;
					if(filepath.contains("." + filename + "." + filename)) continue;
					if(filepath.startsWith(context.getRootPath() + context.getArgument())){
						reporter.reportKeyword(filepath.substring(context.getRootPath().length()), "", context.getReplaceRange());
					}
				}
			}
		}
	}
}
