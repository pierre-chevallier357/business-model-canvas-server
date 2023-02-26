package com.cogiteo.canvas.excel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import com.cogiteo.canvas.excel.helper.ExcelHelper;
import com.cogiteo.canvas.excel.message.ResponseMessage;
import com.cogiteo.canvas.excel.service.ExcelAccueilService;
import com.cogiteo.canvas.excel.service.ExcelAdministratifService;
import com.cogiteo.canvas.excel.service.ExcelCanvasService;
import com.cogiteo.canvas.excel.service.ExcelFAQService;
import com.cogiteo.canvas.excel.service.ExcelMessageService;
import com.cogiteo.canvas.excel.service.ExcelStructureService;
import com.cogiteo.canvas.excel.service.ExcelUtilisateurService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/excel")
public class ExcelController {

  @Autowired
  ExcelFAQService fileFAQService;

  @Autowired
  ExcelCanvasService fileServiceCanvas;

  @Autowired
  ExcelAdministratifService fileServiceAdministratif;

  @Autowired
  ExcelAccueilService fileServiceAccueil;

  @Autowired
  ExcelStructureService fileServiceStructure;

  @Autowired
  ExcelUtilisateurService fileServiceUtilisateur;

  @Autowired
  ExcelMessageService fileServiceMessage;

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file,
      @RequestParam(required = true) String adminToken) {
    if (!fileFAQService.isAdmin(adminToken)) {
      return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    String message = "";
    boolean versionInsert = true;

    if (ExcelHelper.hasExcelFormat(file)) {
      try {
        String fileName = file.getOriginalFilename() == null ? "" : file.getOriginalFilename().split("-")[0];

        switch (fileName) {
          case "FAQ":
            fileFAQService.saveFAQ(file);
            break;
          case "CASE":
            versionInsert = fileServiceCanvas.saveCase(file);
            break;
          case "ADMINISTRATIF":
            fileServiceAdministratif.saveAdministratif(file);
            break;
          case "ACCUEIL":
            fileServiceAccueil.saveAccueil(file);
            break;
          case "STRUCTURE":
            fileServiceStructure.saveStructure(file);
            break;
          case "UTILISATEUR":
            fileServiceUtilisateur.saveUtilisateur(file);
            break;
          case "MESSAGE":
            fileServiceMessage.saveMessage(file);
            break;
          default:
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Nom de fichier inconnu."));
        }

        if (!versionInsert) {
          return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage("Cette version existe déjà."));
        }

        message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
      } catch (Exception e) {
        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
      }
    }

    message = "Please upload an excel file!";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
  }

  @GetMapping("/download/FAQ")
  public ResponseEntity<Resource> getFileFAQ(@RequestParam(required = true) String adminToken) {
    if (!fileFAQService.isAdmin(adminToken)) {
      return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    String filename = "FAQ-CANVAS.xlsx";
    InputStreamResource file = new InputStreamResource(fileFAQService.loadFAQ());

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
        .body(file);
  }

  @GetMapping("/download/canvas")
  public ResponseEntity<Resource> getFileCaseIndividuel(@RequestParam(required = true) String adminToken) {
    if (!fileServiceCanvas.isAdmin(adminToken)) {
      return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    String filename = "CASE-CANVAS.xlsx";
    InputStreamResource file = new InputStreamResource(fileServiceCanvas.loadCaseIndividuel());

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
        .body(file);
  }

  @GetMapping("/canvas/lastversion")
  public ResponseEntity<String> getLastVersionCanvas() {
    String lastVersion = fileServiceCanvas.getLastVersionCanvas();
    return new ResponseEntity<>(lastVersion, HttpStatus.OK);
  }

  @GetMapping("/download/administratif")
  public ResponseEntity<Resource> getFileAdministratif(@RequestParam(required = true) String adminToken) {
    if (!fileFAQService.isAdmin(adminToken)) {
      return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    String filename = "ADMINISTRATIF-CANVAS.xlsx";
    InputStreamResource file = new InputStreamResource(fileServiceAdministratif.loadAdministratif());

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
        .body(file);
  }

  @GetMapping("/download/accueil")
  public ResponseEntity<Resource> getFileAccueil(@RequestParam(required = true) String adminToken) {
    if (!fileFAQService.isAdmin(adminToken)) {
      return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    String filename = "ACCUEIL-CANVAS.xlsx";
    InputStreamResource file = new InputStreamResource(fileServiceAccueil.loadAccueil());

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
        .body(file);
  }

  @GetMapping("/download/structure")
  public ResponseEntity<Resource> getFileStructure(@RequestParam(required = true) String adminToken) {
    if (!fileFAQService.isAdmin(adminToken)) {
      return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    String filename = "STRUCTURE-CANVAS.xlsx";
    InputStreamResource file = new InputStreamResource(fileServiceStructure.loadStructure());

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
        .body(file);
  }

  @GetMapping("/download/utilisateur")
  public ResponseEntity<Resource> getFileUtilisateur(@RequestParam(required = true) String adminToken) {
    if (!fileFAQService.isAdmin(adminToken)) {
      return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    String filename = "UTILISATEUR-CANVAS.xlsx";
    InputStreamResource file = new InputStreamResource(fileServiceUtilisateur.loadUtilisateur());

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
        .body(file);
  }

  @GetMapping("/download/message")
  public ResponseEntity<Resource> getFileMessage(@RequestParam(required = true) String adminToken) {
    if (!fileFAQService.isAdmin(adminToken)) {
      return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    String filename = "MESSAGE-CANVAS.xlsx";
    InputStreamResource file = new InputStreamResource(fileServiceMessage.loadMessage());

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
        .body(file);
  }
}