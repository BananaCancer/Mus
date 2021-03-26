package com.montaury.mus.jeu;

import com.montaury.mus.jeu.joueur.Joueur;

import java.util.ArrayList;
import java.util.List;

public class Equipe {
    private String nomEquipe;
    private List<Joueur> listeJoueursEquipe;

    public static Equipe equipeHumain(String nomEquipe, String nomJoueur)
    {
        return new Equipe(nomEquipe, Joueur.humain(nomJoueur), Joueur.ordinateur("Camarade"));
    }

    public static Equipe equipeOrdinateurs()
    {
        return new Equipe("Equipe ordinateurs", Joueur.ordinateur("Méchant 1"), Joueur.ordinateur("Méchant 2"));
    }

    public Equipe(String nomE, Joueur joueur1, Joueur joueur2)
    {
        listeJoueursEquipe = new ArrayList<>();
        this.nomEquipe = nomE;
        joueur1.setEquipeDuJoueur(this);
        joueur2.setEquipeDuJoueur(this);

        listeJoueursEquipe.add(joueur1);
        listeJoueursEquipe.add(joueur2);
    }

    public Joueur getJoueurUn() {
        return listeJoueursEquipe.get(0);
    }

    public Joueur getJoueurDeux() {
        return listeJoueursEquipe.get(1);
    }

    public Joueur getAutreJoueurEquipe(Joueur joueurActuelEquipe)
    {
        int indexAutreJoueur = (listeJoueursEquipe.indexOf(joueurActuelEquipe)+1)%2;
        Joueur autreJoueurEquipe = listeJoueursEquipe.get(indexAutreJoueur);
        return autreJoueurEquipe;
    }

    public List<Joueur> getListeJoueursEquipe() {
        return listeJoueursEquipe;
    }

    public String getNomEquipe() {
        return nomEquipe;
    }
}
