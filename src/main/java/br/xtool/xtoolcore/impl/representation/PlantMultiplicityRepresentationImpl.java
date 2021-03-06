package br.xtool.xtoolcore.impl.representation;

import br.xtool.xtoolcore.representation.plantuml.PlantClassRepresentation;
import br.xtool.xtoolcore.representation.plantuml.PlantMultiplicityRepresentation;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

public class PlantMultiplicityRepresentationImpl implements PlantMultiplicityRepresentation {

	private String sourceQualifier;

	private PlantClassRepresentation sourceClass;

	private PlantClassRepresentation targetClass;

	public PlantMultiplicityRepresentationImpl(PlantClassRepresentation sourceClass, PlantClassRepresentation targetClass, String sourceQualifier) {
		super();
		this.sourceQualifier = sourceQualifier;
		this.sourceClass = sourceClass;
		this.targetClass = targetClass;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlMultiplicity#getMutiplicityType()
	 */
	@Override
	public MultiplicityType getMutiplicityType() {
		String invalidMultiplicity = "Multiplicidade '%s' inválida entre %s e %s. Os tipos válidos são: %s";
		String blankMultiplicity = "É necessário definir a multiplicidade entre %s e %s";
		String multiplicities = StringUtils.join(MultiplicityType.values(), ", ");
		if (StringUtils.isBlank(this.sourceQualifier)) throw new IllegalArgumentException(String.format(blankMultiplicity, this.sourceClass.getName(), this.targetClass.getName()));
		// @formatter:off
		return Stream.of(MultiplicityType.values())
				.filter(multiplicityType -> multiplicityType.getPattern().equals(this.sourceQualifier))
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException(String.format(invalidMultiplicity, this.sourceQualifier, this.sourceClass.getName(), this.targetClass.getName(), multiplicities)));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlMultiplicity#isToMany()
	 */
	@Override
	public boolean isToMany() {
		return Stream.of(MultiplicityType.ZERO_TO_MANY, MultiplicityType.ONE_TO_MANY, MultiplicityType.MANY).anyMatch(m -> m.equals(this.getMutiplicityType()));
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlMultiplicity#isToOne()
	 */
	@Override
	public boolean isToOne() {
		return getMutiplicityType().equals(MultiplicityType.ZERO_TO_ONE) || getMutiplicityType().equals(MultiplicityType.ONE);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlMultiplicity#isOptional()
	 */
	@Override
	public boolean isOptional() {
		return Stream.of(MultiplicityType.ZERO_TO_ONE, MultiplicityType.ZERO_TO_MANY, MultiplicityType.MANY).anyMatch(m -> m.equals(this.getMutiplicityType()));
	}

}
