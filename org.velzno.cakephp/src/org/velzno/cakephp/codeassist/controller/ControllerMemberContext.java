package org.velzno.cakephp.codeassist.controller;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext;

/**
 * complete contoller members
 * 1. $this->Model
 * 2. $this->Component
 * 3. $this->Model after $this->loadModel('Model');
 */
public class ControllerMemberContext extends ClassMemberContext{
	public boolean isValid(ISourceModule sourceModule, int offset, CompletionRequestor requestor){
		if(!super.isValid(sourceModule, offset, requestor)) return false;
		try {
			return (this.getLhsTypes()[0]).getElementName().endsWith("Controller");
		}catch(Exception e){
		}
		return false;
	}
}
