package com.montaury.mus;

import com.montaury.mus.jeu.Equipe;
import com.montaury.mus.jeu.Partie;
import com.montaury.mus.jeu.joueur.AffichageConsoleEvenementsDeJeu;
import com.montaury.mus.jeu.joueur.Opposants;
import java.util.Scanner;

public class Mus {
  public static void main(String[] args) {
    System.out.print("Entrez votre nom: ");
    String nomJoueur = new Scanner(System.in).next();
    System.out.print("Entrez le nom de votre équipe: ");
    String nomEquipe = new Scanner(System.in).next();
    Equipe equipeHumain = Equipe.equipeHumain(nomEquipe, nomJoueur);

    Partie partie = new Partie(new AffichageConsoleEvenementsDeJeu(equipeHumain.getJoueurUn()));
    Partie.Resultat resultat = partie.jouerPartie(new Opposants(equipeHumain, Equipe.equipeOrdinateurs()));

    System.out.println("Le vainqueur de la partie est " + resultat.vainqueur().getNomEquipe());
  }
}
