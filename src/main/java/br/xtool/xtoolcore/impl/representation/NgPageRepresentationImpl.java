package br.xtool.xtoolcore.impl.representation;

import br.xtool.xtoolcore.helper.JsonHelper;
import br.xtool.xtoolcore.helper.StringHelper;
import br.xtool.xtoolcore.representation.angular.NgPageNavigationRepresentation;
import br.xtool.xtoolcore.representation.angular.NgPageRepresentation;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.tuple.Pair;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

public class NgPageRepresentationImpl extends NgComponentRepresentationImpl implements NgPageRepresentation {

	public NgPageRepresentationImpl(Path path) {
		super(path);
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.angular.NgPageRepresentation#getNavigations()
	 */
	@Override
	public List<NgPageNavigationRepresentation> getNavigations() {
		Pattern pattern = Pattern.compile(NgPageRepresentation.NAVIGATION_PATTERN);
		int idx = StringHelper.indexOfPattern(pattern, this.getTsFileContent()).getRight();
		String startNavigationArray = this.getTsFileContent().substring(idx);
		Pair<Integer, Integer> idxOfFirstArray = StringHelper.indexOfFirstArray(startNavigationArray);
		
		String strNavigation = startNavigationArray.substring(idxOfFirstArray.getLeft(), idxOfFirstArray.getRight());
		return JsonHelper.deserialize(strNavigation, new TypeReference<List<NgPageNavigationRepresentation>>() {});
	}

}
