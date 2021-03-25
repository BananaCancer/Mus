package com.montaury.mus.jeu.joueur;

import com.montaury.mus.jeu.Manche;
import com.montaury.mus.jeu.Partie;
import com.montaury.mus.jeu.tour.phases.Phase;
import com.montaury.mus.jeu.tour.phases.dialogue.Choix;
import java.util.stream.Collectors;

public class AffichageConsoleEvenementsDeJeu implements AffichageEvenementsDeJeu
{
  private final Joueur joueurCourant;

  public AffichageConsoleEvenementsDeJeu(Joueur courant)
  {
    this.joueurCourant = courant;
  }

  @Override
  public void nouvellePartie()
  {
    println("Nouvelle partie");
  }

  @Override
  public void nouvelleManche()
  {
    println("Nouvelle manche");
  }

  @Override
  public void mancheTerminee(Partie.ScorePartie scorePartie)
  {
    println("Manche terminée");
    scorePartie.resultatManches().forEach(manche -> println("Vainqueur : " + manche.vainqueur() + ", score du perdant : " + manche.pointsVaincu()));
  }

  @Override
  public void nouveauTour(Opposants opposants)
  {
    println(opposants.getJoueurEsku().getNomJoueur() + " est esku");
  }

  @Override
  public void tourTermine(Opposants opposants, Manche.ScoreManche scoreManche)
  {
    println("Tour terminé");
    opposants.dansLOrdre().forEach(this::afficherMain);
    scoreManche.scoreParEquipe().forEach((key, value) -> println("Score " + key.getNomEquipe() + ": " + value));
    println();
  }

  @Override
  public void choixFait(Joueur joueur, Choix choix)
  {
    println(joueur.getNomJoueur() + ": " + description(choix));
    //System.out.println(joueur.getNomJoueur());
  }

  private static String description(Choix choix)
  {
    return choix.type().nom();
  }

  @Override
  public void nouvelleMain(Joueur joueur)
  {
    if (joueur == joueurCourant)
    {
      println("Votre main est: ");
      afficherMain(joueur);
      println();
    }
  }

  private void afficherMain(Joueur joueur)
  {
    println(joueur.getNomJoueur() + ": " + joueur.main().cartesDuPlusGrandAuPlusPetit().stream().map(carte -> carte.valeur() + " " + carte.type()).collect(Collectors.joining(" | ")));
  }

  @Override
  public void nouvellePhase(Phase phase)
  {
    println("Nouvelle phase: " + phase.nom());
  }

  @Override
  public void partieTerminee(Partie.Resultat resultat)
  {
    println("La partie est terminée !");
    println("Vainqueur: " + resultat.vainqueur());
  }

  private void println(String string)
  {
    System.out.println(string);
  }

  private void println()
  {
    System.out.println();
  }
}
