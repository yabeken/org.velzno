package org.velzno.codeassist;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.core.codeassist.ICompletionStrategyFactory;

public class Rhaco2CompletionStrategyFactory implements ICompletionStrategyFactory{
	@Override
	public ICompletionStrategy[] create(ICompletionContext[] contexts) {
		List<ICompletionStrategy> result = new LinkedList<ICompletionStrategy>();
		for(ICompletionContext context : contexts){
			if(context instanceof AccessorCompletionContext){
				result.add(new AccessorCompletionStrategy(context));
			}else if(context instanceof PackageCompletionContext){
				result.add(new PackageCompletionStrategy(context));
			}
		}
		return (ICompletionStrategy[]) result.toArray(new ICompletionStrategy[result.size()]);
	}

}
