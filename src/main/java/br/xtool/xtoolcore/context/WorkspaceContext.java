package br.xtool.xtoolcore.context;

import br.xtool.xtoolcore.representation.ProjectRepresentation;
import br.xtool.xtoolcore.representation.WorkspaceRepresentation;

/**
 * Contexto do workspace xtool.
 *
 * @author jcruz
 */
public interface WorkspaceContext {


    /**
     * Retorna a representação do workspace.
     *
     * @return
     */
    WorkspaceRepresentation getWorkspace();

    /**
     * Define o projeto de trabalho.
     *
     * @param projectRepresentation
     */
    void setWorkingProject(ProjectRepresentation projectRepresentation);


    /**
     * Retorna o projeto de trabalho.
     *
     * @return
     */
    ProjectRepresentation getWorkingProject();
}
