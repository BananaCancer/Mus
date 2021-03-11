package com.montaury.mus.jeu.joueur;

import com.montaury.mus.jeu.Equipe;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class Opposants {
  private Joueur joueurEsku;
  private Joueur joueurZaku;
  private Deque<Joueur> listeDesJoueursOrdonnes;

  private Equipe equipeEsku;
  private  Equipe equipeZaku;

  public Opposants(Equipe equipeEsku, Equipe equipeZaku) {
    listeDesJoueursOrdonnes = new ArrayDeque<Joueur>();
    listeDesJoueursOrdonnes.add(equipeEsku.getJoueurUn());
    listeDesJoueursOrdonnes.add(equipeZaku.getJoueurUn());
    listeDesJoueursOrdonnes.add(equipeEsku.getJoueurDeux());
    listeDesJoueursOrdonnes.add(equipeZaku.getJoueurDeux());
    this.joueurEsku = listeDesJoueursOrdonnes.getFirst();
    this.joueurZaku = listeDesJoueursOrdonnes.getLast();
  }

  public void tourner() {
    Joueur joueurTeteDeFil = listeDesJoueursOrdonnes.remove();
    listeDesJoueursOrdonnes.add(joueurTeteDeFil);
    joueurEsku = listeDesJoueursOrdonnes.getFirst();
    joueurZaku = listeDesJoueursOrdonnes.getLast();
  }

  public Joueur getJoueurEsku() {
    return joueurEsku;
  }

  public Joueur getJoueurZaku() {
    return joueurZaku;
  }



  public Iterator<Joueur> itererDansLOrdre() {
    return new IteratorInfini(this);
  }

  public List<Joueur> dansLOrdre() {
    return List.of(joueurEsku, joueurZaku);
  }

  private static class IteratorInfini implements Iterator<Joueur> {
    private final Opposants opposants;
    private Joueur suivant;

    public IteratorInfini(Opposants opposants) {
      this.opposants = opposants;
      suivant = opposants.joueurEsku;
    }

    @Override
    public boolean hasNext() {
      return true;
    }

    @Override
    public Joueur next() {
      Joueur next = suivant;
      suivant = suivant == opposants.joueurEsku ? opposants.joueurZaku : opposants.joueurEsku;
      return next;
    }
  }
}
