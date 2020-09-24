package uniftec.joao.com.projetoempreendedor.Entidades;

import java.util.List;

public class Pratos {
    public int pratoID;
    public String nome;
    public Double valor;
    public String modopreparo;
    public String video;
    public String foto;
    public List<Pratos_Ingredientes> pratos_Ingredientes;
    public List<Ingredientes> Ingredientes;
}
