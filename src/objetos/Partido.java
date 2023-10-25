package objetos;

import java.util.HashMap;
import java.util.Map;

public class Partido {
    private String NR_PARTIDO;
    private String SG_PARTIDO;
    private int QT_VOTOS_LEGENDA;
    private Map<String, Candidato> candidatos = new HashMap<>();

    public Partido(String nR_PARTIDO, String sG_PARTIDO) {
        NR_PARTIDO = nR_PARTIDO;
        SG_PARTIDO = sG_PARTIDO;
        QT_VOTOS_LEGENDA = 0;
    }

    public void addCandidatos(Candidato candidato){
        if(candidatos.get(candidato.getNR_CANDIDATO()) == null){
            candidatos.put(candidato.getNR_CANDIDATO(), candidato);
        }
    }

    public Candidato getCandidatoMaisVotado(){
        Candidato maisVotado = new Candidato("","","","","","","",null,"","","",0);

        for(Candidato candidato : candidatos.values()){
            if(candidato.getQT_VOTOS() > maisVotado.getQT_VOTOS()){
                maisVotado = candidato;
            }
        }

        return maisVotado;
    }

    public Candidato getCandidatoMenosVotado(){
        Candidato menosVotado = new Candidato("","","","","","","",null,"","","", 0);
        menosVotado.addVotosCandidato(Integer.MAX_VALUE);

        for(Candidato candidato : candidatos.values()){
            if(candidato.getQT_VOTOS() < menosVotado.getQT_VOTOS()){
                menosVotado = candidato;
            }
        }

        return menosVotado;
    }

    public void addVotosPartidoLegenda(int votos){
        QT_VOTOS_LEGENDA += votos;
    }

    public int getQT_VOTOS_NOMINAIS() {
        int QT_VOTOS_NOMINAIS = 0;
        for(Candidato candidato : candidatos.values()){
            QT_VOTOS_NOMINAIS += candidato.getQT_VOTOS();
        }
        return QT_VOTOS_NOMINAIS;
    }

    public int getQT_VOTOS_LEGENDA(){
        return QT_VOTOS_LEGENDA;
    }

    public int getCandidatosEleitos(){
        int eleitos = 0;
        for(Candidato candidato : candidatos.values()){
            if(candidato.getCD_SIT_TOT_TURNO().equals("2") || candidato.getCD_SIT_TOT_TURNO().equals("3")){
                eleitos++;
            }
        }
        return eleitos;
    }

    public String getNR_PARTIDO() {
        return NR_PARTIDO;
    }
    public String getSG_PARTIDO() {
        return SG_PARTIDO;
    }
    public int getQT_VOTOS_TOTAIS() {
        return getQT_VOTOS_NOMINAIS() + QT_VOTOS_LEGENDA;
    }

    
}
