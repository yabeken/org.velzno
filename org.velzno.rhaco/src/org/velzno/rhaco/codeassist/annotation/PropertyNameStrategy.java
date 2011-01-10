package org.velzno.rhaco.codeassist.annotation;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.PHPDocTagStrategy;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

@SuppressWarnings("restriction")
public class PropertyNameStrategy extends PHPDocTagStrategy implements ICompletionStrategy{
	public PropertyNameStrategy(ICompletionContext context) {
		super(context);
	}
	public void apply(ICompletionReporter reporter) {
		PropertyNameContext context = (PropertyNameContext) this.getContext();
		String statementText = context.getStatementText().toString();
		Matcher m = Pattern.compile("@var\\s+\\w+\\s+(.*?)$").matcher(statementText);
		if(!m.find()) return;
		String param = m.group(1);
		try {
			for(IField p : this.getProperties()){
				if(p.getElementName().startsWith(param.toLowerCase())){
					reporter.reportField(p, "", new SourceRange(context.getOffset() - param.length(), param.length()), false);
				}
			}
		} catch (Exception e) {
		}
	}
	private IField[] getProperties() throws Exception{
		PropertyNameContext context = (PropertyNameContext) this.getContext();
		ArrayList<IField> result = new ArrayList<IField>();
		IType type = context.getClassType();
		for(IField f : PHPModelUtils.getTypeField(type, "", false)){
			if(f.getElementName().startsWith("$_") || !PHPFlags.isProtected(f.getFlags()) || PHPFlags.isStatic(f.getFlags())){
				continue;
			}
			result.add(f);
		}
		for(IField f : PHPModelUtils.getSuperTypeHierarchyField(type, "$", false, null)){
			if(f.getElementName().startsWith("$_") || !PHPFlags.isProtected(f.getFlags()) || PHPFlags.isStatic(f.getFlags())){
				continue;
			}
			result.add(f);
		}
		return result.toArray(new IField[0]);
	}
}
