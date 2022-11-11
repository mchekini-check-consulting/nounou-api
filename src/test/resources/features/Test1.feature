Feature: Je veux additionner deux nombres

  Scenario Outline:
    Given J'ai un nombre <a> et un nombre <b>
    When j'additionne les deux nombres a et b
    Then le resultat attendu est <resultat_attendu>

    Examples:

      | a   | b    | resultat_attendu |
      | 5   | 10   | 15               |
      | 100 | 300  | 400              |
      | -2  | 15   | 13               |
      | -2  | -190 | -192             |
