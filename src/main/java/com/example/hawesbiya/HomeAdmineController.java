package com.example.hawesbiya;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;

public class HomeAdmineController {
    @FXML
    private TabPane tabPane;

    private String role = "user"; // Supposons que vous avez déjà initialisé votre variable role

    @FXML
    public void initialize() {
        this.testAdmine();
    }
    private void testAdmine(){
        if (!role.equals("admine")) {
            // Masquer l'onglet "exemple 2" s'il ne s'agit pas d'un "admine"
            for (Tab tab : this.tabPane.getTabs()) {
                if (tab.getText().equals("exemple 2")) {
                    tab.setDisable(true); // Désactiver l'onglet pour le masquer
                    break; // Sortir de la boucle une fois l'onglet trouvé et désactivé
                }
            }
        }
    }
}
