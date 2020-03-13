package br.xtool.xtoolcore.impl.representation;

import br.xtool.xtoolcore.representation.springboot.JpaEntityRepresentation;
import br.xtool.xtoolcore.representation.springboot.SpringBooSpecificationRepresentation;
import br.xtool.xtoolcore.representation.springboot.SpringBootProjectRepresentation;
import org.jboss.forge.roaster.model.source.JavaClassSource;

public class SpringBootSpecificationRepresentationImpl extends JavaClassRepresentationImpl implements SpringBooSpecificationRepresentation {

	public SpringBootSpecificationRepresentationImpl(SpringBootProjectRepresentation project, JavaClassSource javaClassSource) {
		super(project, javaClassSource);
	}

	@Override
	public JpaEntityRepresentation getTargetEntity() {
		// @formatter:off
		return this.getProject().getEntities().stream()
					.filter(jpaEntity -> jpaEntity.getName().concat("Specification").equals(this.getName()))
					.findFirst()
					.orElseThrow(() -> new IllegalArgumentException(String.format("A specification '%s' não está associada a nehuma classe Jpa.", this.getName())));
		// @formatter:on
	}

}
