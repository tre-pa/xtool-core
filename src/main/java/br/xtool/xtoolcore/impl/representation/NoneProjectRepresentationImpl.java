package br.xtool.xtoolcore.impl.representation;

import br.xtool.xtoolcore.representation.NoneProjectRepresentation;

import java.nio.file.Path;

public class NoneProjectRepresentationImpl extends ProjectRepresentationImpl implements NoneProjectRepresentation {

	public NoneProjectRepresentationImpl(Path path) {
		super(path);
	}

	@Override
	public void refresh() {

	}

	@Override
	public String getType() {
		return "none";
	}

	@Override
	public String getVersion() {
		return "none";
	}

	@Override
	public String getFrameworkVersion() {
		return "none";
	}
}
