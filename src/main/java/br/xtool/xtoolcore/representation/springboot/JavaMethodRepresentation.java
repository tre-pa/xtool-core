package br.xtool.xtoolcore.representation.springboot;

import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.JavaSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import java.util.Collection;
import java.util.SortedSet;

/**
 * Representação de um método java.
 * 
 * @author jcruz
 *
 */
public interface JavaMethodRepresentation<T extends JavaSource<T>> extends Comparable<JavaMethodRepresentation<T>> {

	/**
	 * Retorna o objeto JavaClass do método
	 * 
	 * @return
	 */
	JavaSource<T> getJavaSource();

	/**
	 * Retorna o nome do método.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Retorna o tipo de retorno do método.
	 * 
	 * @return
	 */
	Type<T> getReturnType();

	/**
	 * Retorna os parametros do método.
	 * 
	 * @return
	 */
	Collection<JavaMethodParameterRepresentation<T>> getParameters();

	/**
	 * Retorna as annotations do método.
	 * 
	 * @return
	 */
	SortedSet<JavaAnnotationRepresentation<T>> getAnnotations();

	/**
	 * Retorna o objeto MethodSource.
	 * 
	 * @return
	 */
	MethodSource<T> getRoasterMethod();
}
