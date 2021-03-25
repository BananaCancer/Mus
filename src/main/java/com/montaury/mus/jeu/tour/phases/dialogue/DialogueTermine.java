package com.montaury.mus.jeu.tour.phases.dialogue;

import com.montaury.mus.jeu.joueur.Joueur;
import java.util.List;
import java.util.stream.Collectors;

import static com.montaury.mus.jeu.tour.phases.dialogue.TypeChoix.IDOKI;
import static com.montaury.mus.jeu.tour.phases.dialogue.TypeChoix.PASO;
import static com.montaury.mus.jeu.tour.phases.dialogue.TypeChoix.TIRA;

public class DialogueTermine
{
  private final List<Dialogue.ChoixJoueur> choix;
  private final int pointsEngages;

  public DialogueTermine(List<Dialogue.ChoixJoueur> choix)
  {
    this.choix = choix;
    this.pointsEngages = calculerPointsEngages();
  }

  private List<Dialogue.ChoixJoueur> mises()
  {
    return choix.stream().filter(choixJoueur -> !choixJoueur.choix.est(PASO)).collect(Collectors.toList());
  }

  private int calculerPointsEngages()
  {
    if (estConcluPar(TIRA))
    {
      return mises().size() == 2 ? 1 : cumulMisesJusquauChoix(choix.size() - 2);
    }
    if (estConcluPar(IDOKI))
    {
      return cumulMisesJusquauChoix(choix.size() - 1);
    }
    // paso
    return 1;
  }

  private int cumulMisesJusquauChoix(int positionChoixExclusive)
  {
    return choix.subList(0, positionChoixExclusive).stream().mapToInt(Dialogue.ChoixJoueur::mise).sum();
  }

  public Choix dernierChoix()
  {
    return choix.get(choix.size() - 1).choix;
  }

  public int pointsEngages()
  {
    return pointsEngages;
  }

  public boolean estConcluPar(TypeChoix typeChoix)
  {
    return dernierChoix().est(typeChoix);
  }

  public Joueur avantDernierJoueur()
  {
    return choix.get(choix.size() - 2).joueur;
  }

  public boolean estSuspendu()
  {
    return dernierChoix().est(IDOKI) || dernierChoix().est(PASO);
  }
}
