package br.xtool.xtoolcore.impl.representation;

import br.xtool.xtoolcore.representation.plantuml.PlantClassRepresentation;
import br.xtool.xtoolcore.representation.plantuml.PlantStereotypeRepresentation;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlantStereotypeRepresentationImpl implements PlantStereotypeRepresentation {

	private PlantClassRepresentation umlClass;

	private String stereotype;

	public PlantStereotypeRepresentationImpl(PlantClassRepresentation umlClass, String stereotype) {
		super();
		this.umlClass = umlClass;
		this.stereotype = stereotype;
	}

	@Override
	public StereotypeType getStereotypeType() {
		String invalidStereotype = "Stereotype '%s' inválido para a classe '%s'. Os stereotypes válidos são: %s";
		// @formatter:off
		String stereotypes = Stream.of(StereotypeType.values())
				.map(StereotypeType::getType)
				.collect(Collectors.joining(","));
		return Stream.of(StereotypeType.values())
					.filter(s -> s.getType().equals(this.stereotype))
					.findAny()
					.orElseThrow(()-> new IllegalArgumentException(String.format(invalidStereotype, this.stereotype, this.umlClass.getName(), stereotypes)));
		// @formatter:on
	}

}
