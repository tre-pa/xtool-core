package br.xtool.xtoolcore.representation.springboot;

import org.jboss.forge.roaster.model.source.JavaEnumSource;

import java.util.Collection;

/**
 * Representação de um Enum Java.
 * 
 * @author jcruz
 *
 */
public interface JavaEnumRepresentation extends JavaTypeRepresentation<JavaEnumSource> {

	/**
	 * Retorna a lista de constantes do enum
	 * 
	 * @return
	 */
	Collection<String> getConstants();

	/**
	 * Retorna o objeto Roaster
	 * 
	 * @return
	 */
	JavaEnumSource getRoasterEnum();
}
