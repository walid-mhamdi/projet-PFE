package ouachousoft.BackEnd0;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import ouachousoft.BackEnd0.entity.Utilisateur;
import ouachousoft.BackEnd0.service.UtilisateurService;

import java.util.List;

@EnableScheduling
@SpringBootApplication

//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class BackEnd0Application implements CommandLineRunner {

	private final UtilisateurService utilisateurService;

	public BackEnd0Application(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}

	public static void main(String[] args) {
		SpringApplication.run(BackEnd0Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Fetch all users
		List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
		// Print each user's specified fields
		utilisateurs.forEach(utilisateur -> {
			System.out.println("ID: " + utilisateur.getId());
			System.out.println("Nom: " + utilisateur.getNom());
			System.out.println("Email: " + utilisateur.getEmail());
			System.out.println("Actif: " + utilisateur.isActif());
			System.out.println("Mot de Passe: " + utilisateur.getMdp());
			System.out.println("-------------------");
		});
	}

}
