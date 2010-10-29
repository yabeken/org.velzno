package org.velzno.codeassist;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionContextResolver;
import org.eclipse.php.internal.core.codeassist.contexts.CompletionContextResolver;

public class Rhaco2ContextResolver extends CompletionContextResolver implements ICompletionContextResolver{
	public ICompletionContext[] createContexts() {
	    return new ICompletionContext[] {
	    			new FunctionCompletionContext(),
	    			new AccessorCompletionContext(),
	    			new PackageCompletionContext()
	    		};
	}
}
