package org.velzno.cakephp.codeassist.view;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceModule;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.AbstractCompletionStrategy;
import org.eclipse.php.internal.core.typeinference.FakeField;

@SuppressWarnings("restriction")
public class ViewHelperStrategy extends AbstractCompletionStrategy  implements ICompletionStrategy {
	public ViewHelperStrategy(ICompletionContext context) {
		super(context);
	}
	@Override
	public void apply(ICompletionReporter reporter) throws Exception {
		ViewHelperContext context = (ViewHelperContext) this.getContext();
		String statementText = context.getStatementText().toString();
		String param = statementText.substring(statementText.lastIndexOf("->")+2);
		for(IScriptFolder folder : ((ViewHelperContext)this.getContext()).getSourceModule().getScriptProject().getScriptFolders()){
			// ignore except helpers dir
			if(!folder.getElementName().endsWith("/helpers")) continue;
			for(IModelElement source : folder.getChildren()){
				if(source instanceof SourceModule){
					String sourcePath = source.getElementName();
					if(!sourcePath.endsWith(".php")) continue;
					try{
						IType classType = ((SourceModule) source).getTypes()[0];
						String className = classType.getElementName();
						if(!className.endsWith("Helper")) continue;
						if(className.startsWith(param)){
							reporter.reportField(new FakeField((ModelElement) classType, className.substring(0, className.lastIndexOf("Helper")), Modifiers.AccPublic), "->", this.getReplacementRange(context), true);
						}
					}catch(Exception e){
					}
				}
			}
		}
	}
}