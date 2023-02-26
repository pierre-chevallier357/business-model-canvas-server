package com.cogiteo.canvas.captcha;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.util.MultiValueMap;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/captcha")
public class CaptchaController {

    @GetMapping("/verifier")
    public ResponseEntity<String> VerifierCaptcha(@RequestParam(required = true) String response) {

        try {

            String apiUrl = "https://www.google.com/recaptcha/api/siteverify";
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            map.add("secret", "6Ld5nl4kAAAAAHJgo-1rAFeujkuBjP6IRxw-E96A");
            map.add("response", response);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map,
                    headers);

            ResponseEntity<String> requestResponse = restTemplate.postForEntity(apiUrl, request, String.class);
            String responseToSend = requestResponse.toString().substring(11, 30);

            if (responseToSend.equals("{\n  \"success\": true")) {
                responseToSend = responseToSend + "}";
            } else {
                responseToSend = responseToSend + "e}";
            }

            return new ResponseEntity<>(responseToSend, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
