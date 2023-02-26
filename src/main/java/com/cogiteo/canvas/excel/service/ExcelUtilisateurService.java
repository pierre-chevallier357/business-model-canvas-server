package com.cogiteo.canvas.excel.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cogiteo.canvas.excel.helper.ExcelUtilisateurHelper;
import com.cogiteo.canvas.excel.model.utilisateur.Connexion;
import com.cogiteo.canvas.excel.model.utilisateur.Inscription;
import com.cogiteo.canvas.excel.model.utilisateur.MdpOublie;
import com.cogiteo.canvas.excel.model.utilisateur.Profil;
import com.cogiteo.canvas.excel.repository.repositoryUtilisateur.RepositoryUtilisateurConnexion;
import com.cogiteo.canvas.excel.repository.repositoryUtilisateur.RepositoryUtilisateurInscription;
import com.cogiteo.canvas.excel.repository.repositoryUtilisateur.RepositoryUtilisateurMdpOublie;
import com.cogiteo.canvas.excel.repository.repositoryUtilisateur.RepositoryUtilisateurProfil;

@Service
public class ExcelUtilisateurService extends ExcelService {
    @Autowired
    RepositoryUtilisateurConnexion repositoryUtilisateurConnexion;

    @Autowired
    RepositoryUtilisateurInscription repositoryUtilisateurInscription;

    @Autowired
    RepositoryUtilisateurMdpOublie repositoryUtilisateurMdpOublie;

    @Autowired
    RepositoryUtilisateurProfil repositoryUtilisateurProfil;

    public void saveUtilisateur(MultipartFile file) throws JSONException {
        try {
            long newVersionConnexion = repositoryUtilisateurConnexion.max() + 1;
            long newVersionInscription = repositoryUtilisateurInscription.max() + 1;
            long newVersionMdpOublie = repositoryUtilisateurMdpOublie.max() + 1;
            long newVersionProfil = repositoryUtilisateurProfil.max() + 1;

            List<Connexion> connexionItems = ExcelUtilisateurHelper.excelToConnexion(file.getInputStream(),
                    newVersionConnexion);
            List<Inscription> inscriptionItems = ExcelUtilisateurHelper.excelToInscription(file.getInputStream(),
                    newVersionInscription);
            List<MdpOublie> mdpoublieItems = ExcelUtilisateurHelper.excelToMdpOublie(file.getInputStream(),
                    newVersionMdpOublie);
            List<Profil> profilItems = ExcelUtilisateurHelper.excelToProfil(file.getInputStream(), newVersionProfil);
            repositoryUtilisateurConnexion.saveAll(connexionItems);
            repositoryUtilisateurInscription.saveAll(inscriptionItems);
            repositoryUtilisateurMdpOublie.saveAll(mdpoublieItems);
            repositoryUtilisateurProfil.saveAll(profilItems);

            // CONNEXION
            Connexion connexion = new Connexion();

            // recup last version
            List<Connexion> allConnexion = getLastVersionConnexion();

            // upload & construit json
            uploadFile("www/assets/i18n/connexion/", "connexion-fr.json", connexion.getJSON("FR", allConnexion),
                    false);
            uploadFile("www/assets/i18n/connexion/", "connexion-en.json", connexion.getJSON("EN", allConnexion),
                    false);

            // INSCRIPTION
            Inscription inscription = new Inscription();

            // recup last version
            List<Inscription> allInscription = getLastVersionInscription();

            // upload & construit json
            uploadFile("www/assets/i18n/inscription/", "inscription-fr.json", inscription.getJSON("FR", allInscription),
                    false);
            uploadFile("www/assets/i18n/inscription/", "inscription-en.json", inscription.getJSON("EN", allInscription),
                    false);

            // MDPOUBLIE
            MdpOublie mdpOublie = new MdpOublie();

            // recup last version
            List<MdpOublie> allMdpOublie = getLastVersionMdpOublie();

            // upload & construit json
            uploadFile("www/assets/i18n/mdpoublie/", "mdpoublie-fr.json", mdpOublie.getJSON("FR", allMdpOublie),
                    false);
            uploadFile("www/assets/i18n/mdpoublie/", "mdpoublie-en.json", mdpOublie.getJSON("EN", allMdpOublie),
                    false);

            // PROFIL
            Profil profil = new Profil();

            // recup last version
            List<Profil> allProfil = getLastVersionProfil();

            // upload & construit json
            uploadFile("www/assets/i18n/profil/", "profil-fr.json", profil.getJSON("FR", allProfil),
                    false);
            uploadFile("www/assets/i18n/profil/", "profil-en.json", profil.getJSON("EN", allProfil),
                    false);

        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    private List<Profil> getLastVersionProfil() {
        long version = repositoryUtilisateurProfil.max();
        return repositoryUtilisateurProfil.findByVersion(version);
    }

    private List<MdpOublie> getLastVersionMdpOublie() {
        long version = repositoryUtilisateurMdpOublie.max();
        return repositoryUtilisateurMdpOublie.findByVersion(version);
    }

    private List<Inscription> getLastVersionInscription() {
        long version = repositoryUtilisateurInscription.max();
        return repositoryUtilisateurInscription.findByVersion(version);
    }

    private List<Connexion> getLastVersionConnexion() {
        long version = repositoryUtilisateurConnexion.max();
        return repositoryUtilisateurConnexion.findByVersion(version);
    }

    public InputStream loadUtilisateur() {
        List<Connexion> allConnexion = getLastVersionConnexion();
        List<Inscription> allInscription = getLastVersionInscription();
        List<MdpOublie> allMdpOublie = getLastVersionMdpOublie();
        List<Profil> allProfil = getLastVersionProfil();

        ByteArrayInputStream in = ExcelUtilisateurHelper.utilisateurToExcel(allConnexion, allInscription, allMdpOublie,
                allProfil);
        return in;

    }

}
