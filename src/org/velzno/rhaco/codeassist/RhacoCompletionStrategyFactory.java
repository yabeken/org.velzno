package org.velzno.rhaco.codeassist;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.core.codeassist.ICompletionStrategyFactory;
import org.velzno.rhaco.codeassist.object.AccessorContext;
import org.velzno.rhaco.codeassist.object.AccessorStrategy;
import org.velzno.rhaco.codeassist.package_name.PackageContext;
import org.velzno.rhaco.codeassist.package_name.PackageStrategy;

public class RhacoCompletionStrategyFactory implements ICompletionStrategyFactory{
	@Override
	public ICompletionStrategy[] create(ICompletionContext[] contexts) {
		List<ICompletionStrategy> result = new LinkedList<ICompletionStrategy>();
		for(ICompletionContext context : contexts){
			if(context instanceof AccessorContext){
				result.add(new AccessorStrategy(context));
			}else if(context instanceof PackageContext){
				result.add(new PackageStrategy(context));
			}
		}
		return (ICompletionStrategy[]) result.toArray(new ICompletionStrategy[result.size()]);
	}

}
