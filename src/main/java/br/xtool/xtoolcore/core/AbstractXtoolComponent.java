package br.xtool.xtoolcore.core;

import picocli.CommandLine;

/**
 * Classe base para os componentes Xtool.
 */
public abstract class AbstractXtoolComponent {

    /**
     * Retorna as opções de linha de comando (picocli).
     *
     * @return
     */
    public CommandLine.Model.OptionSpec options() {
        return null;
    }

    /**
     * Método que deverá ser implementado pelo componente Xtool.
     */
    public abstract void run();

}
