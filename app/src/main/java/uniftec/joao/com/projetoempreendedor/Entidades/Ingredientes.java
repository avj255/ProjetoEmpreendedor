package uniftec.joao.com.projetoempreendedor.Entidades;

import java.util.List;

public class Ingredientes {

    public Integer ingredienteID;
    public String nome;
    public Double peso;
    public Double calorias;
    public List<Pratos_Ingredientes> pratos_Ingredientes;

    public int getAge() {
        return ingredienteID;
    }
}
