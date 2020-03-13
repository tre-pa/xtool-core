package br.xtool.xtoolcore.impl.representation;

import br.xtool.xtoolcore.representation.angular.NgComponentRepresentation;
import br.xtool.xtoolcore.representation.angular.NgHtmlTemplateRepresentation;
import br.xtool.xtoolcore.representation.angular.NgRoute;
import br.xtool.xtoolcore.representation.angular.NgTsClassRepresentation;

import java.nio.file.Path;

public class NgComponentRepresentationImpl extends NgClassRepresentationImpl implements NgComponentRepresentation {

	public NgComponentRepresentationImpl(Path path) {
		super(path);
	}

	@Override
	public String getName() {
		return super.getName().concat("Component");
	}

	@Override
	public String getRoutePath() {
		return this.getTsFileName().replace("-component", "");
	}

	@Override
	public NgHtmlTemplateRepresentation getNgHtmlTemplate() {
		throw new UnsupportedOperationException();
	}

	@Override
	public NgTsClassRepresentation getNgTsClass() {
		return new NgTsClassRepresentationImpl(this.getPath().resolve(String.format("%s.component.ts", this.getTsFileName())));
	}

	@Override
	public NgRoute getDefaultRoute() {
		return new NgRoute();
	}

}
