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
 * 基底クラス Object を継承したクラスのコードアシストを行う
 * @author yabeken
 */
@SuppressWarnings("restriction")
public class ObjectMethodCompletionStrategy extends ClassMembersStrategy implements ICompletionStrategy{
	private List<String> excludeMethods = new ArrayList<String>();
	public ObjectMethodCompletionStrategy(ICompletionContext context) {
		super(context);
		this.excludeMethods.add("__new__");
		this.excludeMethods.add("__init__");
		this.excludeMethods.add("__del__");
		this.excludeMethods.add("__clone__");
		this.excludeMethods.add("__get__");
		this.excludeMethods.add("__set__");
		this.excludeMethods.add("__str__");
		this.excludeMethods.add("__hash__");
		this.excludeMethods.add("__add__");
		this.excludeMethods.add("__sub__");
		this.excludeMethods.add("__mul__");
		this.excludeMethods.add("__div__");
		this.excludeMethods.add("__cp__");
		this.excludeMethods.add("__import__");
		this.excludeMethods.add("__shutdown__");
		
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
				for(String a : extractAccessors(PHPModelUtils.getSuperTypeHierarchyMethod(type, "__", false, null))){
					accessors.add(a);
				}
				for(String a : extractAccessors(PHPModelUtils.getTypeMethod(type, "__", false))){
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
			if(!m.getElementName().endsWith("__") || m.getElementName().startsWith("__choices_") 
					|| excludeMethods.contains(m.getElementName())){
				continue;
			}
			result.add(m.getElementName().substring(2, m.getElementName().length() - 2));
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