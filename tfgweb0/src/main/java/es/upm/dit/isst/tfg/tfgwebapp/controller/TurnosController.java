package es.upm.dit.isst.tfg.tfgwebapp.controller;
import java.security.Principal;
import es.upm.dit.isst.tfg.tfgwebapp.model.Turnos;
import es.upm.dit.isst.tfg.tfgwebapp.model.Empleado;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Arrays;


import java.util.List;
import org.springframework.web.client.RestTemplate;

@Controller
public class TurnosController {

    @GetMapping("/turnos")
    public String getTurnosPorIdEmpleado(Principal principal, Model model) {
        // RestTemplate restTemplate1 = new RestTemplate();
        // Empleado empleadoLogueado = new Empleado();
        // String idEmpLog = "";
        // Empleado[] empleados = restTemplate1.getForObject("http://localhost:8083/empleados/", Empleado[].class);
        // for(int i=0;i<empleados.length;i++){
        //     if(empleados[i].getEmail() == principal.getName()){
        //      empleadoLogueado = empleados[i];
        //      idEmpLog = empleadoLogueado.getIdEmpleado();
        //     }

        // }
        try {
            System.out.println(principal.getName());
            RestTemplate restTemplate1 = new RestTemplate();

            Empleado empleado2 = restTemplate1.getForObject("http://localhost:8083/datos/"

                    + principal.getName(), Empleado.class);
            String idEmpLog = empleado2.getIdEmpleado();

            if (empleado2 != null) {
                String url = "http://localhost:8083/turnos/" + idEmpLog;
                System.out.println(idEmpLog);
                RestTemplate restTemplate = new RestTemplate();
                Turnos[] turnosArray = restTemplate.getForObject(url, Turnos[].class);
                List<Turnos> turnos = Arrays.asList(turnosArray);
                model.addAttribute("turno", turnos);
                return "turnos";
            }
        } catch (Exception e) {
            return "401";

        }
        return "401";
    }
}
