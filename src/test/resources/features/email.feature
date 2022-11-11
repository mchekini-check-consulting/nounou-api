Feature: Test de la fonctionnalité de mise à jour d'une nounou

  Scenario Outline:
    Given Une nounou avec le nom="<lastname>", le prenom="<firstname>", email="<email>" persistée dans la base de données
    When j'appelle l'api de modification "<updateNounouUrl>" d'une nounou
    And avec les paramètres: nom="<newLastname>", prenom="<newFirstname>" et email="<newEmail>"
    And et j'appelle l'api de consultation d'une nounou "<getNounouUrl>" avec l'email "<email>"
    Then je dois vérifier que nom="<newLastname>", prenom="<newFirstname>" et email="<email>"

    Examples:
      | lastname         | firstname  | email                      | updateNounouUrl  | newLastname | newFirstname | newEmail                  |
      | salah            | abderraouf | salah.abderraouf@gmail.com | /api/v1/nounous/ | LARABI      | Mohand       | larabi.mohand@gmail.com   |
      | touahria miliani | zakaria    | zakaria.ztm@gmail.com      | /api/v1/nounous/ | RAMDANI     | Mohamed      | ramdani.mohamed@gmail.com |

