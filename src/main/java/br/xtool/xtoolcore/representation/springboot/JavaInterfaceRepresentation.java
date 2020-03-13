package br.xtool.xtoolcore.representation.springboot;


import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import java.util.Collection;

/**
 * Representação de uma interface Java.
 * 
 * @author jcruz
 *
 */
public interface JavaInterfaceRepresentation extends JavaTypeRepresentation<JavaInterfaceSource>, Comparable<JavaInterfaceRepresentation> {

	/**
	 * 
	 * @return
	 */
	String getInstanceName();

	/**
	 * Retorna a interface roaster.
	 * 
	 * @return
	 */
	JavaInterfaceSource getRoasterInterface();

	/**
	 * Retorna os métodos da interface.
	 * 
	 * @return
	 */
	Collection<JavaMethodRepresentation<JavaInterfaceSource>> getJavaMethods();

}
