package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.carte.Defausse;
import com.montaury.mus.jeu.carte.Paquet;
import com.montaury.mus.jeu.joueur.AffichageEvenementsDeJeu;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
import com.montaury.mus.jeu.tour.phases.dialogue.Mintza;
import java.util.List;

public class Mus
{
  private final Paquet paquet;
  private final Defausse defausse;
  private final AffichageEvenementsDeJeu affichage;

  public Mus(Paquet paquet, Defausse defausse, AffichageEvenementsDeJeu affichage)
  {
    this.paquet = paquet;
    this.defausse = defausse;
    this.affichage = affichage;
  }

  public void jouerMus(Opposants opposants)
  {
    List<Joueur> joueursDansLOrdre = opposants.dansLOrdre();
    joueursDansLOrdre.forEach(joueur -> joueur.main().jeterTout());

    boolean mus;
    
    do
    {
      joueursDansLOrdre.forEach(joueur -> completerMain(affichage, joueur));
      mus = veulentAllerMus(affichage, joueursDansLOrdre);
      if (mus)
      {
        joueursDansLOrdre.forEach(this::allerMus);
      }
    }
    while (mus);
    
  }

  private void completerMain(AffichageEvenementsDeJeu affichage, Joueur joueur)
  {
    joueur.donnerCartes(paquet.tirer(joueur.main().nombreCartesManquantes()));
    affichage.nouvelleMain(joueur);
  }

  private boolean veulentAllerMus(AffichageEvenementsDeJeu affichage, List<Joueur> joueurs)
  {
    for (Joueur joueur : joueurs)
    {
      boolean mus = joueur.interfaceJoueur.veutAllerMus();
      affichage.choixFait(joueur, mus ? new com.montaury.mus.jeu.tour.phases.dialogue.Mus() : new Mintza());
      if (!mus)
      {
        return false;
      }
    }
    return true;
  }

  private void allerMus(Joueur joueur) {
    List<Carte> cartesAJeter = joueur.interfaceJoueur.cartesAJeter();
    joueur.main().retirer(cartesAJeter);
    defausse.jeter(cartesAJeter);
  }
}
