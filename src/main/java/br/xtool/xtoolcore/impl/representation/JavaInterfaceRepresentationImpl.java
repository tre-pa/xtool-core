package br.xtool.xtoolcore.impl.representation;

import br.xtool.xtoolcore.representation.springboot.JavaInterfaceRepresentation;
import br.xtool.xtoolcore.representation.springboot.JavaMethodRepresentation;
import br.xtool.xtoolcore.representation.springboot.JavaPackageRepresentation;
import br.xtool.xtoolcore.representation.springboot.SpringBootProjectRepresentation;
import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.*;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JavaInterfaceRepresentationImpl implements JavaInterfaceRepresentation {

    private SpringBootProjectRepresentation bootProject;

    private JavaInterfaceSource javaInterfaceSource;

    public JavaInterfaceRepresentationImpl(SpringBootProjectRepresentation bootProject, JavaInterfaceSource javaInterfaceSource) {
        super();
        this.bootProject = bootProject;
        this.javaInterfaceSource = javaInterfaceSource;
    }

    /*
     * (non-Javadoc)
     * @see br.xtool.core.representation.EJavaInterface#getInstanceName()
     */
    @Override
    public String getInstanceName() {
        return StringUtils.uncapitalize(this.getName());
    }

    /*
     * (non-Javadoc)
     * @see br.xtool.core.representation.EJavaInterface#getProject()
     */
    @Override
    public SpringBootProjectRepresentation getProject() {
        return this.bootProject;
    }

    /**
     * Retorna o pacote da classe
     *
     * @return
     */
    @Override
    public JavaPackageRepresentation getJavaPackage() {
        return JavaPackageRepresentationImpl.of(this.javaInterfaceSource.getPackage());
    }

    @Override
    public String getCanonicalName() {
        return this.javaInterfaceSource.getCanonicalName();
    }

    @Override
    public String getQualifiedName() {
        return this.javaInterfaceSource.getQualifiedName();
    }

    @Override
    public List<SyntaxError> getSyntaxErrors() {
        return this.javaInterfaceSource.getSyntaxErrors();
    }

    @Override
    public boolean hasSyntaxErrors() {
        return this.javaInterfaceSource.hasSyntaxErrors();
    }

    @Override
    public boolean isClass() {
        return this.javaInterfaceSource.isClass();
    }

    @Override
    public boolean isEnum() {
        return this.javaInterfaceSource.isEnum();
    }

    @Override
    public boolean isInterface() {
        return this.javaInterfaceSource.isInterface();
    }

    @Override
    public boolean isAnnotation() {
        return this.javaInterfaceSource.isAnnotation();
    }

    @Override
    public JavaType<?> getEnclosingType() {
        return this.javaInterfaceSource.getEnclosingType();
    }

    @Override
    public String toUnformattedString() {
        return this.javaInterfaceSource.toUnformattedString();
    }

    @Override
    public String getPackage() {
        return this.javaInterfaceSource.getPackage();
    }

    @Override
    public boolean isDefaultPackage() {
        return this.javaInterfaceSource.isDefaultPackage();
    }

    @Override
    public String getName() {
        return this.javaInterfaceSource.getName();
    }

    @Override
    public boolean isPackagePrivate() {
        return this.javaInterfaceSource.isPackagePrivate();
    }

    @Override
    public boolean isPublic() {
        return this.javaInterfaceSource.isPublic();
    }

    @Override
    public boolean isPrivate() {
        return this.javaInterfaceSource.isPrivate();
    }

    @Override
    public boolean isProtected() {
        return this.javaInterfaceSource.isProtected();
    }

    @Override
    public Visibility getVisibility() {
        return this.javaInterfaceSource.getVisibility();
    }

    @Override
    public List<? extends Annotation<JavaInterfaceSource>> getAnnotations() {
        return this.javaInterfaceSource.getAnnotations();
    }

    @Override
    public boolean hasAnnotation(Class<? extends java.lang.annotation.Annotation> aClass) {
        return false;
    }


    @Override
    public boolean hasAnnotation(String type) {
        return this.javaInterfaceSource.hasAnnotation(type);
    }

    @Override
    public Annotation<JavaInterfaceSource> getAnnotation(Class<? extends java.lang.annotation.Annotation> aClass) {
        return null;
    }


    @Override
    public Annotation<JavaInterfaceSource> getAnnotation(String type) {
        return this.javaInterfaceSource.getAnnotation(type);
    }

    @Override
    public Object getInternal() {
        return this.javaInterfaceSource.getInternal();
    }

    @Override
    public JavaInterfaceSource getOrigin() {
        return this.javaInterfaceSource.getOrigin();
    }

    @Override
    public JavaDoc<JavaInterfaceSource> getJavaDoc() {
        return this.javaInterfaceSource.getJavaDoc();
    }

    @Override
    public boolean hasJavaDoc() {
        return this.javaInterfaceSource.hasJavaDoc();
    }

    @Override
    public JavaInterfaceSource getRoasterInterface() {
        return this.javaInterfaceSource;
    }

    @Override
    public Collection<JavaMethodRepresentation<JavaInterfaceSource>> getJavaMethods() {
        // @formatter:off
        return this.javaInterfaceSource.getMethods().stream()
                .map(methodSource -> new JavaMethodRepresentationImpl<>(this.javaInterfaceSource, methodSource))
                .collect(Collectors.toList());
        // @formatter:on
    }

    @Override
    public int compareTo(JavaInterfaceRepresentation o) {
        return this.getName().compareTo(o.getName());
    }

}
