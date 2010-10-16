package org.velzno.codeassist;

import org.eclipse.dltk.core.IType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

public class Rhaco2Utils {
	/**
	 * Object を継承したクラスかどうか
	 * @param type
	 * @return boolean
	 */
	public static boolean isSubclassOfObject(IType type){
		if(type.getElementName().equals("Object")) return true;
		try {
			for(IType t : PHPModelUtils.getSuperClasses(type, null)){
				if(t.getElementName().equals("Object")) return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
}
