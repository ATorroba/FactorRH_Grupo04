package es.upm.dit.isst.tfg.tfgwebapp.controller;

import java.util.stream.Collectors;


import java.io.Console;
import java.lang.reflect.Array;
import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import es.upm.dit.isst.tfg.tfgwebapp.model.Permisos;
import es.upm.dit.isst.tfg.tfgwebapp.model.Empleado;
import java.util.Arrays;

import java.util.List;

import java.util.Map;

import javax.validation.Constraint;
import javax.validation.Valid;
import javax.validation.executable.ValidateOnExecution;

import org.springframework.core.io.ByteArrayResource;

import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.client.HttpClientErrorException;

import org.springframework.web.client.RestTemplate;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PermisosController {

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("permisos/lista")
    public String lista(Model model, Principal principal) {
        List<Permisos> lista = new ArrayList<Permisos>();
        lista = Arrays.asList(restTemplate.getForEntity("http://localhost:8083/permisos/",
                Permisos[].class).getBody());
        model.addAttribute("Totpermisos", lista);

        model.addAttribute("date", ZonedDateTime.now());

        return "permisos";
    }
    
    
}