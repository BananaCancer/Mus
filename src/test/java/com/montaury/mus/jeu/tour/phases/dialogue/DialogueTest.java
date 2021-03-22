package com.montaury.mus.jeu.tour.phases.dialogue;

import com.montaury.mus.jeu.joueur.Joueur;
import org.junit.jupiter.api.Test;

import static com.montaury.mus.jeu.joueur.Fixtures.unJoueur;
import static org.assertj.core.api.Assertions.assertThat;

class DialogueTest {

  private final Joueur joueur1 = unJoueur();
  private final Joueur joueur2 = unJoueur();
  private final Joueur joueur3 = unJoueur();

  @Test
  void n_est_pas_termine_si_personne_n_a_parle() {
    Dialogue dialogue = new Dialogue();

    assertThat(dialogue.enCours()).isTrue();
  }

  @Test
  void n_est_pas_termine_si_tout_le_monde_n_a_pas_parle() {
    Dialogue dialogue = new Dialogue();
    dialogue.ajouter(new Paso(), joueur1);
    //dialogue.ajouter(new Paso(), joueur2);

    assertThat(dialogue.enCours()).isTrue();
  }

  @Test
  void n_est_pas_termine_si_le_dernier_choix_est_imido() {
    Dialogue dialogue = new Dialogue();
    dialogue.ajouter(new Paso(), joueur1);
    dialogue.ajouter(new Imido(), joueur2);

    assertThat(dialogue.enCours()).isTrue();
  }

  @Test
  void n_est_pas_termine_si_le_dernier_choix_est_hordago() {
    Dialogue dialogue = new Dialogue();
    dialogue.ajouter(new Paso(), joueur1);
    dialogue.ajouter(new Hordago(), joueur2);

    assertThat(dialogue.enCours()).isTrue();
  }

  @Test
  void n_est_pas_termine_si_le_dernier_choix_est_gehiago() {
    Dialogue dialogue = new Dialogue();
    dialogue.ajouter(new Paso(), joueur1);
    dialogue.ajouter(new Gehiago(2), joueur2);

    assertThat(dialogue.enCours()).isTrue();
  }

  @Test
  void est_termine_si_tout_le_monde_est_paso() {
    Dialogue dialogue = new Dialogue();
    dialogue.ajouter(new Paso(), joueur1);
    dialogue.ajouter(new Paso(), joueur2);

    assertThat(dialogue.enCours()).isFalse();
  }

  @Test
  void est_termine_si_le_dernier_choix_est_tira() {
    Dialogue dialogue = new Dialogue();
    dialogue.ajouter(new Paso(), joueur1);
    dialogue.ajouter(new Imido(), joueur2);
    dialogue.ajouter(new Tira(), joueur1);

    assertThat(dialogue.enCours()).isFalse();
  }

  @Test
  void est_termine_si_le_dernier_choix_est_idoki() {
    Dialogue dialogue = new Dialogue();
    dialogue.ajouter(new Paso(), joueur1);
    dialogue.ajouter(new Imido(), joueur2);
    dialogue.ajouter(new Idoki(), joueur1);

    assertThat(dialogue.enCours()).isFalse();
  }

  @Test
  void est_termine_si_le_dernier_choix_est_kanta() {
    Dialogue dialogue = new Dialogue();
    dialogue.ajouter(new Paso(), joueur1);
    dialogue.ajouter(new Hordago(), joueur2);
    dialogue.ajouter(new Kanta(), joueur1);

    assertThat(dialogue.enCours()).isFalse();
  }

}