package com.montaury.mus.jeu;

import com.montaury.mus.jeu.joueur.AffichageEvenementsDeJeu;
import com.montaury.mus.jeu.joueur.Opposants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Partie {
  private final AffichageEvenementsDeJeu affichage;

  public Partie(AffichageEvenementsDeJeu affichage) {
    this.affichage = affichage;
  }

  public Resultat jouerPartie(Opposants opposants) {
    affichage.nouvellePartie();
    ScorePartie scorePartie = new ScorePartie(opposants);
    Optional<Equipe> vainqueur;

    do {
      Manche.Resultat resultat = new Manche(affichage).jouerManche(opposants);
      vainqueur = scorePartie.enregistrer(resultat);
      affichage.mancheTerminee(scorePartie);
    } while (vainqueur.isEmpty());

    return new Resultat(vainqueur.get(), scorePartie);
  }

  public static class ScorePartie {
    private static final int NB_MANCHES_A_GAGNER = 3;

    private final List<Manche.Resultat> resultatManches = new ArrayList<>();
    private final Map<Equipe, Integer> manchesGagneesParEquipe = new HashMap<>();

    public ScorePartie(Opposants opposants) {
      for(Equipe equipe:opposants.getListeDesEquipes())
      {
        manchesGagneesParEquipe.put(equipe,0);
      }
    }

    public Optional<Equipe> enregistrer(Manche.Resultat score) {
      resultatManches.add(score);
      manchesGagneesParEquipe.put(score.vainqueur(), manchesGagneesParEquipe.get(score.vainqueur()) + 1);
      return vainqueur();
    }

    public List<Manche.Resultat> resultatManches() {
      return resultatManches;
    }

    public Optional<Equipe> vainqueur() {
      return manchesGagneesParEquipe.keySet().stream().filter(equipe -> manchesGagneesParEquipe.get(equipe) == NB_MANCHES_A_GAGNER).findAny();
    }
  }

  public static class Resultat {
    private final Equipe vainqueur;
    private final ScorePartie scorePartie;

    private Resultat(Equipe vainqueur, ScorePartie scorePartie) {
      this.vainqueur = vainqueur;
      this.scorePartie = scorePartie;
    }

    public Equipe vainqueur() {
      return vainqueur;
    }

    public ScorePartie score() {
      return scorePartie;
    }
  }
}
