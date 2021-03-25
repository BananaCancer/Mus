package com.montaury.mus.jeu.joueur;

import com.montaury.mus.jeu.Equipe;
import com.montaury.mus.jeu.carte.Carte;
import java.util.List;

public class Joueur
{

  private Equipe equipeDuJoueur;
  private final String nomJoueur;
  public final InterfaceJoueur interfaceJoueur;
  private final Main main = Main.vide();

  public static Joueur humain(String nom)
  {
    return new Joueur(nom, new InterfaceJoueurHumain());
  }

  public static Joueur ordinateur(String nom)
  {
    return new Joueur(nom, new InterfaceJoueurOrdinateur());
  }

  public Joueur(String nomJoueur, InterfaceJoueur interfaceJoueur)
  {
    this.nomJoueur = nomJoueur;
    this.interfaceJoueur = interfaceJoueur;
  }

  public String getNomJoueur()
  {
    return nomJoueur;
  }

  public Equipe getEquipeDuJoueur()
  {
    return equipeDuJoueur;
  }

  public void setEquipeDuJoueur(Equipe equipeJoueur)
  {
    this.equipeDuJoueur = equipeJoueur;
  }

  public Main main()
  {
    return main;
  }

  public void donnerCartes(List<Carte> cartes)
  {
    main.ajouter(cartes);
    interfaceJoueur.nouvelleMain(main);
  }
}
