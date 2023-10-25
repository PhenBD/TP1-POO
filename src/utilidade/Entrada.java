package utilidade;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

import objetos.Candidato;
import objetos.Partido;

public class Entrada {
    public static void leCandidatosCSV(String cargo, String arquivoCandidato, LocalDate data_eleicao, Map<String, Candidato> candidatos, Map<String, Partido> partidos) throws IOException{
        try (FileInputStream f = new FileInputStream(arquivoCandidato);
            Scanner scanner = new Scanner(f, "ISO-8859-1")){
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] columns = line.split(";");

                String cargoParaLer = "";

                if(cargo.equals("--federal")){
                    cargoParaLer = "\"6\"";
                }
                else if(cargo.equals("--estadual")){
                    cargoParaLer = "\"7\"";
                }

                String NR_PARTIDO = columns[27].substring(1, columns[27].length() - 1);
                String SG_PARTIDO = columns[28].substring(1, columns[28].length() - 1);

                if(partidos.get(NR_PARTIDO) == null){
                    partidos.put(NR_PARTIDO, new Partido(NR_PARTIDO, SG_PARTIDO));
                }
 
                if(((!columns[68].equals("\"2\"") && !columns[68].equals("\"16\"")) && !columns[67].equals("\"Válido (legenda)\"")) || !columns[13].equals(cargoParaLer)){
                    continue;
                }
                
                String CD_CARGO = columns[13].substring(1, columns[13].length() - 1);
                String CD_SITUACAO_CANDIDATO_TOT = columns[68].substring(1, columns[68].length() - 1);
                String NR_CANDIDATO = columns[16].substring(1, columns[16].length() - 1);
                String NM_URNA_CANDIDATO = columns[18].substring(1, columns[18].length() - 1);

                

                String NR_FEDERACAO = columns[30].substring(1, columns[30].length() - 1);
                LocalDate DT_NASCIMENTO = LocalDate.parse(columns[42].substring(1, columns[42].length() - 1), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                
                String CD_SIT_TOT_TURNO = columns[56].substring(1, columns[56].length() - 1);
                String CD_GENERO = columns[45].substring(1, columns[45].length() - 1);
                String NM_TIPO_DESTINACAO_VOTOS = columns[67].substring(1, columns[67].length() - 1);

                Period idade = Period.between(DT_NASCIMENTO, data_eleicao);
                int totalDias = idade.getYears() * 365 + idade.getMonths() * 30 + idade.getDays();

                candidatos.put(NR_CANDIDATO, new Candidato(CD_CARGO, CD_SITUACAO_CANDIDATO_TOT, NR_CANDIDATO, NM_URNA_CANDIDATO, NR_PARTIDO, SG_PARTIDO, NR_FEDERACAO, DT_NASCIMENTO, CD_SIT_TOT_TURNO, CD_GENERO, NM_TIPO_DESTINACAO_VOTOS, totalDias));

                partidos.get(NR_PARTIDO).addCandidatos(candidatos.get(NR_CANDIDATO));

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void leVotacaoCSV(String cargo, String arquivoCandidato, Map<String, Candidato> candidatos, Map<String, Partido> partidos) throws IOException{
        try (FileInputStream f = new FileInputStream(arquivoCandidato);
            Scanner scanner = new Scanner(f, "ISO-8859-1")){
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] columns = line.split(";");

                String cargoParaLer = "";

                if(cargo.equals("--federal")){
                    cargoParaLer = "\"6\"";
                }
                else if(cargo.equals("--estadual")){
                    cargoParaLer = "\"7\"";
                }

                if(!columns[17].equals(cargoParaLer)){
                    continue;
                }

                String NR_VOTAVEL = columns[19].substring(1, columns[19].length() - 1);
                String QT_VOTOS = columns[21].substring(1, columns[21].length() - 1);

                if(partidos.get(NR_VOTAVEL) != null){
                    partidos.get(NR_VOTAVEL).addVotosPartidoLegenda(Integer.parseInt(QT_VOTOS));
                }

                if(candidatos.get(NR_VOTAVEL) != null){
                    if(candidatos.get(NR_VOTAVEL).getNM_TIPO_DESTINACAO_VOTOS().equals("Válido (legenda)")){
                        partidos.get(candidatos.get(NR_VOTAVEL).getNR_PARTIDO()).addVotosPartidoLegenda(Integer.parseInt(QT_VOTOS));
                    }
                    else{
                        candidatos.get(NR_VOTAVEL).addVotosCandidato(Integer.parseInt(QT_VOTOS));
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
