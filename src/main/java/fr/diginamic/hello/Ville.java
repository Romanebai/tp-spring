package fr.diginamic.hello;


public class Ville {

    private int id;
    private String nom;
    private int population;

    public Ville(int id, String nom, int population) {
        this.id = id;
        this.nom = nom;
        this.population = population;
    }

    @Override
    public String toString() {
        return "Ville{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", population=" + population +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
