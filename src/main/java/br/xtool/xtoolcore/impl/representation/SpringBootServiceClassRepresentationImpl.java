package br.xtool.xtoolcore.impl.representation;

import br.xtool.xtoolcore.representation.springboot.SpringBootProjectRepresentation;
import br.xtool.xtoolcore.representation.springboot.SpringBootServiceClassRepresentation;
import org.jboss.forge.roaster.model.source.JavaClassSource;

public class SpringBootServiceClassRepresentationImpl extends JavaClassRepresentationImpl implements SpringBootServiceClassRepresentation {

	public SpringBootServiceClassRepresentationImpl(SpringBootProjectRepresentation project, JavaClassSource javaClassSource) {
		super(project, javaClassSource);
	}

}
