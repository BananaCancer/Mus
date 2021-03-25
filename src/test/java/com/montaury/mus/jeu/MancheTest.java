package com.montaury.mus.jeu;

import com.montaury.mus.jeu.joueur.AffichageEvenementsDeJeu;
import com.montaury.mus.jeu.joueur.InterfaceJoueur;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
import com.montaury.mus.jeu.tour.phases.dialogue.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MancheTest {

  private Manche manche;

  @BeforeEach
  void setUp() {
    interfaceJoueurEsku = mock(InterfaceJoueur.class);
    interfaceJoueurZaku = mock(InterfaceJoueur.class);
    interfaceJoueurNormal1 = mock(InterfaceJoueur.class);
    interfaceJoueurNormal2 = mock(InterfaceJoueur.class);
    joueurEsku = new Joueur("J1", interfaceJoueurEsku);
    joueurZaku = new Joueur("J4", interfaceJoueurZaku);
    joueurNormal1 = new Joueur("J2", interfaceJoueurNormal1);
    joueurNormal2 = new Joueur("J3", interfaceJoueurNormal2);


    equipeEsku = new Equipe("E1", joueurEsku, joueurNormal1);
    equipeZaku = new Equipe("E2", joueurNormal2, joueurZaku);
    opposants = new Opposants(equipeEsku, equipeZaku);
    manche = new Manche(mock(AffichageEvenementsDeJeu.class));
  }

  @Test
  void devrait_terminer_la_manche_si_hordago_au_grand() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Hordago());
    when(interfaceJoueurNormal2.faireChoixParmi(any())).thenReturn(new Kanta());
    when(interfaceJoueurNormal1.faireChoixParmi(any())).thenReturn(new Kanta());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Kanta());

    Manche.Resultat resultat = manche.jouerManche(opposants);

    assertThat(resultat.vainqueur()).isNotNull();
    assertThat(resultat.pointsVaincu()).isZero();
  }

  @Test
  void devrait_terminer_la_manche_si_un_joueur_a_40_points() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Paso());
    when(interfaceJoueurNormal2.faireChoixParmi(any())).thenReturn(new Gehiago(40));
    when(interfaceJoueurNormal1.faireChoixParmi(any())).thenReturn(new Gehiago(1));
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Tira());

    Manche.Resultat resultat = manche.jouerManche(opposants);

    assertThat(resultat.vainqueur().getJoueurUn()).isEqualTo(joueurEsku);
    assertThat(resultat.pointsVaincu()).isZero();
  }

  @Test
  void devrait_changer_l_ordre_des_opposants_a_la_fin_du_tour() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Hordago());
    when(interfaceJoueurNormal2.faireChoixParmi(any())).thenReturn(new Kanta());
    when(interfaceJoueurNormal1.faireChoixParmi(any())).thenReturn(new Kanta());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Kanta());

    manche.jouerManche(opposants);

    assertThat(opposants.dansLOrdre()).containsExactly(joueurZaku, joueurEsku);
  }

  private InterfaceJoueur interfaceJoueurEsku;
  private InterfaceJoueur interfaceJoueurZaku;
  private InterfaceJoueur interfaceJoueurNormal1;
  private InterfaceJoueur interfaceJoueurNormal2;
  private Joueur joueurEsku;
  private Joueur joueurZaku;
  private Joueur joueurNormal1;
  private Joueur joueurNormal2;
  private Equipe equipeEsku;
  private Equipe equipeZaku;

  private Opposants opposants;

}