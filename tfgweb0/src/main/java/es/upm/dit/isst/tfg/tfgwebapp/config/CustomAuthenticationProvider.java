
package es.upm.dit.isst.tfg.tfgwebapp.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.ExemptionMechanismException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import es.upm.dit.isst.tfg.tfgwebapp.model.Empleado;
import es.upm.dit.isst.tfg.tfgwebapp.model.Rol;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    /*
     * public final String RHMANAGERGER_STRING = env.getProperty
     * ("RHMANAGERger.server") + "tfgs/";
     * "http://localhost:8083/empleados/";
     */
    public final String RHMANAGERGER_STRING = "http://localhost:8083/empleados/";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        try {

            String name = authentication.getPrincipal().toString();
            String pass = authentication.getCredentials().toString(); // getName();
            Empleado empleado2 = restTemplate.getForObject("http://localhost:8083/datos/" + name, Empleado.class);
            if (empleado2 == null) {
                throw new UsernameNotFoundException("user not found");
            }

            System.out.println(empleado2.getIdEmpleado());
            List<Rol> rol1 = Arrays.asList(
                    restTemplate.getForEntity("http://localhost:8083/roles/empleado/" + empleado2.getIdEmpleado(),
                            Rol[].class).getBody());
            System.out.println(empleado2.getIdEmpleado());
            System.out.println(empleado2.getEmail());
            System.out.println(empleado2.getPassword());
            System.out.println(authentication.getPrincipal().toString());
            System.out.println(authentication.getCredentials().toString());

            if (!(authentication.getCredentials().equals(empleado2.getPassword()))) {
                throw new UsernameNotFoundException("wrong password");

            }
            List<SimpleGrantedAuthority> ga = new ArrayList<SimpleGrantedAuthority>();

            for (Rol r : rol1) {
                if (r.getClave().getIdrol().equals("EMP")) {
                    ga.add(new SimpleGrantedAuthority("ROLE_EMPLEADO"));
                } else if (r.getClave().getIdrol().equals("REC")) {
                    ga.add(new SimpleGrantedAuthority("ROLE_RECLUTADOR"));
                } else if (r.getClave().getIdrol().equals("CON")) {
                    ga.add(new SimpleGrantedAuthority("ROLE_CONTROLADOR"));
                }

                else if (r.getClave().getIdrol().equals("HAB")) {
                    ga.add(new SimpleGrantedAuthority("ROLE_HABILITADO"));
                } else {
                    throw new UsernameNotFoundException("no roles found");

                }
            }
            return new UsernamePasswordAuthenticationToken(name, pass, ga);

        } catch (Error e) {

            throw new UsernameNotFoundException("could not login");

        }

    }

    /*
     * @Autowired
     * public void configureGlobal(AuthenticationManagerBuilder auth) throws
     * Exception {
     * auth.eraseCredentials(false);
     * }
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
