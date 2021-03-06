package br.xtool.xtoolcore.impl.representation;

import br.xtool.xtoolcore.representation.springboot.*;
import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.util.Types;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class JavaFieldRepresentationImpl implements JavaFieldRepresentation {

	private JavaClassRepresentation javaClass;

	protected FieldSource<JavaClassSource> fieldSource;

	public JavaFieldRepresentationImpl(JavaClassRepresentation javaClass, FieldSource<JavaClassSource> fieldSource) {
		super();
		this.javaClass = javaClass;
		this.fieldSource = fieldSource;
	}

	/**
	 * Retorna o nome do attributo.
	 * 
	 * @return
	 */
	@Override
	public String getName() {
		return this.fieldSource.getName();
	}

	@Override
	public String getLabel() {
		// @formatter:off
		return this.fieldSource.getJavaDoc().getTags()
			.stream()
			.filter(docTag -> docTag.getName().equals("@label"))
			.map(docTag -> docTag.getValue())
			.findAny()
			.orElse(this.fieldSource.getName());
		// @formatter:on
	}

	@Override
	public JavaClassRepresentation getJavaClass() {
		return this.javaClass;
	}

	@Override
	public boolean isSimpleField() {
		return !this.isRelationshipField() || !this.isCollectionField() || !this.isEnumField();
	}

	@Override
	public Optional<JavaEnumRepresentation> getEnum() {
		// @formatter:off
		return this.getJavaClass().getProject().getEnums().stream()
				.filter(javaEnum -> javaEnum.getName().equals(this.getType().getName()))
				.findFirst();
		// @formatter:on
	}

	@Override
	public boolean isEnumField() {
		return this.getEnum().isPresent();
	}

	@Override
	public boolean isRelationshipField() {
		return this.getRelationship().isPresent();
	}

	/**
	 * Retorna o tipo do atributo.
	 * 
	 * @return
	 */
	@Override
	public Type<JavaClassSource> getType() {
		return this.fieldSource.getType();
	}

	/**
	 * Verifica se o atributo é uma coleção
	 * 
	 * @return
	 */
	@Override
	public boolean isCollectionField() {
		return this.fieldSource.getType().isType(List.class) || this.getType().isType(Set.class) || this.getType().isType(Collection.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.representation.springboot.JavaFieldRepresentation#isStringField
	 * ()
	 */
	@Override
	public boolean isStringField() {
		return this.getType().isType(String.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.representation.springboot.JavaFieldRepresentation#isNumberField
	 * ()
	 */
	@Override
	public boolean isNumberField() {
		return this.getType().isType(Long.class) || this.getType().isType(Integer.class) || this.getType().isType(Short.class) || this.getType().isType(BigDecimal.class)
				|| this.getType().isType(BigInteger.class) || this.getType().isType(Byte.class) || this.getType().isType(Float.class) || this.getType().isType(Double.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.springboot.JavaFieldRepresentation#
	 * isTemporalField()
	 */
	@Override
	public boolean isTemporalField() {
		return this.getType().isType(Date.class) || this.getType().isType(java.sql.Date.class) || this.getType().isType(LocalDate.class) || this.getType().isType(LocalDateTime.class);
	}

	@Override
	public boolean isBooleanField() {
		return this.getType().isType(Boolean.class);
	}

	@Override
	public boolean isLongField() {
		return this.getType().isType(Long.class);
	}

	@Override
	public boolean isIntegerField() {
		return this.getType().isType(Integer.class);
	}

	@Override
	public boolean isByteField() {
		return this.getType().isType(Byte.class);
	}

	@Override
	public boolean isBigDecimalField() {
		return this.getType().isType(BigDecimal.class);
	}

	@Override
	public boolean isLocalDate() {
		return this.getType().isType(LocalDate.class);
	}

	@Override
	public boolean isLocalDateTime() {
		return this.getType().isType(LocalDateTime.class);
	}

	@Override
	public boolean isNotNullField() {
		return false;
	}

	@Override
	public boolean isUniqueField() {
		return false;
	}

	/**
	 * Verifica se o atributo é static.
	 * 
	 * @return
	 */
	@Override
	public boolean isStaticField() {
		return this.fieldSource.isStatic();
	}

	@Override
	public SortedSet<JavaAnnotationRepresentation<JavaClassSource>> getAnnotations() {
		// @formatter:off
		return this.fieldSource.getAnnotations()
				.stream()
				.map(JavaAnnotationRepresentationImpl::new)
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	@Override
	public JavaAnnotationRepresentation<JavaClassSource> addAnnotation(Class<? extends Annotation> type) {
		// @formatter:off
		return this.getAnnotations().stream()
				.filter(javaAnn -> javaAnn.getName().equals(type.getSimpleName()))
				.findAny()
				.orElseGet(() -> new JavaAnnotationRepresentationImpl<JavaClassSource>(this.fieldSource.addAnnotation(type)));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EJavaField#getRoasterField()
	 */
	@Override
	public FieldSource<JavaClassSource> getRoasterField() {
		return this.fieldSource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EJavaField#getRelationship()
	 */
	@Override
	public Optional<JavaRelationshipRepresentation> getRelationship() {
		if (this.isCollectionField()) {
			String entityName = Types.getGenericsTypeParameter(this.getType().getQualifiedNameWithGenerics());
			// @formatter:off
			return this.getJavaClass().getProject().getEntities().stream()
					.filter(entity -> entity.getName().equals(entityName))
					.map(entityTarget -> new JavaRelationshipRepresentationImpl(this.javaClass, entityTarget, this))
					.map(JavaRelationshipRepresentation.class::cast)
					.findFirst();
			// @formatter:on
		}
		// @formatter:off
		String entityName = this.getType().getName();
		return this.getJavaClass().getProject().getEntities().stream()
				.filter(entity -> entity.getName().equals(entityName))
				.map(entityTarget -> new JavaRelationshipRepresentationImpl(this.javaClass, entityTarget, this))
				.map(JavaRelationshipRepresentation.class::cast)
				.findFirst();
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(JavaFieldRepresentation o) {
		return this.getName().compareTo(o.getName());
	}

}
