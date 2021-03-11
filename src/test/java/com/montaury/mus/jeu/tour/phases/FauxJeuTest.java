package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.Equipe;
import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
import org.junit.jupiter.api.Test;

import static com.montaury.mus.jeu.joueur.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

class FauxJeuTest {
  @Test
  void ne_doit_pas_se_derouler_si_un_seul_des_joueurs_a_le_jeu() {
    Opposants opposants = new Opposants(
      uneEquipeAvec(unJoueurAvec(main(Carte.AS_BATON, Carte.QUATRE_PIECE, Carte.VALET_BATON, Carte.SIX_COUPE)), unJoueur()),
      uneEquipeAvec(unJoueur(), unJoueurAvec(main(Carte.VALET_PIECE, Carte.CAVALIER_PIECE, Carte.ROI_BATON, Carte.AS_PIECE)))
    );

    boolean peutSeDerouler = new FauxJeu().peutSeDerouler(opposants);

    assertThat(peutSeDerouler).isFalse();
  }

  @Test
  void doit_se_derouler_si_personne_n_a_le_jeu() {
    Opposants opposants = new Opposants(
      uneEquipeAvec(unJoueurAvec(main(Carte.AS_BATON, Carte.QUATRE_PIECE, Carte.VALET_BATON, Carte.SIX_COUPE)), unJoueur()),
      uneEquipeAvec(unJoueur(), unJoueurAvec(main(Carte.VALET_PIECE, Carte.SIX_PIECE, Carte.QUATRE_BATON, Carte.AS_PIECE)))

    );

    boolean peutSeDerouler = new FauxJeu().peutSeDerouler(opposants);

    assertThat(peutSeDerouler).isTrue();
  }

  @Test
  void devrait_faire_gagner_le_joueur_ayant_le_plus_grand_nombre_de_points() {
    Joueur joueurZaku = unJoueurAvec(main(Carte.VALET_PIECE, Carte.SIX_PIECE, Carte.QUATRE_BATON, Carte.ROI_COUPE));
    Equipe equipeZaku = uneEquipeAvec(unJoueur(), joueurZaku);
    Opposants opposants = new Opposants(
      uneEquipeAvec(unJoueurAvec(main(Carte.AS_BATON, Carte.QUATRE_PIECE, Carte.VALET_BATON, Carte.SIX_COUPE)), unJoueur()),
      equipeZaku
    );

    Joueur vainqueur = new FauxJeu().meilleurParmi(opposants);

    assertThat(vainqueur).isEqualTo(joueurZaku);
  }

  @Test
  void devrait_faire_gagner_le_joueur_esku_en_cas_d_egalite() {
    Joueur joueurEsku = unJoueurAvec(main(Carte.AS_BATON, Carte.QUATRE_PIECE, Carte.VALET_BATON, Carte.SIX_COUPE));
    Equipe equipeEsku = uneEquipeAvec(joueurEsku, unJoueur());
    Opposants opposants = new Opposants(
      equipeEsku,
      uneEquipeAvec(unJoueur(), unJoueurAvec(main(Carte.VALET_PIECE, Carte.SIX_PIECE, Carte.QUATRE_BATON, Carte.AS_PIECE)))
    );

    Joueur vainqueur = new FauxJeu().meilleurParmi(opposants);

    assertThat(vainqueur).isEqualTo(joueurEsku);
  }

  @Test
  void doit_faire_gagner_un_bonus_de_1() {
    Joueur joueurEsku = unJoueurAvec(main(Carte.AS_BATON, Carte.QUATRE_PIECE, Carte.VALET_BATON, Carte.SIX_COUPE));

    int bonus = new FauxJeu().pointsBonus(joueurEsku);

    assertThat(bonus).isEqualTo(1);
  }

}
