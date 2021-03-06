package br.xtool.xtoolcore.impl.representation;

import br.xtool.xtoolcore.helper.RoasterHelper;
import br.xtool.xtoolcore.representation.angular.NgProjectRepresentation;
import br.xtool.xtoolcore.representation.plantuml.PlantClassDiagramRepresentation;
import br.xtool.xtoolcore.representation.springboot.*;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.JavaUnit;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaEnumSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Classe que representa um projeto Spring Boot
 * 
 * @author jcruz
 *
 */
public class SpringBootProjectRepresentationImpl extends ProjectRepresentationImpl implements SpringBootProjectRepresentation {

	private Collection<JavaUnit> javaUnits;

	private PomRepresentation pom;

	private SpringBootApplicationPropertiesRepresentation applicationProperties;

	private JavaClassRepresentation mainClass;

	public SpringBootProjectRepresentationImpl(Path path) {
		super(path);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EBootProject#getBaseClassName()
	 */
	@Override
	public String getBaseClassName() {
		return this.getMainclass().getName().replaceAll("Application", "");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.ESBootProject#getMainclass()
	 */
	@Override
	public JavaClassRepresentation getMainclass() {
		if (Objects.isNull(this.mainClass)) {
			// @formatter:off
			this.mainClass = this.getJavaUnits()
				.parallelStream()
				.filter(javaUnit -> javaUnit.getGoverningType().isClass())
				.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
				.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("SpringBootApplication")))
				.map(javaUnit -> new JavaClassRepresentationImpl(this, javaUnit))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Não foi possível localizar a classe principal (@SpringBootApplication). Verifique se a mesma existe ou contêm erros."));
			// @formatter:on
		}
		return this.mainClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.ESBootProject#getRootPackage()
	 */
	@Override
	public JavaPackageRepresentation getRootPkg() {
		return this.getPom().getGroupId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.ESBootProject#getMainSourceFolder()
	 */
	@Override
	public JavaSourceFolderRepresentation getMainSourceFolder() {
		return new JavaSourceFolderRepresentationImpl(this, this.getPath().resolve("src/main/java"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.ESBootProject#getTestSourceFolder()
	 */
	@Override
	public JavaSourceFolderRepresentation getTestSourceFolder() {
		return new JavaSourceFolderRepresentationImpl(this, this.getPath().resolve("src/test/java"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.ESBootProject#getPom()
	 */
	@Override
	public PomRepresentation getPom() {
		if (Objects.isNull(this.pom)) {
			this.pom = PomRepresentationImpl.of(this.getPath().resolve("pom.xml"));
		}
		return this.pom;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EBootProject#getApplicationProperties()
	 */
	@Override
	public SpringBootApplicationPropertiesRepresentation getApplicationProperties() {
		if (Objects.isNull(this.applicationProperties)) {
			this.applicationProperties = SpringBootApplicationPropertiesRepresentationImpl.of(this, this.getPath().resolve("src/main/resources/application.properties"));
		}
		return this.applicationProperties;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EBootProject#getEnums()
	 */
	@Override
	public Collection<JavaEnumRepresentation> getEnums() {
		// @formatter:off
		return this.getJavaUnits()
			.parallelStream()
			.filter(javaUnit -> javaUnit.getGoverningType().isEnum())
			.map(javaUnit -> javaUnit.<JavaEnumSource>getGoverningType())
			.map(j -> new JavaEnumRepresentationImpl(this, j))
			.collect(Collectors.toList());
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EBootProject#getEntities()
	 */
	@Override
	public SortedSet<JpaEntityRepresentation> getEntities() {
		// @formatter:off
		return this.getJavaUnits()
			.parallelStream()
			.filter(javaUnit -> javaUnit.getGoverningType().isClass())
			.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
			.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("Entity")))
			.map(j -> new JpaEntityRepresentationImpl(this, j))
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EBootProject#getProjections()
	 */
	@Override
	public SortedSet<SpringBootProjectionRepresentation> getProjections() {
		// @formatter:off
		return this.getJavaUnits()
				.parallelStream()
				.filter(javaUnit -> javaUnit.getGoverningType().isInterface())
				.map(javaUnit -> javaUnit.<JavaInterfaceSource>getGoverningType())
				.filter(j -> j.getName().endsWith("Projection"))
				.map(j -> new SpringBootProjectionRepresentationImpl(this,j))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EBootProject#getSpecifications()
	 */
	@Override
	public SortedSet<SpringBooSpecificationRepresentation> getSpecifications() {
		// @formatter:off
		return this.getJavaUnits()
				.parallelStream()
				.filter(javaUnit -> javaUnit.getGoverningType().isClass())
				.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
				.filter(j -> j.getName().endsWith("Specification"))
				.map(j -> new SpringBootSpecificationRepresentationImpl(this, j))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EBootProject#getServices()
	 */
	@Override
	public SortedSet<SpringBootServiceClassRepresentation> getServices() {
		// @formatter:off
		return this.getJavaUnits()
				.parallelStream()
				.filter(javaUnit -> javaUnit.getGoverningType().isClass())
				.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
				.filter(j -> j.getName().endsWith("Service"))
				.map(j -> new SpringBootServiceClassRepresentationImpl(this, j))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EBootProject#getRepositories()
	 */
	@Override
	public SortedSet<SpringBootRepositoryRepresentation> getRepositories() {
		// @formatter:off
		return this.getJavaUnits()
			.parallelStream()
			.filter(javaUnit -> javaUnit.getGoverningType().isInterface())
			.map(javaUnit -> javaUnit.<JavaInterfaceSource>getGoverningType())
			.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("Repository")))
			.map(j -> new SpringBootRepositoryRepresentationImpl(this, j))
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EBootProject#getRests()
	 */
	@Override
	public SortedSet<SpringBootRestClassRepresentation> getRests() {
		// @formatter:off
		return this.getJavaUnits()
			.parallelStream()
			.filter(javaUnit -> javaUnit.getGoverningType().isClass())
			.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
			.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("RestController")))
			.map(j -> new SpringBootRestClassRepresentationImpl(this, j))
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	public Collection<JavaUnit> getJavaUnits() {
		if (Objects.isNull(this.javaUnits)) {
			// @formatter:off
			this.javaUnits = this.listAllFiles().stream()
				.filter(path -> path.toString().endsWith(".java"))
				.map(Path::toFile)
				.map(RoasterHelper::loadJavaUnit)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
			// @formatter:on
		}
		return this.javaUnits;
	}

	@Override
	public Collection<JavaUnit> getRoasterJavaUnits() {
		return this.getJavaUnits();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EBootProject#getAssociatedAngularProject()
	 */
	@Override
	public Optional<NgProjectRepresentation> getAssociatedAngularProject() {
		String angularPath = this.getPath().toString().replace("-backend", "-frontend");
		if (StringUtils.isNotEmpty(angularPath)) {
			if (Files.exists(Paths.get(angularPath))) {
				if (NgProjectRepresentation.isValid(Paths.get(angularPath))) {
					return Optional.of(new NgProjectRepresentationImpl(Paths.get(angularPath)));
				}
			}
		}
		return Optional.empty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EBootProject#getDomainClassDiagram()
	 */
	@Override
	public PlantClassDiagramRepresentation getMainDomainClassDiagram() {
		return PlantClassDiagramRepresentationImpl.of(this.getPath().resolve("docs/diagrams/class/main.plantuml"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.SpringBootProjectRepresentation#getClassDiagrams()
	 */
	@Override
	@SneakyThrows
	public List<PlantClassDiagramRepresentation> getClassDiagrams() {
		try (Stream<Path> paths = Files.walk(this.getPath().resolve("docs/diagrams/class"))) {
			// @formatter:off
			return paths.filter(Files::isRegularFile)
					.filter(p -> FilenameUtils.getExtension(p.toFile().getAbsolutePath()).equals("plantuml"))
					.map(PlantClassDiagramRepresentationImpl::of)
					.collect(Collectors.toList());
			// @formatter:on
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EProject#getProjectVersion()
	 */
	@Override
	public String getVersion() {
		return getPom().getVersion();
	}

	@Override
	public String getFrameworkVersion() {
		return getPom().getParentVersion().orElse("");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EProject#refresh()
	 */
	@Override
	public void refresh() {
		this.javaUnits = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EProject#getProjectType()
	 */
	@Override
	public String getType() {
		return "springboot";
	}

}
