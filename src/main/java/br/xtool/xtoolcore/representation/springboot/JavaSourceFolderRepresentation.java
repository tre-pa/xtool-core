package br.xtool.xtoolcore.representation.springboot;

import br.xtool.xtoolcore.representation.SourceFolderRepresentation;

import java.util.SortedSet;

/**
 * Representação de um source folder Java.
 * 
 * @author jcruz
 *
 */
public interface JavaSourceFolderRepresentation extends SourceFolderRepresentation {

	/**
	 * Retorna a lista de pacotes do source folder java.
	 * 
	 * @return
	 */
	SortedSet<JavaPackageRepresentation> getPackages();

	/**
	 * Retorna o projeto Spring Boot associado.
	 * 
	 * @return
	 */
	SpringBootProjectRepresentation getBootProject();

}
