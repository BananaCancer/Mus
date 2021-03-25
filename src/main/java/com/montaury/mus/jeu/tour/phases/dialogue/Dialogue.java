package com.montaury.mus.jeu.tour.phases.dialogue;

import com.montaury.mus.jeu.joueur.AffichageEvenementsDeJeu;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.montaury.mus.jeu.tour.phases.dialogue.TypeChoix.PASO;

public class Dialogue {
  private final List<ChoixJoueur> choix = new ArrayList<>();

  public final DialogueTermine derouler(AffichageEvenementsDeJeu affichage, Opposants opposants) {
    Iterator<Joueur> iteratorJoueur = opposants.itererDansLOrdre();

    do {
      Joueur parlant = iteratorJoueur.next();
      //System.out.println(parlant.getNomJoueur());
      Choix choixJoueur = parlant.interfaceJoueur.faireChoixParmi(prochainsChoixPossibles());
      affichage.choixFait(parlant, choixJoueur);
      ajouter(choixJoueur, parlant);
    }
    while (enCours());

    return new DialogueTermine(choix);
  }

  void ajouter(Choix choix, Joueur joueur) {
    this.choix.add(new ChoixJoueur(choix, joueur));
  }

  boolean enCours() {
    return choix.size() <= 1 || (!dernierChoix().metFinAuDialogue() && !dernierChoix().est(PASO));
  }

  private Choix dernierChoix() {
    return choix.get(choix.size() - 1).choix;
  }

  private List<TypeChoix> prochainsChoixPossibles() {
    return choix.isEmpty() ? TypeChoix.INITIAUX : dernierChoix().prochainsChoixPossibles();
  }

  public static class ChoixJoueur {
    public final Choix choix;
    public final Joueur joueur;

    public ChoixJoueur(Choix choix, Joueur joueur) {
      this.choix = choix;
      this.joueur = joueur;
    }

    public int mise() {
      return choix.mise();
    }
  }
}
