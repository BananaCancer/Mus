package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;

import static com.montaury.mus.jeu.tour.phases.Jeu.aLeJeu;

public class FauxJeu extends Phase {
  public FauxJeu()
  {
    super("Faux Jeu");
  }

  @Override
  protected boolean peutParticiper(Joueur joueur)
  {
    return !aLeJeu(joueur);
  }

  @Override
  protected Joueur meilleurParmi(Opposants opposants)
  {
    Joueur joueurMeilleurFauxJeu = opposants.getJoueurEsku();
    int scoreMeilleurFauxJeu = joueurMeilleurFauxJeu.main().pointsPourJeu();

    for(Joueur joueur:opposants.dansLOrdre())
    {
      if(joueur.main().pointsPourJeu() > scoreMeilleurFauxJeu)
      {
        joueurMeilleurFauxJeu = joueur;
        scoreMeilleurFauxJeu = joueur.main().pointsPourJeu();
      }
    }
    return joueurMeilleurFauxJeu;
  }

  @Override
  public int pointsBonus(Joueur vainqueur)
  {
    return 1;
  }
}
