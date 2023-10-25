package objetos;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import utilidade.Entrada;

public class Eleicao {
    private Map<String, Candidato> candidatos = new HashMap<>();
    private Map<String, Partido> partidos = new HashMap<>();
    private LocalDate data_eleicao;
    private String cargo;
    private int vagas;

    public Eleicao(String cargo, String arquivoCandidato, String arquivoVotacao, LocalDate data_eleicao) throws IOException{
        Entrada.leCandidatosCSV(cargo, arquivoCandidato, data_eleicao, this.candidatos, this.partidos);
        Entrada.leVotacaoCSV(cargo, arquivoVotacao, this.candidatos, this.partidos);
        this.data_eleicao = data_eleicao;
        this.cargo = cargo;

        this.vagas = 0;
        for (Candidato candidato : candidatos.values()) {
            if(candidato.getCD_SIT_TOT_TURNO().equals("2") || candidato.getCD_SIT_TOT_TURNO().equals("3")){
                vagas++;
            }
        }
    }

    public void numeroVagas(){
        int vagas = 0;

        for (Candidato candidato : this.candidatos.values()) {
            if(candidato.getCD_SIT_TOT_TURNO().equals("2") || candidato.getCD_SIT_TOT_TURNO().equals("3")){
                vagas++;
            }
        }

        System.out.println("Número de vagas: " + vagas);
        System.out.println();
    }

    public void candidatosEleitos(){
        if(this.cargo.equals("--federal")){
            System.out.println("Deputados federais eleitos:");
        }
        else if(this.cargo.equals("--estudual")){
            System.out.println("Deputados estaduais eleitos:");
        }

        int colocacao = 1;

        Locale brLocale = Locale.forLanguageTag("pt-BR");
		NumberFormat nf = NumberFormat.getInstance(brLocale);

        List<Candidato> lista = new ArrayList<>(candidatos.values());
        lista.sort(Comparator.comparing(Candidato::getQT_VOTOS).reversed().thenComparing(Candidato::getIdadeEmDias).reversed());
        for (Candidato candidato : lista) {
            if(candidato.getCD_SIT_TOT_TURNO().equals("2") || candidato.getCD_SIT_TOT_TURNO().equals("3")){
                String federacao = "";
                if(!candidato.getNR_FEDERACAO().equals("-1")){
                    federacao = "*";
                }
                System.out.println(colocacao + " - " + federacao + candidato.getNM_URNA_CANDIDATO() + " ("+ candidato.getSG_PARTIDO() + ", " + nf.format(candidato.getQT_VOTOS()) + " votos)");
                colocacao++;
            }
        }

        System.out.println();
    }

    public void candidatosMaisVotados(){
        System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");

        int colocacao = 1;

        Locale brLocale = Locale.forLanguageTag("pt-BR");
		NumberFormat nf = NumberFormat.getInstance(brLocale);

        List<Candidato> lista = new ArrayList<>(candidatos.values());
        lista.sort(Comparator.comparing(Candidato::getQT_VOTOS).reversed().thenComparing(Candidato::getIdadeEmDias));
        for (int i = 0; i < this.vagas; i++) {
            String federacao = "";
            if(!lista.get(i).getNR_FEDERACAO().equals("-1")){
                federacao = "*";
            }
            System.out.println(colocacao + " - " + federacao + lista.get(i).getNM_URNA_CANDIDATO() + " ("+ lista.get(i).getSG_PARTIDO() + ", " + nf.format(lista.get(i).getQT_VOTOS()) + " votos)");
            colocacao++;
        }

        System.out.println();
    }

    public void candidatosNaoEleitosMasSeriamMajoritariamente(){
        System.out.println("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos::");
        System.out.println("(com sua posição no ranking de mais votados)");

        int colocacao = 1;

        Locale brLocale = Locale.forLanguageTag("pt-BR");
		NumberFormat nf = NumberFormat.getInstance(brLocale);

        List<Candidato> lista = new ArrayList<>(candidatos.values());
        lista.sort(Comparator.comparing(Candidato::getQT_VOTOS).reversed().thenComparing(Candidato::getIdadeEmDias));

        for (int i = 0; i < this.vagas; i++) {
            if(!(lista.get(i).getCD_SIT_TOT_TURNO().equals("2") || lista.get(i).getCD_SIT_TOT_TURNO().equals("3"))){
                String federacao = "";
                if(!lista.get(i).getNR_FEDERACAO().equals("-1")){
                    federacao = "*";
                }
                
                System.out.println(colocacao + " - " + federacao + lista.get(i).getNM_URNA_CANDIDATO() + " ("+ lista.get(i).getSG_PARTIDO() + ", " + nf.format(lista.get(i).getQT_VOTOS()) + " votos)");
            }
            colocacao++;
        }

        System.out.println();
    }

    public void candidatosEleitosQueNaoSeriamMajoritariamente(){
        System.out.println("Eleitos, que se beneficiaram do sistema proporcional:");
        System.out.println("(com sua posição no ranking de mais votados)");

        int colocacao = this.vagas;

        Locale brLocale = Locale.forLanguageTag("pt-BR");
		NumberFormat nf = NumberFormat.getInstance(brLocale);

        List<Candidato> lista = new ArrayList<>(candidatos.values());
        lista.sort(Comparator.comparing(Candidato::getQT_VOTOS).reversed().thenComparing(Candidato::getIdadeEmDias));

        for (int i = this.vagas; i < lista.size(); i++) {
            if((lista.get(i).getQT_VOTOS() < lista.get(this.vagas - 1).getQT_VOTOS()) && (lista.get(i).getCD_SIT_TOT_TURNO().equals("2") || lista.get(i).getCD_SIT_TOT_TURNO().equals("3"))){
                String federacao = "";
                if(!lista.get(i).getNR_FEDERACAO().equals("-1")){
                    federacao = "*";
                }
                
                System.out.println(colocacao + " - " + federacao + lista.get(i).getNM_URNA_CANDIDATO() + " ("+ lista.get(i).getSG_PARTIDO() + ", " + nf.format(lista.get(i).getQT_VOTOS()) + " votos)");
            }
            colocacao++;
        }

        System.out.println();
    }

    public void votacaoPartidos(){
        System.out.println("Votação dos partidos e número de candidatos eleitos:");

        int colocacao = 1;

        Locale brLocale = Locale.forLanguageTag("pt-BR");
        NumberFormat nf = NumberFormat.getInstance(brLocale);

        List<Partido> lista = new ArrayList<>(partidos.values());
        lista.sort(Comparator.comparing(Partido::getQT_VOTOS_TOTAIS).reversed());

        for(int i = 0; i < lista.size(); i++){
            System.out.println(colocacao + " - " + lista.get(i).getSG_PARTIDO() + " - " + lista.get(i).getNR_PARTIDO() + ", " + nf.format(lista.get(i).getQT_VOTOS_TOTAIS()) + " votos (" + nf.format(lista.get(i).getQT_VOTOS_NOMINAIS()) + " nominais e " + nf.format(lista.get(i).getQT_VOTOS_LEGENDA()) + " de legenda), " + lista.get(i).getCandidatosEleitos() + " candidatos eleitos");
            colocacao++;
        }

        System.out.println();
    }

    public void primeiroUltimoColocadoPartidos(){
        System.out.println("Primeiro e último colocados de cada partido:");

        int colocacao = 1;

        Locale brLocale = Locale.forLanguageTag("pt-BR");
        NumberFormat nf = NumberFormat.getInstance(brLocale);

        List<Partido> lista = new ArrayList<>(partidos.values());
        lista.sort(Comparator.comparing(Partido -> ((objetos.Partido) Partido).getCandidatoMaisVotado().getQT_VOTOS()).reversed());

        for(Partido partido : lista){
            if(partido.getCandidatoMaisVotado().getQT_VOTOS() == 0){
                continue;
            }
            System.out.println(colocacao + " - " + partido.getSG_PARTIDO() + " - " + partido.getNR_PARTIDO() + ", " + partido.getCandidatoMaisVotado().getNM_URNA_CANDIDATO() + " ("+ partido.getCandidatoMaisVotado().getNR_CANDIDATO() + ", " + nf.format(partido.getCandidatoMaisVotado().getQT_VOTOS()) + " votos) / " + partido.getCandidatoMenosVotado().getNM_URNA_CANDIDATO() + " ("+ partido.getCandidatoMenosVotado().getNR_CANDIDATO() + ", " + nf.format(partido.getCandidatoMenosVotado().getQT_VOTOS()) + " votos)");
            colocacao++;
        }

        System.out.println();
    }

    public void eleitosPorFaixaEtaria(){
        int idade0_30 = 0, idade30_40 = 0, idade40_50 = 0, idade50_60 = 0, idade60_ = 0;

        for(Candidato candidato : candidatos.values()){
            if(candidato.getCD_SIT_TOT_TURNO().equals("2") || candidato.getCD_SIT_TOT_TURNO().equals("3")){
                Period idade = Period.between(candidato.getDT_NASCIMENTO(), data_eleicao);

                if(idade.getYears() < 30){
                    idade0_30++;
                }else if(idade.getYears() >= 30 && idade.getYears() < 40){
                    idade30_40++;
                }else if(idade.getYears() >= 40 && idade.getYears() < 50){
                    idade40_50++;
                }else if(idade.getYears() >= 50 && idade.getYears() < 60){
                    idade50_60++;
                }else if(idade.getYears() >= 60){
                    idade60_++;
                }
            }
        }

        double idade0_30_percent = (double)idade0_30 / (double)this.vagas * 100;
        double idade30_40_percent = (double)idade30_40 / (double)this.vagas * 100;
        double idade40_50_percent = (double)idade40_50 / (double)this.vagas * 100;
        double idade50_60_percent = (double)idade50_60 / (double)this.vagas * 100;
        double idade60_percent = (double)idade60_ / (double)this.vagas * 100;

        System.out.println("Eleitos, por faixa etária (na data da eleição):");
        
        System.out.printf("      Idade < 30: %d (%.2f%%)%n", idade0_30, idade0_30_percent);
        System.out.printf("30 <= Idade < 40: %d (%.2f%%)%n", idade30_40, idade30_40_percent);
        System.out.printf("40 <= Idade < 50: %d (%.2f%%)%n", idade40_50, idade40_50_percent);
        System.out.printf("50 <= Idade < 60: %d (%.2f%%)%n", idade50_60, idade50_60_percent);
        System.out.printf("60 <= Idade     : %d (%.2f%%)%n", idade60_, idade60_percent);

        System.out.println();
    }

    public void eleitosPorGenero(){
        int feminino = 0, masculino = 0;

        for(Candidato candidato : candidatos.values()){
            if(candidato.getCD_SIT_TOT_TURNO().equals("2") || candidato.getCD_SIT_TOT_TURNO().equals("3")){
                if(candidato.getCD_GENERO().equals("4")){
                    feminino++;
                }else if(candidato.getCD_GENERO().equals("2")){
                    masculino++;
                }
            }
        }

        double feminino_percent = (double)feminino / (double)this.vagas * 100;
        double masculino_percent = (double)masculino / (double)this.vagas * 100;

        System.out.println("Eleitos, por gênero:");
        
        System.out.printf("Feminino: %2d (%.2f%%)%n", feminino, feminino_percent);
        System.out.printf("Masculino: %2d (%.2f%%)%n", masculino, masculino_percent);

        System.out.println();
    }

    public void votacaoGeral(){
        int votos_nominais = 0, votos_legenda = 0, votos_totais = 0;
        for(Partido partido : partidos.values()){
            votos_nominais += partido.getQT_VOTOS_NOMINAIS();
            votos_legenda += partido.getQT_VOTOS_LEGENDA();
            votos_totais += partido.getQT_VOTOS_TOTAIS();
        }

        double votos_nominais_percent = (double)votos_nominais / (double)votos_totais * 100;
        double votos_legenda_percent = (double)votos_legenda / (double)votos_totais * 100;

        System.out.printf("Total de votos válidos: %5d%n", votos_totais);
        System.out.printf("Total de votos nominais: %5d (%.2f%%)%n", votos_nominais, votos_nominais_percent);
        System.out.printf("Total de votos de legenda: %5d (%.2f%%)%n", votos_legenda, votos_legenda_percent);

        System.out.println();
    }
}
