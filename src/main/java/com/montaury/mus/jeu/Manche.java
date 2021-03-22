package com.montaury.mus.jeu;

import com.montaury.mus.jeu.joueur.AffichageEvenementsDeJeu;
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

  public Resultat jouerManche(Opposants opposants) {
    affichage.nouvelleManche();
    ScoreManche scoreManche = new ScoreManche(opposants);

    do {
      new Tour(affichage).jouerTour(opposants, scoreManche);
      affichage.tourTermine(opposants, scoreManche);
      opposants.tourner();
    } while (scoreManche.vainqueur().isEmpty());

    return new Resultat(scoreManche.vainqueur().get(), scoreManche.pointsVaincu().get());
  }

  public static class ScoreManche {
    private static final int POINTS_POUR_TERMINER_MANCHE = 40;

    private final Map<Equipe, Integer> scoreParEquipe = new HashMap<>();

    public ScoreManche(Opposants opposants) {
      for(Equipe equipe:opposants.getListeDesEquipes())
      {
        scoreParEquipe.put(equipe,0);
      }
    }

    public Map<Equipe, Integer> scoreParEquipe() {
      return scoreParEquipe;
    }

    public void scorer(Equipe equipe, int points) {
      if (vainqueur().isEmpty()) {
        scoreParEquipe.put(equipe, Math.min(scoreParEquipe.get(equipe) + points, POINTS_POUR_TERMINER_MANCHE));
      }
    }

    public void remporterManche(Equipe equipe) {
      scoreParEquipe.put(equipe, POINTS_POUR_TERMINER_MANCHE);
    }

    public Optional<Equipe> vainqueur() {
      return scoreParEquipe.keySet().stream().filter(equipe -> scoreParEquipe.get(equipe) == POINTS_POUR_TERMINER_MANCHE).findAny();
    }

    public Optional<Integer> pointsVaincu() {
      return vainqueur().isEmpty() ?
        Optional.empty() :
        scoreParEquipe.values().stream().filter(points -> points < POINTS_POUR_TERMINER_MANCHE).findAny();
    }
  }

  public static class Resultat {
    private final Equipe vainqueur;
    private final int pointsVaincu;

    public Resultat(Equipe equipe, int pointsVaincu) {
      vainqueur = equipe;
      this.pointsVaincu = pointsVaincu;
    }

    public Equipe vainqueur() {
      return vainqueur;
    }

    public int pointsVaincu() {
      return pointsVaincu;
    }
  }
}
