package br.xtool.xtoolcore.representation.repo;

import java.nio.file.Path;
import java.util.Set;

/**
 * Classe que representa um repositório de componentes
 *
 * @author jcruz
 */
public interface RepositoryRepresentation {

    static final String MASTER_REPOSITORY = "master";

    /**
     * Retorna o nome do repositório.
     *
     * @return
     */
    String getName();

    /**
     * Caminho do repositório.
     *
     * @return Path com o caminho do repositório.
     */
    Path getPath();

    /**
     * Retorna a lista de módulos do repositório.
     *
     * @return
     */
    Set<ModuleRepresentation> getModules();

    /**
     * Retorna a quantidade de componentes do repositório.
     *
     * @return
     */
    long getTotalComponents();

    /**
     * Retorna a quantidade total de módulos do repositório.
     *
     * @return
     */
    long getTotalModules();

}
