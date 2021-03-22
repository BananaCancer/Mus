package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.carte.ValeurCarte;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Main;
import com.montaury.mus.jeu.joueur.Opposants;
import java.util.List;

import static com.montaury.mus.jeu.carte.ValeurCarte.Comparaison.PLUS_GRANDE;
import static com.montaury.mus.jeu.carte.ValeurCarte.Comparaison.PLUS_PETITE;

public class Petit extends Phase
{
  public Petit()
  {
    super("Petit");
  }

  @Override
  protected Joueur meilleurParmi(Opposants opposants)
  {
    Joueur joueurPrioritaire = comparerMainsPetit(opposants.getJoueurEsku(), opposants.dansLOrdre().get(2));
    Joueur joueurNonPrioritaire = comparerMainsPetit(opposants.dansLOrdre().get(1), opposants.getJoueurZaku());
    joueurPrioritaire = comparerMainsPetit(joueurPrioritaire, joueurNonPrioritaire);

    return joueurPrioritaire;
  }

  private Joueur comparerMainsPetit(Joueur joueurPrioritaire, Joueur joueurNonPrioritaire)
  {
    Joueur joueurMeilleureMain = joueurPrioritaire;
    List<Carte> cartesJoueurPrioritaire = joueurPrioritaire.main().cartesDuPlusGrandAuPlusPetit();
    List<Carte> cartesJoueurNonPrioritaire = joueurNonPrioritaire.main().cartesDuPlusGrandAuPlusPetit();
    for (int i = Main.TAILLE - 1; i >= 0; i--)
    {
      ValeurCarte.Comparaison compare = cartesJoueurPrioritaire.get(i).comparerAvec(cartesJoueurNonPrioritaire.get(i));
      if (compare == PLUS_PETITE) {
        joueurMeilleureMain = joueurPrioritaire;
      }
      if (compare == PLUS_GRANDE) {
        joueurMeilleureMain = joueurNonPrioritaire;
      }
    }
    return joueurMeilleureMain;
  }
}
