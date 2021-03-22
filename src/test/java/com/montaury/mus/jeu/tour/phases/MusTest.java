package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.Equipe;
import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.carte.Defausse;
import com.montaury.mus.jeu.joueur.AffichageConsoleEvenementsDeJeu;
import com.montaury.mus.jeu.joueur.InterfaceJoueur;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.montaury.mus.jeu.carte.Fixtures.paquetEntierCroissant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MusTest {

  @BeforeEach
  void setUp() {
    defausse = new Defausse();
    mus = new Mus(paquetEntierCroissant(), defausse, new AffichageConsoleEvenementsDeJeu(joueurEsku));
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
  }

  @Test
  void devrait_distribuer_quatre_cartes_a_chaque_joueur() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(false);

    mus.jouerMus(opposants);

    assertThat(joueurEsku.main().cartes()).containsExactly(Carte.AS_BATON, Carte.AS_COUPE, Carte.AS_EPEE, Carte.AS_PIECE);
    assertThat(joueurNormal2.main().cartes()).containsExactly(Carte.DEUX_BATON, Carte.DEUX_COUPE, Carte.DEUX_EPEE, Carte.DEUX_PIECE);
    assertThat(joueurNormal1.main().cartes()).containsExactly(Carte.TROIS_BATON, Carte.TROIS_COUPE, Carte.TROIS_EPEE, Carte.TROIS_PIECE);
    assertThat(joueurZaku.main().cartes()).containsExactly(Carte.QUATRE_BATON, Carte.QUATRE_COUPE, Carte.QUATRE_EPEE, Carte.QUATRE_PIECE);
  }

  @Test
  void devrait_se_terminer_si_le_joueur_esku_veut_sortir() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(false);

    mus.jouerMus(opposants);

    verify(interfaceJoueurZaku, times(0)).veutAllerMus();
  }

  @Test
  void devrait_se_terminer_si_le_joueur_zaku_veut_sortir() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurNormal1.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurNormal2.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurZaku.veutAllerMus()).thenReturn(false);

    mus.jouerMus(opposants);

    verify(interfaceJoueurEsku, times(0)).cartesAJeter();
  }

  @Test
  void devrait_demander_les_cartes_a_jeter_aux_joueurs_s_ils_vont_mus() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(true, false);
    when(interfaceJoueurNormal1.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurNormal2.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurZaku.veutAllerMus()).thenReturn(true);

    mus.jouerMus(opposants);

    verify(interfaceJoueurEsku, times(1)).cartesAJeter();
    verify(interfaceJoueurNormal1, times(1)).cartesAJeter();
    verify(interfaceJoueurNormal2, times(1)).cartesAJeter();
    verify(interfaceJoueurZaku, times(1)).cartesAJeter();
  }

  @Test
  void devrait_defausser_les_cartes_a_jeter_si_les_joueurs_vont_mus() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(true, false);
    when(interfaceJoueurEsku.cartesAJeter()).thenReturn(List.of(Carte.AS_COUPE));
    when(interfaceJoueurNormal2.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurNormal2.cartesAJeter()).thenReturn(List.of(Carte.DEUX_COUPE));
    when(interfaceJoueurNormal1.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurNormal1.cartesAJeter()).thenReturn(List.of(Carte.TROIS_COUPE));
    when(interfaceJoueurZaku.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurZaku.cartesAJeter()).thenReturn(List.of(Carte.QUATRE_COUPE));

    mus.jouerMus(opposants);

    assertThat(defausse.reprendreCartes()).containsExactly(Carte.AS_COUPE, Carte.DEUX_COUPE, Carte.TROIS_COUPE, Carte.QUATRE_COUPE);
  }

  @Test
  void devrait_distribuer_des_cartes_pour_remplacer_les_cartes_jetees_si_les_joueurs_vont_mus() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(true, false);
    when(interfaceJoueurEsku.cartesAJeter()).thenReturn(List.of(Carte.AS_COUPE));
    when(interfaceJoueurNormal2.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurNormal2.cartesAJeter()).thenReturn(List.of(Carte.DEUX_COUPE));
    when(interfaceJoueurNormal1.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurNormal1.cartesAJeter()).thenReturn(List.of(Carte.TROIS_COUPE));
    when(interfaceJoueurZaku.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurZaku.cartesAJeter()).thenReturn(List.of(Carte.QUATRE_COUPE));

    mus.jouerMus(opposants);

    assertThat(joueurEsku.main().cartes()).containsExactly(Carte.AS_BATON, Carte.AS_EPEE, Carte.AS_PIECE, Carte.CINQ_BATON);
    assertThat(joueurNormal2.main().cartes()).containsExactly(Carte.DEUX_BATON, Carte.DEUX_EPEE, Carte.DEUX_PIECE, Carte.CINQ_COUPE);
    assertThat(joueurNormal1.main().cartes()).containsExactly(Carte.TROIS_BATON, Carte.TROIS_EPEE, Carte.TROIS_PIECE, Carte.CINQ_EPEE);
    assertThat(joueurZaku.main().cartes()).containsExactly(Carte.QUATRE_BATON, Carte.QUATRE_EPEE, Carte.QUATRE_PIECE, Carte.CINQ_PIECE);
  }

  private Mus mus;
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
  private Defausse defausse;
}