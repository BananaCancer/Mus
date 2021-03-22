package com.montaury.mus.jeu.tour.phases;


import com.montaury.mus.jeu.Manche;
import com.montaury.mus.jeu.joueur.AffichageEvenementsDeJeu;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
import com.montaury.mus.jeu.tour.phases.dialogue.Dialogue;
import com.montaury.mus.jeu.tour.phases.dialogue.DialogueTermine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.montaury.mus.jeu.tour.phases.dialogue.TypeChoix.KANTA;
import static com.montaury.mus.jeu.tour.phases.dialogue.TypeChoix.PASO;
import static com.montaury.mus.jeu.tour.phases.dialogue.TypeChoix.TIRA;

public abstract class Phase
{
  private final String nomPhase;

  protected Phase(String nomPhase)
  {
    this.nomPhase = nomPhase;
  }

  public String nom()
  {
    return nomPhase;
  }

  public final Resultat jouerPhase(AffichageEvenementsDeJeu affichage, Opposants opposants, Manche.ScoreManche scoreManche)
  {
    affichage.nouvellePhase(this);
    List<Joueur> joueurs = participantsParmi(opposants);
    if (joueurs.isEmpty())
    {
      return Resultat.nonJouable();
    }
    if (joueurs.size() == 1)
    {
      return Resultat.termine(joueurs.get(0), pointsBonus(joueurs.get(0)));
    }
    DialogueTermine dialogue = new Dialogue().derouler(affichage, opposants);
    return conclure(dialogue, scoreManche, opposants);
  }

  private Resultat conclure(DialogueTermine dialogue, Manche.ScoreManche scoreManche, Opposants opposants)
  {
    if (dialogue.estConcluPar(TIRA))
    {
      Joueur joueurEmportantLaMise = dialogue.avantDernierJoueur();
      scoreManche.scorer(joueurEmportantLaMise.getEquipeDuJoueur(), dialogue.pointsEngages());
      return Resultat.termine(joueurEmportantLaMise, pointsBonus(joueurEmportantLaMise));
    }
    if (dialogue.estConcluPar(KANTA))
    {
      Joueur vainqueur = meilleurParmi(opposants);
      scoreManche.remporterManche(vainqueur.getEquipeDuJoueur());
      return Resultat.termine(vainqueur, 0);
    }

    Joueur vainqueurPhase = meilleurParmi(opposants);
    int bonus = pointsBonus(vainqueurPhase);
    return Resultat.suspendu(vainqueurPhase, dialogue.estConcluPar(PASO) && bonus != 0 ? 0 : dialogue.pointsEngages(), bonus);
  }

  public List<Joueur> participantsParmi(Opposants opposants)
  {
    List<Joueur> listeDesParticipants = new ArrayList<>();
    for(Joueur joueur:opposants.dansLOrdre())
    {
      if(peutParticiper(joueur))
      {
        listeDesParticipants.add(joueur);
      }
    }
    return  listeDesParticipants;
  }

  public final boolean peutSeDerouler(Opposants opposants)
  {
    int nombreParticipants = 0;
    boolean seDeroulerPossible;

    for(Joueur joueur: opposants.dansLOrdre())
    {
      if(peutParticiper(joueur))
      {
        nombreParticipants++;
      }
    }
    seDeroulerPossible = nombreParticipants >= 3;
    return seDeroulerPossible;
  }

  protected boolean peutParticiper(Joueur joueur)
  {
    return true;
  }

  protected abstract Joueur meilleurParmi(Opposants opposants);

  protected int pointsBonus(Joueur vainqueur)
  {
    return 0;
  }

  public static class Resultat
  {
    public static Resultat nonJouable()
    {
      return new Resultat(null, 0, 0);
    }

    public static Resultat termine(Joueur vainqueur, int bonusDeFin)
    {
      return new Resultat(vainqueur, 0, bonusDeFin);
    }

    public static Resultat suspendu(Joueur vainqueur, int pointsEnSuspens, int bonusDeFin)
    {
      return new Resultat(vainqueur, pointsEnSuspens, bonusDeFin);
    }

    private final Joueur vainqueur;
    public final int pointsEnSuspens;
    public final int bonus;

    private Resultat(Joueur vainqueur, int pointsEnSuspens, int bonus)
    {
      this.pointsEnSuspens = pointsEnSuspens;
      this.vainqueur = vainqueur;
      this.bonus = bonus;
    }

    public Optional<Joueur> vainqueur()
    {
      return Optional.ofNullable(vainqueur);
    }

    public int points()
    {
      return pointsEnSuspens + bonus;
    }
  }
}
