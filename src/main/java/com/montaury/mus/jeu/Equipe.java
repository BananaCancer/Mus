package com.montaury.mus.jeu;

import com.montaury.mus.jeu.joueur.Joueur;

public class Equipe {
    private String nomEquipe;
    private Joueur joueurUn;
    private Joueur joueurDeux;

    public static Equipe equipeHumain(String nomEquipe, String nomJoueur)
    {
        return new Equipe(nomEquipe, Joueur.humain(nomJoueur), Joueur.ordinateur());
    }

    public static Equipe equipeOdinateurs()
    {
        return new Equipe("Equipe ordinateurs", Joueur.ordinateur(), Joueur.ordinateur());
    }

    public Equipe(String nomE, Joueur joueur1, Joueur joueur2)
    {
        this.nomEquipe = nomE;
        this.joueurUn = joueur1;
        this.joueurDeux = joueur2;
    }

    public Joueur getJoueurUn() {
        return joueurUn;
    }

    public Joueur getJoueurDeux() {
        return joueurDeux;
    }

    public String getNomEquipe() {
        return nomEquipe;
    }
}
