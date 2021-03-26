package com.montaury.mus.jeu.joueur;

import com.montaury.mus.jeu.Equipe;

import java.util.*;

public class Opposants
{
  private Joueur joueurEsku;
  private Joueur joueurZaku;

  private List<Equipe> listeDesEquipes;
  private List<Joueur> listeDesJoueursOrdonnes;

  public Opposants(Equipe equipe1, Equipe equipe2)
  {
    listeDesEquipes = new ArrayList<>();
    listeDesJoueursOrdonnes = new ArrayList<>();

    listeDesEquipes.add(equipe1);
    listeDesEquipes.add(equipe2);

    listeDesJoueursOrdonnes.add(equipe1.getJoueurUn());
    listeDesJoueursOrdonnes.add(equipe2.getJoueurUn());
    listeDesJoueursOrdonnes.add(equipe1.getJoueurDeux());
    listeDesJoueursOrdonnes.add(equipe2.getJoueurDeux());

    joueurEsku = listeDesJoueursOrdonnes.get(0);
    joueurZaku = listeDesJoueursOrdonnes.get(3);
  }

  public void tourner()
  {
    Joueur joueurTeteDeListe;
    joueurTeteDeListe = listeDesJoueursOrdonnes.remove(0);
    listeDesJoueursOrdonnes.add(joueurTeteDeListe);
    joueurEsku = listeDesJoueursOrdonnes.get(0);
    joueurZaku = listeDesJoueursOrdonnes.get(3);
  }

  public Joueur getJoueurEsku()
  {
    return joueurEsku;
  }

  public Joueur getJoueurZaku()
  {
    return joueurZaku;
  }

  public Equipe getAutreEquipe(Equipe equipeActuelle)
  {
    int indexAutreEquipe = (listeDesEquipes.indexOf(equipeActuelle)+1)%2;
    Equipe autreEquipe = listeDesEquipes.get(indexAutreEquipe);
    return  autreEquipe;
  }

  public List<Equipe> getListeDesEquipes()
  {
    return listeDesEquipes;
  }

  public Iterator<Joueur> itererDansLOrdre()
  {
    return new IteratorInfini(this);
  }

  public List<Joueur> dansLOrdre()
  {
    return listeDesJoueursOrdonnes;
  }

  private static class IteratorInfini implements Iterator<Joueur>
  {
    private final Opposants opposants;
    private Joueur suivant;

    public IteratorInfini(Opposants opposants)
    {
      this.opposants = opposants;
      suivant = opposants.joueurEsku;
    }

    @Override
    public boolean hasNext()
    {
      return true;
    }

    @Override
    public Joueur next()
    {
      int indexJoueurSuivant;
      Joueur next = suivant;
      for (Joueur joueurActuel:opposants.listeDesJoueursOrdonnes)
      {
        if(joueurActuel == suivant)
        {
          indexJoueurSuivant = ((opposants.listeDesJoueursOrdonnes.indexOf(joueurActuel))+1)%4;
          suivant = opposants.listeDesJoueursOrdonnes.get(indexJoueurSuivant);
          break;
        }
      }
      return next;
    }
  }
}
