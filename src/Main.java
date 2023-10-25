import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import objetos.Eleicao;

public class Main {
    public static void main(String[] args) throws Exception {
        String cargo = args[0];
        String arquivoCandidato = args[1];
        String arquivoVotacao = args[2];
        LocalDate data = LocalDate.parse(args[3], DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Eleicao eleicao = new Eleicao(cargo, arquivoCandidato, arquivoVotacao, data);
        eleicao.numeroVagas();
        eleicao.candidatosEleitos();
        eleicao.candidatosMaisVotados();
        eleicao.candidatosNaoEleitosMasSeriamMajoritariamente();
        eleicao.candidatosEleitosQueNaoSeriamMajoritariamente();
        eleicao.votacaoPartidos();
        eleicao.primeiroUltimoColocadoPartidos();
        eleicao.eleitosPorFaixaEtaria();
        eleicao.eleitosPorGenero();
        eleicao.votacaoGeral();
    }
}
