package br.xtool.xtoolcore.impl.representation;

import br.xtool.xtoolcore.representation.angular.NgListRepresentation;
import br.xtool.xtoolcore.representation.angular.NgRoute;

import java.nio.file.Path;

public class NgListRepresentationImpl extends NgComponentRepresentationImpl implements NgListRepresentation {

	public NgListRepresentationImpl(Path path) {
		super(path);
	}

	@Override
	public NgRoute getDefaultRoute() {
		NgRoute ngRoute = new NgRoute();
		ngRoute.setPath("");
		ngRoute.setComponent(this.getName());
		return ngRoute;
	}

}
