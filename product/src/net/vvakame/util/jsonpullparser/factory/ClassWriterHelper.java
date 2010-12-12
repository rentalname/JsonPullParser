package net.vvakame.util.jsonpullparser.factory;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

public class ClassWriterHelper {

	String classPostfix;

	ProcessingEnvironment processingEnv;
	PrintWriter pw;
	Element classElement;
	Element holder;

	public ClassWriterHelper(ProcessingEnvironment prosessingEnv,
			PrintWriter pw, Element classElement, String postfix) {
		this.processingEnv = prosessingEnv;
		this.pw = pw;
		this.classElement = classElement;
		classPostfix = postfix;
	}

	ClassWriterHelper wr(String str) {
		pw.print(str);
		return this;
	}

	ClassWriterHelper wr(Element... elements) {
		Elements utils = processingEnv.getElementUtils();
		utils.printElements(pw, elements);
		return this;
	}

	ClassWriterHelper wr(Class<?> clazz) {
		wr(clazz.getCanonicalName());
		return this;
	}

	ClassWriterHelper wr(Object obj) {
		wr(obj.toString());
		return this;
	}

	ClassWriterHelper writePackage() {
		pw.print("package ");
		pw.print(getPackageName());
		pw.println(";");
		return this;
	}

	@Deprecated
	ClassWriterHelper writeImport(Class<?> clazz) {
		pw.print("import ");
		pw.print(clazz.getCanonicalName());
		pw.println(";");
		return this;
	}

	ClassWriterHelper writeClassSignature() {
		pw.print("public class ");
		pw.print(getGenerateClassName());
		pw.print("{");
		return this;
	}

	ClassWriterHelper writeClassName() {
		pw.print(getClassName());
		return this;
	}

	ClassWriterHelper writeListClassName() {
		this.wr(List.class).wr("<").writeClassName().wr(">");
		return this;
	}

	ClassWriterHelper writeListInstance() {
		this.wr(ArrayList.class).wr("<").writeClassName().wr(">");
		return this;
	}

	String getGenerateCanonicalClassName() {
		return getGenerateCanonicalClassName(classElement, classPostfix);
	}

	String getGenerateCanonicalClassName(Element element) {
		return element.asType().toString() + classPostfix;
	}

	String getGenerateCanonicalClassName(TypeMirror type) {
		Element element = processingEnv.getTypeUtils().asElement(type);
		return getGenerateCanonicalClassName(element);
	}

	String getGenerateClassName() {
		return classElement.getSimpleName().toString() + classPostfix;
	}

	String getClassName() {
		return classElement.getSimpleName().toString();
	}

	String getPackageName() {
		if (classElement.getKind() != ElementKind.CLASS) {
			throw new IllegalStateException();
		}
		String str = classElement.asType().toString();
		int i = str.lastIndexOf(".");
		return str.substring(0, i);
	}

	void flush() {
		pw.flush();
	}

	static String getGenerateCanonicalClassName(Element classElement,
			String postfix) {
		return classElement.asType().toString() + postfix;
	}

	/**
	 * @return the holder
	 */
	public Element getHolder() {
		return holder;
	}

	/**
	 * @param holder
	 *            the holder to set
	 */
	public void setHolder(Element holder) {
		this.holder = holder;
	}

	/**
	 * @return the classPostfix
	 */
	public String getClassPostfix() {
		return classPostfix;
	}
}
