package es.upm.dit.isst.tfg.tfgwebapp.controller;

import java.security.Principal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import es.upm.dit.isst.tfg.tfgwebapp.model.Empleado;
import es.upm.dit.isst.tfg.tfgwebapp.model.Rol;
import es.upm.dit.isst.tfg.tfgwebapp.model.RolPK;

@Controller
public class RolController {
    public final String RHMANAGERGER_STRING = "http://localhost:8083/roles/";

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/roles")
    public String roles(Principal principal, Model model) {
        List<Rol> listaemp = new ArrayList<Rol>();
        List<Rol> listarec = new ArrayList<Rol>();
        List<Rol> listacon = new ArrayList<Rol>();
        List<Rol> listahab = new ArrayList<Rol>();

        listaemp = Arrays.asList(restTemplate.getForEntity(RHMANAGERGER_STRING + "EMP",
                Rol[].class).getBody());
        listarec = Arrays.asList(restTemplate.getForEntity(RHMANAGERGER_STRING + "REC",
                Rol[].class).getBody());
        listacon = Arrays.asList(restTemplate.getForEntity(RHMANAGERGER_STRING + "CON",
                Rol[].class).getBody());
        listahab = Arrays.asList(restTemplate.getForEntity(RHMANAGERGER_STRING + "HAB",
                Rol[].class).getBody());

        model.addAttribute("listaemp", listaemp);
        model.addAttribute("listarec", listarec);

        model.addAttribute("listacon", listacon);

        model.addAttribute("listahab", listahab);

        return "lista_roles";

    }

    @GetMapping("/eliminarrol/{idrol}/{idempleado}")
    public String eliminar(@PathVariable(value = "idrol") String idrol,
            @PathVariable(value = "idempleado") String idempleado) {
        System.out.println(idrol + idempleado);
        System.out.println(RHMANAGERGER_STRING + idrol + "/" + idempleado);
        restTemplate.delete(RHMANAGERGER_STRING + idrol + "/" + idempleado);

        return "redirect:/roles";

    }

    @GetMapping("/crearrol/{idrol}")

    public String crear(@PathVariable(value = "idrol") String idrol, Map<String, Object> model) {
        List<Empleado> lista = new ArrayList<Empleado>();

        lista = Arrays.asList(restTemplate.getForEntity("http://localhost:8083/empleados/",

                Empleado[].class).getBody());
        RolPK Rol = new RolPK();
        model.put("Rol", Rol);
        model.put("idrol", idrol);
        model.put("empleados", lista);
        model.put("date", ZonedDateTime.now());
        return "form_roles";

    }

    @PostMapping("/guardarrol")

    public String guardar(@Validated RolPK Rol, BindingResult result, Model model) {
        Rol Rol2 = new Rol();
        if (result.hasErrors()) {
            model.addAttribute("Rol", Rol);
            List<ObjectError> r = result.getAllErrors();
            // r.stream().map(a -> a);

            model.addAttribute("result", r);// .getDefaultMessage());

            model.addAttribute("date", ZonedDateTime.now());

            // 0 System.out.println(r);
            // System.out.println(r.get(0));

            return "form_roles";

        } else {
            try {
                Rol2.setClave(Rol);
                Rol2.setActivo("1");
                restTemplate.postForObject(RHMANAGERGER_STRING, Rol2, Rol.class);

            } catch (Exception e) {

            }

            return "redirect:/roles";
        }
    }

}
