package br.xtool.xtoolcore.impl.representation;

import br.xtool.xtoolcore.representation.springboot.SpringBootProjectRepresentation;
import br.xtool.xtoolcore.representation.springboot.SpringBootProjectionRepresentation;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

public class SpringBootProjectionRepresentationImpl extends JavaInterfaceRepresentationImpl implements SpringBootProjectionRepresentation {

	public SpringBootProjectionRepresentationImpl(SpringBootProjectRepresentation bootProject, JavaInterfaceSource javaInterfaceSource) {
		super(bootProject, javaInterfaceSource);
	}

}
