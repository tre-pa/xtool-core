package br.xtool.xtoolcore.representation.springboot;

import org.jdom2.Element;

import java.util.Optional;

/**
 * Representação de uma dependência do pom.xml de um projeto Spring Boot.
 * 
 * @author jcruz
 *
 */
public interface PomDependencyRepresentation {

	public String getGroupId();

	String getArtifactId();

	Optional<String> getVersion();

	Element getAsDom();

}
