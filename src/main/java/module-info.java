module com.team10.fp_a2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires jakarta.persistence;

    opens com.team10.fp_a2 to javafx.fxml;
//    exports com.team10.fp_a2;
//    exports com.team10.fp_a2.data.model.person;
//    exports com.team10.fp_a2.data.model.property;
//    exports com.team10.fp_a2.data.model.others;
    // Open entity packages to EclipseLink for reflection
    opens com.team10.fp_a2.data.model.person to org.eclipse.persistence.core, org.eclipse.persistence.jpa;
    opens com.team10.fp_a2.data.model.others to org.eclipse.persistence.core, org.eclipse.persistence.jpa;
    opens com.team10.fp_a2.data.model.property to org.eclipse.persistence.core, org.eclipse.persistence.jpa;
}