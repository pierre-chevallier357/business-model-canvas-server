package com.cogiteo.canvas.entitiescontroller.canvas.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cogiteo.canvas.entitiescontroller.canvas.object.Canvas;
import com.cogiteo.canvas.entitiescontroller.canvas.service.CanvasService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/canvas")
public class CanvasController {

  @Autowired
  CanvasService canvasService;

  @GetMapping("/all")
  public ResponseEntity<List<Object>> getAllCanvas(@RequestParam(required = true) String tokenAdmin,
      @RequestParam(required = false, defaultValue = "") String nom,
      @RequestParam(required = false, defaultValue = "") String statut,
      @RequestParam(required = false, defaultValue = "") String version,
      @RequestParam(required = false, defaultValue = "") String nomUser,
      @RequestParam(required = false, defaultValue = "") String prenomUser,
      @RequestParam(required = false, defaultValue = "") String entreprise,
      @RequestParam(required = false, defaultValue = "") String type,
      @RequestParam(required = false, defaultValue = "2020-01-01") String dateStart,
      @RequestParam(required = false, defaultValue = "2050-01-01") String dateEnd,
      @RequestParam(required = false, defaultValue = "guidcanvas") String sortColumn,
      @RequestParam(required = false, defaultValue = "desc") String sortWay,
      @RequestParam(required = false, defaultValue = "1") Integer page) {

    try {

      if (!canvasService.isAdmin(tokenAdmin)) {
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
      }
      List<Object> filterResult = new ArrayList<Object>();
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

      Calendar startCalendar = Calendar.getInstance();
      Calendar endCalendar = Calendar.getInstance();
      startCalendar.setTime(formatter.parse(dateStart));
      endCalendar.setTime(formatter.parse(dateEnd));

      canvasService.filterCanvas(nom, statut, version, nomUser, prenomUser, entreprise, type, startCalendar,
          endCalendar, sortColumn, sortWay.toUpperCase(), page, filterResult);

      List<Canvas> lCanvas = (List<Canvas>) filterResult.get(2);

      if (lCanvas.isEmpty()) {
        return new ResponseEntity<>(filterResult, HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(filterResult, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

}
