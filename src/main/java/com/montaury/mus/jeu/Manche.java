package com.montaury.mus.jeu;

import com.montaury.mus.jeu.joueur.AffichageEvenementsDeJeu;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
import com.montaury.mus.jeu.tour.Tour;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Manche {
  private final AffichageEvenementsDeJeu affichage;

  public Manche(AffichageEvenementsDeJeu affichage) {
    this.affichage = affichage;
  }

  public Resultat jouer(Opposants opposants) {
    affichage.nouvelleManche();
    Score score = new Score(opposants);
    do {
      new Tour(affichage).jouer(opposants, score);
      affichage.tourTermine(opposants, score);
      opposants.tourner();
    } while (score.vainqueur().isEmpty());
    return new Resultat(score.vainqueur().get(), score.pointsVaincu().get());
  }

  public static class Score {
    private static final int POINTS_POUR_TERMINER_MANCHE = 40;

    private final Map<Joueur, Integer> scoreParJoueur = new HashMap<>();

    public Score(Opposants opposants) {
      scoreParJoueur.put(opposants.getJoueurEsku(), 0);
      scoreParJoueur.put(opposants.getJoueurZaku(), 0);
    }

    public Map<Joueur, Integer> scoreParJoueur() {
      return scoreParJoueur;
    }

    public void scorer(Joueur joueur, int points) {
      if (vainqueur().isEmpty()) {
        scoreParJoueur.put(joueur, Math.min(scoreParJoueur.get(joueur) + points, POINTS_POUR_TERMINER_MANCHE));
      }
    }

    public void remporterManche(Joueur joueur) {
      scoreParJoueur.put(joueur, POINTS_POUR_TERMINER_MANCHE);
    }

    public Optional<Joueur> vainqueur() {
      return scoreParJoueur.keySet().stream().filter(joueur -> scoreParJoueur.get(joueur) == POINTS_POUR_TERMINER_MANCHE).findAny();
    }

    public Optional<Integer> pointsVaincu() {
      return vainqueur().isEmpty() ?
        Optional.empty() :
        scoreParJoueur.values().stream().filter(points -> points < POINTS_POUR_TERMINER_MANCHE).findAny();
    }
  }

  public static class Resultat {
    private final Joueur vainqueur;
    private final int pointsVaincu;

    public Resultat(Joueur joueur, int pointsVaincu) {
      vainqueur = joueur;
      this.pointsVaincu = pointsVaincu;
    }

    public Joueur vainqueur() {
      return vainqueur;
    }

    public int pointsVaincu() {
      return pointsVaincu;
    }
  }
}
