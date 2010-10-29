package org.velzno.codeassist;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext;

/**
 * 基底クラス Object を継承したクラスのコードアシストを行うかの判断を行う
 * @author yabeken
 */
public class AccessorCompletionContext extends ClassMemberContext {
	public boolean isValid(ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if(super.isValid(sourceModule, offset, requestor) && getTriggerType() == Trigger.OBJECT){
			return Rhaco2Utils.isSubclassOfObject(getLhsTypes()[0]);
		}
		return false;
	}
}
