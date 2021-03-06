package br.xtool.xtoolcore.impl.representation;

import br.xtool.xtoolcore.representation.angular.NgClassRepresentation;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Representa uma classe Typescript de um projeto Angular.
 * 
 * @author jcruz
 *
 */
public class NgClassRepresentationImpl extends NgTypeRepresentationImpl implements NgClassRepresentation {

	public NgClassRepresentationImpl(Path path) {
		super(path);
	}

	@Override
	public String getName() {
		// @formatter:off
		return Stream.of(FilenameUtils.getBaseName(this.getPath().toString()).split("\\-|\\."))
			.map(StringUtils::capitalize)
			.collect(Collectors.joining());
		// @formatter:on
	}

	@Override
	public String getInstanceName() {
		return StringUtils.uncapitalize(this.getName());
	}

	@Override
	public int compareTo(NgClassRepresentation o) {
		return this.getName().compareTo(o.getName());
	}

	@Override
	public String toString() {
		return "NgClassRepresentationImpl [getName()=" + getName() + ", getPath()=" + getPath() + ", getTsFileName()=" + getTsFileName() + "]";
	}

}
