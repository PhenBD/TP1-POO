package objetos;
import java.time.LocalDate;

public class Candidato {
    private String CD_CARGO;
    private String CD_SITUACAO_CANDIDATO_TOT;
    private String NR_CANDIDATO;
    private String NM_URNA_CANDIDATO;
    private String NR_PARTIDO;
    private String SG_PARTIDO;
    private String NR_FEDERACAO;
    private LocalDate DT_NASCIMENTO;
    private String CD_SIT_TOT_TURNO;
    private String CD_GENERO;
    private String NM_TIPO_DESTINACAO_VOTOS;
    private int QT_VOTOS;
    private int idadeEmDias;

    public Candidato(String cD_CARGO, String cD_SITUACAO_CANDIDATO_TOT, String nR_CANDIDATO, String nM_URNA_CANDIDATO,
            String nR_PARTIDO, String sG_PARTIDO, String nR_FEDERACAO, LocalDate dT_NASCIMENTO, String cD_SIT_TOT_TURNO,
            String cD_GENERO, String nM_TIPO_DESTINACAO_VOTOS, int idadeEmDias) {
        CD_CARGO = cD_CARGO;
        CD_SITUACAO_CANDIDATO_TOT = cD_SITUACAO_CANDIDATO_TOT;
        NR_CANDIDATO = nR_CANDIDATO;
        NM_URNA_CANDIDATO = nM_URNA_CANDIDATO;
        NR_PARTIDO = nR_PARTIDO;
        SG_PARTIDO = sG_PARTIDO;
        NR_FEDERACAO = nR_FEDERACAO;
        DT_NASCIMENTO = dT_NASCIMENTO;
        CD_SIT_TOT_TURNO = cD_SIT_TOT_TURNO;
        CD_GENERO = cD_GENERO;
        NM_TIPO_DESTINACAO_VOTOS = nM_TIPO_DESTINACAO_VOTOS;
        QT_VOTOS = 0;
        this.idadeEmDias = idadeEmDias;
    }

    public void addVotosCandidato(int votos){
        QT_VOTOS += votos;
    }
    public int getIdadeEmDias() {
        return idadeEmDias;
    }
    public int getQT_VOTOS() {
        return QT_VOTOS;
    }
    public String getCD_CARGO() {
        return CD_CARGO;
    }
    public String getCD_SITUACAO_CANDIDATO_TOT() {
        return CD_SITUACAO_CANDIDATO_TOT;
    }
    public String getNR_CANDIDATO() {
        return NR_CANDIDATO;
    }
    public String getNM_URNA_CANDIDATO() {
        return NM_URNA_CANDIDATO;
    }
    public String getNR_PARTIDO() {
        return NR_PARTIDO;
    }
    public String getSG_PARTIDO() {
        return SG_PARTIDO;
    }
    public String getNR_FEDERACAO() {
        return NR_FEDERACAO;
    }
    public LocalDate getDT_NASCIMENTO() {
        return DT_NASCIMENTO;
    }
    public String getCD_SIT_TOT_TURNO() {
        return CD_SIT_TOT_TURNO;
    }
    public String getCD_GENERO() {
        return CD_GENERO;
    }
    public String getNM_TIPO_DESTINACAO_VOTOS() {
        return NM_TIPO_DESTINACAO_VOTOS;
    }
    
}
