Feature: Je veux tester l'addition de deux nombres

  Scenario Outline:
    Given j'ai un entier <a> en entrée et un entier <b> en entrée
    When j'addtitionne les deux nombres en entrée
    Then le résultat est égal à <resultat>

    Examples:
      | a  | b   | resultat |
      | 10 | 15  | 25       |
      | 35 | -15 | 20       |