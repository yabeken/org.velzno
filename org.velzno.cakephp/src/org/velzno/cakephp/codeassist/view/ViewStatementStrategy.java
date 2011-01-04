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
public class ViewStatementStrategy extends AbstractCompletionStrategy  implements ICompletionStrategy {
	public ViewStatementStrategy(ICompletionContext context) {
		super(context);
	}

	@Override
	public void apply(ICompletionReporter reporter) throws Exception {
		ViewStatementContext context = (ViewStatementContext)this.getContext();
		String statementText = context.getStatementText().toString();
		//helpers
		for(IScriptFolder folder : context.getSourceModule().getScriptProject().getScriptFolders()){
			if(!folder.getElementName().endsWith("/helpers")) continue;
			for(IModelElement source : folder.getChildren()){
				if(source instanceof SourceModule){
					String sourcePath = source.getPath().toString();
					if(!sourcePath.endsWith(".php")) continue;
					IType classType = ((SourceModule) source).getTypes()[0];
					String className = classType.getElementName();
					if(!className.endsWith("Helper")) continue;
					String complete1 = "$" + className.substring(0, className.lastIndexOf("Helper")).toLowerCase();
					if(complete1.startsWith(statementText)){
						reporter.reportField(new FakeField((ModelElement) context.getSourceModule(), complete1, Modifiers.AccPublic), "->", this.getReplacementRange(context), false);
					}
					String complete2 = "$this->" + className.substring(0, className.lastIndexOf("Helper"));
					if(complete2.startsWith(statementText)){
						reporter.reportField(new FakeField((ModelElement) context.getSourceModule(), complete2, Modifiers.AccPublic), "->", this.getReplacementRange(context), false);
					}
				}
			}
		}
	}
}
