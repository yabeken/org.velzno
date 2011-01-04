package org.velzno.rhaco.codeassist.object;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext;

/**
 * 基底クラス Object を継承したクラスのコードアシストを行うかの判断を行う
 * @author yabeken
 */
public class AccessorContext extends ClassMemberContext {
	public boolean isValid(ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if(!super.isValid(sourceModule, offset, requestor)) return false;
		if(this.getTriggerType() != Trigger.OBJECT) return false;
		try {
			IType type = this.getLhsTypes()[0];
			if(type.getElementName().equals("Object")) return true;
			for(String className : type.getSuperClasses()){
				if(className.equals("Object")) return true;
			}
		} catch (ModelException e) {
		}
		return false;
	}
}
