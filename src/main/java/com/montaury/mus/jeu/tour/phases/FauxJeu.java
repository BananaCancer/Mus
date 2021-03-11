package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;

import static com.montaury.mus.jeu.tour.phases.Jeu.aLeJeu;

public class FauxJeu extends Phase {
  public FauxJeu() {
    super("Faux Jeu");
  }

  @Override
  protected boolean peutParticiper(Joueur joueur) {
    return !aLeJeu(joueur);
  }

  @Override
  protected Joueur meilleurParmi(Opposants opposants) {
    int pointsJoueurEsku = opposants.getJoueurEsku().main().pointsPourJeu();
    int pointsJoueurZaku = opposants.getJoueurZaku().main().pointsPourJeu();
    return pointsJoueurEsku >= pointsJoueurZaku ? opposants.getJoueurEsku() : opposants.getJoueurZaku();
  }

  @Override
  public int pointsBonus(Joueur vainqueur) {
    return 1;
  }
}
