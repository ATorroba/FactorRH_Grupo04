package es.upm.dit.isst.tfg.tfgwebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import java.time.ZonedDateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import es.upm.dit.isst.tfg.tfgwebapp.model.Departamento;
import es.upm.dit.isst.tfg.tfgwebapp.model.DepOrganigrama;

@Controller
public class DepartamentoController {

    public final String DEPARTAMENTOSMANAGER_STRING = "http://localhost:8083/departamentos/";
    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/organigrama")
    public String pintaOrganigrama(Model model) {
        Departamento[] depArray = restTemplate.getForObject(DEPARTAMENTOSMANAGER_STRING + "raiz/",
                Departamento[].class);
        Departamento raiz;
        if (depArray.length > 0) {
            raiz = depArray[0];
        } else {
            raiz = null;
        }
        DepOrganigrama departm = crearOrganigrama(raiz);
        model.addAttribute("raiz", departm);
        model.addAttribute("date", ZonedDateTime.now());

        return "organigrama";
    }

    private DepOrganigrama crearOrganigrama(Departamento departamento) {

        DepOrganigrama depart = new DepOrganigrama(departamento);
        List<Departamento> hijos = Arrays.asList(restTemplate
                .getForEntity(DEPARTAMENTOSMANAGER_STRING + "padre/" + departamento.getDepto(), Departamento[].class)
                .getBody());

        if (!hijos.isEmpty()) {
            for (Departamento hijo : hijos) {
                depart.setHijo(crearOrganigrama(hijo));
            }
        }

        return depart;
    }
}
