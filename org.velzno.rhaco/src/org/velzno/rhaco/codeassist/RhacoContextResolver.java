package org.velzno.rhaco.codeassist;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionContextResolver;
import org.eclipse.php.internal.core.codeassist.contexts.CompletionContextResolver;
import org.velzno.rhaco.codeassist.function.FunctionContext;
import org.velzno.rhaco.codeassist.object.AccessorContext;
import org.velzno.rhaco.codeassist.package_name.PackageContext;

public class RhacoContextResolver extends CompletionContextResolver implements ICompletionContextResolver{
	public ICompletionContext[] createContexts() {
	    return new ICompletionContext[] {
	    			new FunctionContext(),
	    			new PackageContext(),
	    			new AccessorContext()
	    		};
	}
}
