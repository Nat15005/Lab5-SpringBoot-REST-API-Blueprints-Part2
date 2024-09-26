package edu.eci.arsw.blueprints.ui;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices blueprintService = ac.getBean(BlueprintsServices.class);

        Point[] points1 = new Point[]{new Point(10, 10), new Point(20, 20), new Point(20, 20), new Point(30, 30)};
        Blueprint blueprint1 = new Blueprint("author1", "blueprint1", points1);

        Point[] points2 = new Point[]{new Point(30, 30), new Point(40, 40)};
        Blueprint blueprint2 = new Blueprint("author2", "blueprint2", points2);

        Point[] points3 = new Point[]{new Point(50, 50), new Point(60, 60), new Point(60, 60), new Point(70, 70)};
        Blueprint blueprint3 = new Blueprint("author1", "blueprint3", points3);

        try {
            System.out.println("Registrando planos...");
            blueprintService.addNewBlueprint(blueprint1);
            blueprintService.addNewBlueprint(blueprint2);
            blueprintService.addNewBlueprint(blueprint3);
            System.out.println("Planos registrados con éxito.\n");

            System.out.println("Consultando blueprint1 de author1:");
            Blueprint retrievedBlueprint = blueprintService.getBlueprint("author1", "blueprint1");
            System.out.println(retrievedBlueprint);

            System.out.println("\nConsultando todos los planos de author1:");
            Set<Blueprint> blueprints = blueprintService.getBlueprintsByAuthor("author1");
            for (Blueprint blueprint : blueprints) {
                System.out.println(blueprint);
            }

            Point[] points4 = new Point[]{new Point(70, 70), new Point(80, 80)};
            Blueprint blueprint4 = new Blueprint("author1", "blueprint4", points4);
            System.out.println("\nRegistrando un nuevo plano específico (blueprint4) para author1...");
            blueprintService.addNewBlueprint(blueprint4);
            System.out.println("Nuevo plano registrado: " + blueprint4);

            System.out.println("\nConsultando nuevamente todos los planos de author1:");
            Set<Blueprint> updatedBlueprints = blueprintService.getBlueprintsByAuthor("author1");
            for (Blueprint blueprint : updatedBlueprints) {
                System.out.println(blueprint);
            }


            Point[] pointsWithRedundancy = new Point[]{new Point(10, 10), new Point(20, 20), new Point(20, 20), new Point(30, 30)};
            Blueprint blueprintWithRedundancy = new Blueprint("author1", "redundantBlueprint", pointsWithRedundancy);
            blueprintService.addNewBlueprint(blueprintWithRedundancy);


            Point[] pointsForSubsampling = new Point[]{new Point(1, 1), new Point(2, 2), new Point(3, 3), new Point(4, 4), new Point(5, 5)};
            Blueprint blueprintForSubsampling = new Blueprint("author2", "subsamplingBlueprint", pointsForSubsampling);
            blueprintService.addNewBlueprint(blueprintForSubsampling);


            System.out.println("\nAplicando filtro al blueprint");
            Blueprint filteredRedundantBlueprint = blueprintService.getBlueprint("author1", "redundantBlueprint");
            System.out.println(filteredRedundantBlueprint);


            System.out.println("\nAplicando filtro al blueprint");
            Blueprint filteredSubsamplingBlueprint = blueprintService.getBlueprint("author2", "subsamplingBlueprint");
            System.out.println(filteredSubsamplingBlueprint);

        } catch (BlueprintNotFoundException e) {
            System.err.println("Ocurrió un error: " + e.getMessage());
        } catch (BlueprintPersistenceException e) {
            throw new RuntimeException(e);
        }
    }
}
