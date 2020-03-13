package br.xtool.xtoolcore.impl.representation;

import br.xtool.xtoolcore.representation.angular.NgBaseTypeRepresentation;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;

public class NgTypeRepresentationImpl implements NgBaseTypeRepresentation {

	private Path path;

	public NgTypeRepresentationImpl(Path path) {
		super();
		this.path = path;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.angular.NgTypeRepresentation#getPath()
	 */
	@Override
	public Path getPath() {
		return this.path;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.angular.NgTypeRepresentation#getTsFileName()
	 */
	@Override
	public String getTsFileName() {
		return this.path.getFileName().toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.angular.NgTypeRepresentation#getTsFileContent()
	 */
	@Override
	@SneakyThrows
	public String getTsFileContent() {
		return new String(Files.readAllBytes(this.path));
	}

}
