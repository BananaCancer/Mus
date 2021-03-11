package com.montaury.mus.jeu.joueur;

import com.montaury.mus.jeu.Equipe;
import com.montaury.mus.jeu.carte.Carte;
import java.util.Arrays;

public class Fixtures {
  public static Joueur unJoueur() {
    return new Joueur("Ordinateur", new FausseInterfaceJoueur());
  }

  public static Joueur unJoueurAvec(Main main) {
    Joueur joueur = new Joueur("Ordinateur", new FausseInterfaceJoueur());
    joueur.donnerCartes(main.cartes());
    return joueur;
  }

  public static Equipe uneEquipe(){
    return  new Equipe("equipeOrdinaeur", unJoueur(), unJoueur());
  }

  public static Equipe uneEquipeAvec(Joueur joueurAvec1, Joueur joueurAvec2){
    return new Equipe("Ordinateur", joueurAvec1, joueurAvec2);
  }

  public static Main main(Carte... cartes) {
    return new Main(Arrays.asList(cartes));
  }
}
