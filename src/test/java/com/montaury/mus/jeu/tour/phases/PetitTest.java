package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.Equipe;
import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
import org.junit.jupiter.api.Test;

import static com.montaury.mus.jeu.joueur.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

class PetitTest {
  @Test
  void devrait_faire_gagner_le_joueur_esku_s_il_a_la_plus_petite_carte() {
    Joueur joueurEsku = unJoueurAvec(main(Carte.DEUX_PIECE, Carte.CINQ_PIECE, Carte.SEPT_BATON, Carte.AS_COUPE));
    Joueur joueurZaku = unJoueurAvec(main(Carte.DEUX_BATON, Carte.TROIS_PIECE, Carte.CAVALIER_BATON, Carte.SIX_PIECE));

    Equipe equipeEsku = uneEquipeAvec(joueurEsku, unJoueur());
    Equipe equipeZaku = uneEquipeAvec(unJoueur(), joueurZaku);

    Joueur vainqueur = new Petit().meilleurParmi(new Opposants(equipeEsku, equipeZaku));

    assertThat(vainqueur).isEqualTo(joueurEsku);
  }

  @Test
  void devrait_faire_gagner_le_joueur_zaku_s_il_a_la_plus_petite_carte() {
    Joueur joueurEsku = unJoueurAvec(main(Carte.DEUX_PIECE, Carte.CINQ_PIECE, Carte.SEPT_BATON, Carte.SIX_COUPE));
    Joueur joueurZaku = unJoueurAvec(main(Carte.DEUX_BATON, Carte.TROIS_PIECE, Carte.CAVALIER_BATON, Carte.AS_PIECE));

    Equipe equipeEsku = uneEquipeAvec(joueurEsku, unJoueur());
    Equipe equipeZaku = uneEquipeAvec(unJoueur(), joueurZaku);

    Joueur vainqueur = new Petit().meilleurParmi(new Opposants(equipeEsku, equipeZaku));

    assertThat(vainqueur).isEqualTo(joueurZaku);
  }

  @Test
  void devrait_faire_gagner_le_joueur_qui_a_la_seconde_plus_petite_carte_si_la_premiere_est_egale() {
    Joueur joueurEsku = unJoueurAvec(main(Carte.AS_BATON, Carte.CINQ_PIECE, Carte.VALET_BATON, Carte.SIX_COUPE));
    Joueur joueurZaku = unJoueurAvec(main(Carte.DEUX_BATON, Carte.VALET_PIECE, Carte.QUATRE_BATON, Carte.AS_PIECE));

    Equipe equipeEsku = uneEquipeAvec(joueurEsku, unJoueur());
    Equipe equipeZaku = uneEquipeAvec(unJoueur(), joueurZaku);

    Joueur vainqueur = new Petit().meilleurParmi(new Opposants(equipeEsku, equipeZaku));

    assertThat(vainqueur).isEqualTo(joueurZaku);
  }

  @Test
  void devrait_faire_gagner_le_joueur_esku_si_les_deux_mains_sont_egales() {
    Joueur joueurEsku = unJoueurAvec(main(Carte.AS_BATON, Carte.QUATRE_PIECE, Carte.VALET_BATON, Carte.SIX_COUPE));
    Joueur joueurZaku = unJoueurAvec(main(Carte.VALET_PIECE, Carte.SIX_PIECE, Carte.QUATRE_BATON, Carte.AS_PIECE));

    Equipe equipeEsku = uneEquipeAvec(joueurEsku, unJoueur());
    Equipe equipeZaku = uneEquipeAvec(unJoueur(), joueurZaku);

    Joueur vainqueur = new Petit().meilleurParmi(new Opposants(equipeEsku, equipeZaku));

    assertThat(vainqueur).isEqualTo(joueurEsku);
  }
}