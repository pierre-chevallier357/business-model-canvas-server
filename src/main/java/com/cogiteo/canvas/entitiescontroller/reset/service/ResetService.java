package com.cogiteo.canvas.entitiescontroller.reset.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cogiteo.canvas.entities.Reset;
import com.cogiteo.canvas.entities.Utilisateur;
import com.cogiteo.canvas.entitiescontroller.reset.repository.RepositoryReset;
import com.cogiteo.canvas.entitiescontroller.utilisateur.repository.RepositoryUtilisateur;

@Service
public class ResetService {
    @Autowired
    RepositoryReset repositoryReset;

    @Autowired
    RepositoryUtilisateur repositoryUtilisateur;

    public boolean IsExistedUser(String mail) {
        Utilisateur u = repositoryUtilisateur.findByMail(mail);

        return u == null ? false : true;
    }

    public boolean IsUniqueCode(String generateCode, BCryptPasswordEncoder bCryptPasswordEncoder) {
        for (Reset reset : repositoryReset.findAll()) {
            if (bCryptPasswordEncoder.matches(generateCode, reset.getCode()))
                return false;
        }
        return true;
    }

    public Reset create(Reset reset) {
        return repositoryReset.save(reset);
    }

    public void cleanDatabase() {
        repositoryReset.cleanDatabase();
    }

    public Reset findByMail(String mail) {
        return repositoryReset.findByMail(mail);
    }

    public Reset updateReset(Reset updateReset) {
        return repositoryReset.save(updateReset);
    }

    public void delete(Reset reset) {
        repositoryReset.delete(reset);
    }

    public Utilisateur getUserByMail(String mail) {
        return repositoryUtilisateur.findByMail(mail);
    }

    public Utilisateur updateUtilisateur(Utilisateur updateUser) {
        return repositoryUtilisateur.save(updateUser);
    }

}
