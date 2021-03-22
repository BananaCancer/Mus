package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;

public class Paires extends Phase
{
  public Paires()
  {
    super("Paires");
  }

  @Override
  protected boolean peutParticiper(Joueur joueur)
  {
    return joueur.main().aDesPaires();
  }

  @Override
  protected Joueur meilleurParmi(Opposants opposants)
  {
    Joueur joueurMeilleuresPaires = opposants.getJoueurEsku();
    com.montaury.mus.jeu.carte.paires.Paires meilleuresPaires = joueurMeilleuresPaires.main().getPaires();
    for(Joueur joueur: opposants.dansLOrdre())
    {
      if(meilleuresPaires.estMeilleureOuEgaleA(joueur.main().getPaires()))
      {
        //Meilleures Paires restent celles du joueurMeilleuresPaires qui a forcément la priorité sur les autres
      }
      else
      {
        joueurMeilleuresPaires = joueur;
      }
    }

    return  joueurMeilleuresPaires;
  }

  @Override
  public int pointsBonus(Joueur vainqueur)
  {
    return vainqueur.main().getPaires().pointsBonus();
  }
}
