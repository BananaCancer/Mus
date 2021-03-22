package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.carte.ValeurCarte;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Main;
import com.montaury.mus.jeu.joueur.Opposants;
import java.util.List;

import static com.montaury.mus.jeu.carte.ValeurCarte.Comparaison.PLUS_GRANDE;
import static com.montaury.mus.jeu.carte.ValeurCarte.Comparaison.PLUS_PETITE;

public class Grand extends Phase
{
  public Grand()
  {
    super("Grand");
  }

  @Override
  protected Joueur meilleurParmi(Opposants opposants)
  {
    Joueur joueurPrioritaire = comparerMainsGrand(opposants.getJoueurEsku(), opposants.dansLOrdre().get(2));
    Joueur joueurNonPrioritaire = comparerMainsGrand(opposants.dansLOrdre().get(1), opposants.getJoueurZaku());
    joueurPrioritaire = comparerMainsGrand(joueurPrioritaire, joueurNonPrioritaire);

    return  joueurPrioritaire;
  }

  private  Joueur comparerMainsGrand(Joueur joueurPrioritaire, Joueur joueurNonPrioritaire)
  {
    Joueur joueurMeilleureMain = joueurPrioritaire;
    List<Carte> cartesJoueurEsku = joueurPrioritaire.main().cartesDuPlusGrandAuPlusPetit();
    List<Carte> cartesJoueurZaku = joueurNonPrioritaire.main().cartesDuPlusGrandAuPlusPetit();

    for (int i = 0; i < Main.TAILLE; i++)
    {
      ValeurCarte.Comparaison compare = cartesJoueurEsku.get(i).comparerAvec(cartesJoueurZaku.get(i));
      if (compare == PLUS_GRANDE)
      {
        joueurMeilleureMain = joueurPrioritaire;
      }
      if (compare == PLUS_PETITE)
      {
        joueurMeilleureMain = joueurNonPrioritaire;
      }
    }

    return joueurMeilleureMain;
  }
}
