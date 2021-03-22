package com.montaury.mus.jeu.tour;

import com.montaury.mus.jeu.Manche;
import com.montaury.mus.jeu.carte.Defausse;
import com.montaury.mus.jeu.carte.Paquet;
import com.montaury.mus.jeu.joueur.AffichageEvenementsDeJeu;
import com.montaury.mus.jeu.joueur.Opposants;
import com.montaury.mus.jeu.tour.phases.FauxJeu;
import com.montaury.mus.jeu.tour.phases.Grand;
import com.montaury.mus.jeu.tour.phases.Jeu;
import com.montaury.mus.jeu.tour.phases.Mus;
import com.montaury.mus.jeu.tour.phases.Paires;
import com.montaury.mus.jeu.tour.phases.Petit;
import com.montaury.mus.jeu.tour.phases.Phase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tour
{

  private final AffichageEvenementsDeJeu affichage;
  private final Paquet paquet;
  private final Defausse defausse;

  public Tour(AffichageEvenementsDeJeu affichage)
  {
    this.affichage = affichage;
    this.defausse = new Defausse();
    this.paquet = Paquet.nouveauMelange(defausse);
  }

  Tour(AffichageEvenementsDeJeu affichage, Paquet paquet, Defausse defausse)
  {
    this.affichage = affichage;
    this.paquet = paquet;
    this.defausse = defausse;
  }

  public void jouerTour(Opposants opposants, Manche.ScoreManche scoreManche)
  {
    affichage.nouveauTour(opposants);
    new Mus(paquet, defausse, affichage).jouerMus(opposants);

    ResultatsPhases resultats = new ResultatsPhases();
    Iterator<Phase> phases = phasesJouablesPar(opposants).iterator();

    do
    {
      Phase.Resultat resultat = phases.next().jouerPhase(affichage, opposants, scoreManche);
      resultats.ajouter(resultat);
    } while (phases.hasNext() && scoreManche.vainqueur().isEmpty());


    resultats.attribuerPointsRestants(scoreManche);
  }

  private static Iterable<Phase> phasesJouablesPar(Opposants opposants)
  {
    Phase phaseDuJeu = new Jeu();
    if (phaseDuJeu.participantsParmi(opposants).isEmpty())
    {
      phaseDuJeu = new FauxJeu();
    }
    return List.of(new Grand(), new Petit(), new Paires(), phaseDuJeu);
  }

  static class ResultatsPhases
  {
    private final List<Phase.Resultat> resultats = new ArrayList<>();

    public void ajouter(Phase.Resultat resultat)
    {
      this.resultats.add(resultat);
    }

    public void attribuerPointsRestants(Manche.ScoreManche scoreManche)
    {
      Iterator<Phase.Resultat> resultatPhase = resultats.iterator();

      while (resultatPhase.hasNext() && scoreManche.vainqueur().isEmpty())
      {
        Phase.Resultat resultat = resultatPhase.next();
        resultat.vainqueur().ifPresent(vainqueur ->
          scoreManche.scorer(vainqueur.getEquipeDuJoueur(), resultat.pointsEnSuspens + resultat.bonus));
      }
    }
  }
}
