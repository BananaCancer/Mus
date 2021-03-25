package com.montaury.mus.jeu.tour.phases.dialogue;

import com.montaury.mus.jeu.Equipe;
import com.montaury.mus.jeu.joueur.Joueur;
import org.junit.jupiter.api.Test;

import static com.montaury.mus.jeu.joueur.Fixtures.unJoueur;
import static com.montaury.mus.jeu.joueur.Fixtures.uneEquipe;
import static org.assertj.core.api.Assertions.assertThat;

class DialogueTest {

  private final Equipe equipe1 = uneEquipe();
  private final Equipe equipe2 = uneEquipe();

  @Test
  void n_est_pas_termine_si_personne_n_a_parle() {
    Dialogue dialogue = new Dialogue();

    assertThat(dialogue.enCours()).isTrue();
  }

  @Test
  void n_est_pas_termine_si_tout_le_monde_n_a_pas_parle() {
    Dialogue dialogue = new Dialogue();
    dialogue.ajouter(new Paso(), equipe1.getJoueurUn());
    dialogue.ajouter(new Paso(), equipe2.getJoueurUn());
    dialogue.ajouter(new Paso(), equipe1.getJoueurDeux());

    assertThat(dialogue.enCours()).isTrue();
  }

  @Test
  void n_est_pas_termine_si_le_dernier_choix_est_imido() {
    Dialogue dialogue = new Dialogue();
    dialogue.ajouter(new Paso(), equipe1.getJoueurUn());
    dialogue.ajouter(new Paso(), equipe2.getJoueurUn());
    dialogue.ajouter(new Paso(), equipe1.getJoueurDeux());
    dialogue.ajouter(new Imido(), equipe2.getJoueurDeux());

    assertThat(dialogue.enCours()).isTrue();
  }

  @Test
  void n_est_pas_termine_si_le_dernier_choix_est_hordago() {
    Dialogue dialogue = new Dialogue();
    dialogue.ajouter(new Paso(), equipe1.getJoueurUn());
    dialogue.ajouter(new Paso(), equipe2.getJoueurUn());
    dialogue.ajouter(new Paso(), equipe1.getJoueurDeux());
    dialogue.ajouter(new Hordago(), equipe2.getJoueurDeux());

    assertThat(dialogue.enCours()).isTrue();
  }

  @Test
  void n_est_pas_termine_si_le_dernier_choix_est_gehiago() {
    Dialogue dialogue = new Dialogue();
    dialogue.ajouter(new Paso(), equipe1.getJoueurUn());
    dialogue.ajouter(new Paso(), equipe2.getJoueurUn());
    dialogue.ajouter(new Paso(), equipe1.getJoueurDeux());
    dialogue.ajouter(new Gehiago(2), equipe2.getJoueurDeux());

    assertThat(dialogue.enCours()).isTrue();
  }

  @Test
  void est_termine_si_tout_le_monde_est_paso() {
    Dialogue dialogue = new Dialogue();
    dialogue.ajouter(new Paso(), equipe1.getJoueurUn());
    dialogue.ajouter(new Paso(), equipe2.getJoueurUn());
    dialogue.ajouter(new Paso(), equipe1.getJoueurDeux());
    dialogue.ajouter(new Paso(), equipe2.getJoueurDeux());

    assertThat(dialogue.enCours()).isFalse();
  }

  @Test
  void est_termine_si_le_dernier_choix_est_tira() {
    Dialogue dialogue = new Dialogue();
    dialogue.ajouter(new Paso(), equipe1.getJoueurUn());
    dialogue.ajouter(new Imido(), equipe2.getJoueurUn());
    dialogue.ajouter(new Tira(), equipe1.getJoueurDeux());

    assertThat(dialogue.enCours()).isFalse();
  }

  @Test
  void est_termine_si_le_dernier_choix_est_idoki() {
    Dialogue dialogue = new Dialogue();
    dialogue.ajouter(new Paso(), equipe1.getJoueurUn());
    dialogue.ajouter(new Imido(), equipe2.getJoueurUn());
    dialogue.ajouter(new Idoki(), equipe1.getJoueurDeux());

    assertThat(dialogue.enCours()).isFalse();
  }

  @Test
  void est_termine_si_le_dernier_choix_est_kanta() {
    Dialogue dialogue = new Dialogue();
    dialogue.ajouter(new Paso(), equipe1.getJoueurUn());
    dialogue.ajouter(new Hordago(), equipe2.getJoueurUn());
    dialogue.ajouter(new Kanta(), equipe1.getJoueurDeux());

    assertThat(dialogue.enCours()).isFalse();
  }

}