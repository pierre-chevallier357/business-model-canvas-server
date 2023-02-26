package com.cogiteo.canvas.entitiescontroller.utilisateur.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cogiteo.canvas.entities.Utilisateur;
import com.cogiteo.canvas.entitiescontroller.utilisateur.dto.ConnectionDto;
import com.cogiteo.canvas.entitiescontroller.utilisateur.repository.RepositoryUtilisateur;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UtilisateurService {
  @Autowired
  RepositoryUtilisateur repository;

  public boolean IsExistedUser(String mail) {
    Utilisateur u = repository.findByMail(mail);

    return u == null ? false : true;
  }

  public Utilisateur getUserByMail(String mail) {
    return repository.findByMail(mail);
  }

  public Utilisateur getUserByGUIDtilisateur(Long id) {
    return repository.findByGUIDUtilisateur(id);
  }

  public Utilisateur update(Utilisateur updateUser) {
    return repository.save(updateUser);
  }

  public Utilisateur register(Utilisateur utilisateur) {
    return repository.save(utilisateur);
  }

  public boolean CanDelete(long idUser) {

    boolean u = repository.canDelete(idUser) == 0 ? true : false;

    return u;
  }

  public boolean IsNotUniqueToken(String token) {

    boolean u = repository.IsNotUniqueToken(token) != 0 ? true : false;

    return u;
  }

  public boolean IsExistedUser(Long guidUtilisateur) {
    Utilisateur u = repository.findByGUIDUtilisateur(guidUtilisateur);

    return u == null ? false : true;
  }

  public void Delete(Long guidUtilisateur) {
    repository.deleteByGUIDUtilisateur(guidUtilisateur);
  }

  private String secret = "${jwt.secret}";

  public String generateToken(ConnectionDto user) {
    Map<String, Object> claims = new HashMap<>();
    return Jwts.builder().setClaims(claims).setSubject(user.getMail()).setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 360 * 1000)).signWith(SignatureAlgorithm.HS512, secret)
        .compact();

  }

  private static final SecureRandom secureRandom = new SecureRandom(); // threadsafe
  private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); // threadsafe

  public static String generateNewToken() {
    byte[] randomBytes = new byte[24];
    secureRandom.nextBytes(randomBytes);
    return base64Encoder.encodeToString(randomBytes);
  }

  public List<Object> filterUtilisateur(String nom, String prenom, String mail, String entreprise, Boolean admin,
      String sortColumn, String sortWay, Integer page) {
    List<Utilisateur> listFilter = new ArrayList<Utilisateur>();
    List<Utilisateur> listInitial = repository.filterUtilisateur(nom, prenom, mail, entreprise);
    List<Object> arrayList = new ArrayList<Object>();

    if (listInitial.size() != 0) {

      if (admin != null) {
        for (Utilisateur utilisateur : listInitial) {
          if (utilisateur.isEstAdmin() == admin) {
            utilisateur.setColumnToCompare(sortColumn);
            listFilter.add(utilisateur);
          }
        }
      } else {
        for (Utilisateur utilisateur : listInitial) {
          utilisateur.setColumnToCompare(sortColumn);
          listFilter.add(utilisateur);
        }
      }

      if (sortWay.equals("ASC")) {
        Collections.sort(listFilter);
      } else {
        Collections.sort(listFilter, Collections.reverseOrder());
      }

      int nbElement = 25;
      int start = 0;
      int end = page * nbElement;

      arrayList.add(listFilter.size());
      arrayList.add((int) Math.ceil((double) listFilter.size() / nbElement));

      if (page == arrayList.get(1)) {
        end = ((page - 1) * nbElement) + (listFilter.size() - ((page - 1) * nbElement));
      }

      if (page != 1) {
        start = ((page - 1) * nbElement);
      }

      arrayList.add(listFilter.subList(start, end));

    } else {
      arrayList.add(0);
      arrayList.add(0);
      arrayList.add(listInitial);
    }
    return arrayList;
  }

  public boolean IsLastAdmin(Long guidUtilisateur) {
    int count = repository.countAdmin();

    return count > 1 ? false : true;
  }

  public Utilisateur getUserByToken(String token) {
    return repository.findByToken(token);
  }

  public boolean IsAdmin(String tokenAdmin) {

    Boolean userAdmin = repository.IsAdmin(tokenAdmin);

    return userAdmin != null && userAdmin == true;
  }
}