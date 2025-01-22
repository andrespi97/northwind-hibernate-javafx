module aadd2.javafxtest {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;

    exports aadd2.javafxtest.model;
    opens aadd2.javafxtest.model to javafx.fxml;
    exports aadd2.javafxtest.view;
    opens aadd2.javafxtest.view to javafx.fxml;
    exports aadd2.javafxtest.controller;
    opens aadd2.javafxtest.controller to javafx.fxml;
}