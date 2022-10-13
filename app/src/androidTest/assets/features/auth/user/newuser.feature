@user
Feature: Nuevo usuario
  Scenario: Crear un nuevo usuario
    Given Se inicia la aplicacion
    And Me muestra la pantalla inicial LoginActivity
    When Hago click en el boton nuevo usuario
    And Veo formulario de nuevo usuario
    And Lleno el formulario user "user38@gmail.com" password "123456"
    When Hago click en el boton guardar
    Then Me muestra un snackbar con la confirmacion del usuario guardado
    And Me regresa a la pantalla inicial
