package com.cogiteo.canvas.excel.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cogiteo.canvas.excel.helper.ExcelAdministratifHelper;
import com.cogiteo.canvas.excel.repository.repositoryAdministratif.RepositoryConditions;
import com.cogiteo.canvas.excel.repository.repositoryAdministratif.RepositoryCookies;
import com.cogiteo.canvas.excel.repository.repositoryAdministratif.RepositoryMentions;
import com.cogiteo.canvas.excel.repository.repositoryAdministratif.RepositoryPolicy;
import com.cogiteo.canvas.excel.model.administratif.Conditions;
import com.cogiteo.canvas.excel.model.administratif.Cookies;
import com.cogiteo.canvas.excel.model.administratif.Mentions;
import com.cogiteo.canvas.excel.model.administratif.Policy;

@Service
public class ExcelAdministratifService extends ExcelService {

    @Autowired
    RepositoryConditions repositoryConditions;

    @Autowired
    RepositoryCookies repositoryCookies;

    @Autowired
    RepositoryMentions repositoryMentions;

    @Autowired
    RepositoryPolicy repositoryPolicy;

    public void saveAdministratif(MultipartFile file) throws JSONException {
        try {
            long newVersionConditions = repositoryConditions.max() + 1;
            long newVersionCookies = repositoryCookies.max() + 1;
            long newVersionMentions = repositoryMentions.max() + 1;
            long newVersionPolicy = repositoryPolicy.max() + 1;

            List<Conditions> ConditionsItems = ExcelAdministratifHelper.excelToConditions(file.getInputStream(),
                    newVersionConditions);
            List<Cookies> CookiesItems = ExcelAdministratifHelper.excelToCookies(file.getInputStream(),
                    newVersionCookies);
            List<Mentions> MentionsItems = ExcelAdministratifHelper.excelToMentions(file.getInputStream(),
                    newVersionMentions);
            List<Policy> PolicyItems = ExcelAdministratifHelper.excelToPolicy(file.getInputStream(), newVersionPolicy);
            repositoryConditions.saveAll(ConditionsItems);
            repositoryCookies.saveAll(CookiesItems);
            repositoryMentions.saveAll(MentionsItems);
            repositoryPolicy.saveAll(PolicyItems);

            // CONDITION
            Conditions conditions = new Conditions();

            // recup last version
            List<Conditions> allConditions = getLastVersionConditions();

            // upload & construit json
            uploadFile("www/assets/i18n/conditions/", "conditions-fr.json", conditions.getJSON("FR", allConditions),
                    false);
            uploadFile("www/assets/i18n/conditions/", "conditions-en.json", conditions.getJSON("EN", allConditions),
                    false);

            // // COOKIES
            Cookies cookies = new Cookies();

            // recup last version
            List<Cookies> allCookies = getLastVersionCookies();

            // upload & construit json
            uploadFile("www/assets/i18n/cookies/", "cookies-fr.json", cookies.getJSON("FR", allCookies), false);
            uploadFile("www/assets/i18n/cookies/", "cookies-en.json", cookies.getJSON("EN", allCookies), false);

            // MENTIONS
            Mentions mentions = new Mentions();

            // recup last version
            List<Mentions> allMentions = getLastVersionMentions();

            // upload & construit json
            uploadFile("www/assets/i18n/mentions/", "mentions-fr.json", mentions.getJSON("FR", allMentions), false);
            uploadFile("www/assets/i18n/mentions/", "mentions-en.json", mentions.getJSON("EN", allMentions), false);

            // POLICY
            Policy policy = new Policy();

            // recup last version
            List<Policy> allPolicy = getLastVersionPolicy();

            // upload & construit json
            uploadFile("www/assets/i18n/policy/", "policy-fr.json", policy.getJSON("FR", allPolicy), false);
            uploadFile("www/assets/i18n/policy/", "policy-en.json", policy.getJSON("EN", allPolicy), false);

        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    private List<Policy> getLastVersionPolicy() {
        long version = repositoryPolicy.max();
        return repositoryPolicy.findByVersion(version);
    }

    private List<Mentions> getLastVersionMentions() {
        long version = repositoryMentions.max();
        return repositoryMentions.findByVersion(version);
    }

    private List<Cookies> getLastVersionCookies() {
        long version = repositoryCookies.max();
        return repositoryCookies.findByVersion(version);
    }

    private List<Conditions> getLastVersionConditions() {
        long version = repositoryConditions.max();
        return repositoryConditions.findByVersion(version);
    }

    public ByteArrayInputStream loadAdministratif() {
        List<Conditions> allConditions = getLastVersionConditions();
        List<Cookies> allCookies = getLastVersionCookies();
        List<Mentions> allMentions = getLastVersionMentions();
        List<Policy> allPolicy = getLastVersionPolicy();

        ByteArrayInputStream in = ExcelAdministratifHelper.administratifToExcel(allConditions, allCookies, allMentions,
                allPolicy);
        return in;
    }
}
