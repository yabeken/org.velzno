package org.velzno.codeassist;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext;
import org.eclipse.php.internal.core.codeassist.strategies.ClassMembersStrategy;
import org.eclipse.php.internal.core.typeinference.FakeMethod;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

/**
 * 基底クラス Object を継承したクラスのアクセッサを補完する
 * @author yabeken
 */
@SuppressWarnings("restriction")
public class AccessorCompletionStrategy extends ClassMembersStrategy implements ICompletionStrategy{
	public AccessorCompletionStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws Exception {
		ICompletionContext context = (ICompletionContext) getContext();
		IType type = ((ClassMemberContext) context).getLhsTypes()[0];
		if(!FunctionCompletionContext.getFunctionName().equals("C")){
			for(IMethod m : getAccessorMethods(type)){
				reporter.reportMethod(m, "()", getReplacementRange(context));
			}
		}
		FunctionCompletionContext.clearNames();
	}
	
	private IMethod[] getAccessorMethods(IType type){
		List<IMethod> result = new LinkedList<IMethod>();
		List<String> accessors = new ArrayList<String>();
		if(Rhaco2Utils.isSubclassOfObject(type)){
			try {
				for(String a : extractAccessors(PHPModelUtils.getSuperTypeHierarchyMethod(type, "___", false, null))){
					accessors.add(a);
				}
				for(String a : extractAccessors(PHPModelUtils.getTypeMethod(type, "___", false))){
					accessors.add(a);
				}
				for(String f : extractAccessorFields(PHPModelUtils.getSuperTypeHierarchyField(type, "$", false, null))){
					result.add(new FakeMethod((ModelElement) type, f));
					for(String a : accessors){
						result.add(new FakeMethod((ModelElement) type, a + "_" + f));
					}
				}
				for(String f : extractAccessorFields(PHPModelUtils.getTypeField(type, "$", false))){
					result.add(new FakeMethod((ModelElement) type, f));
					for(String a : accessors){
						result.add(new FakeMethod((ModelElement) type, a + "_" + f));
					}
				}
			} catch (Exception e) {
				return new IMethod[0];
			}
		}
		return result.toArray(new IMethod[result.size()]);
	}
	private String[] extractAccessors(IMethod[] methods){
		List<String> result = new ArrayList<String>();
		for(IMethod m : methods){
			String a = m.getElementName();
			if(a.endsWith("___") && !a.equals("___get___") && !a.equals("___set___")){
				result.add(a.substring(3, a.length() - 3));
			}
		}
		return result.toArray(new String[result.size()]);
	}
	private String[] extractAccessorFields(IField[] fields) throws Exception{
		List<String> result = new ArrayList<String>();
		for(IField f : fields){
			if(f.getElementName().startsWith("$_") || !PHPFlags.isProtected(f.getFlags()) || PHPFlags.isStatic(f.getFlags())){
				continue;
			}
			result.add(f.getElementName().substring(1));
		}
		return result.toArray(new String[result.size()]);
	}
}