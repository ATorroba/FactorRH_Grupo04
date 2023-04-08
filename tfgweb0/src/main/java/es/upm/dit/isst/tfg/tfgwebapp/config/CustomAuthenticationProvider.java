package es.upm.dit.isst.tfg.tfgwebapp.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    public final String RHMANAGERGER_STRING = // env.getProperty ("RHMANAGERger.server") + "tfgs/";
            "http://localhost:8083/empleados/";

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String name = authentication.getPrincipal().toString(); // getName();
        if (name.contains("@empleado.es")) {
            List<SimpleGrantedAuthority> ga = new ArrayList<SimpleGrantedAuthority>();
            ga.add(new SimpleGrantedAuthority("ROLE_EMPLEADO"));
            return new UsernamePasswordAuthenticationToken(name, "", ga);
        }
        if (name.contains("@controlador.es")) {
            List<SimpleGrantedAuthority> ga = new ArrayList<SimpleGrantedAuthority>();
            ga.add(new SimpleGrantedAuthority("ROLE_CONTROLADOR"));
            return new UsernamePasswordAuthenticationToken(name, "", ga);
        }
        if (name.contains("@habilitado.es")) {
            List<SimpleGrantedAuthority> ga = new ArrayList<SimpleGrantedAuthority>();
            ga.add(new SimpleGrantedAuthority("ROLE_HABILITADO"));
            return new UsernamePasswordAuthenticationToken(name, "", ga);
        }
        if (name.contains("@reclutador.es")) {
            List<SimpleGrantedAuthority> ga = new ArrayList<SimpleGrantedAuthority>();
            ga.add(new SimpleGrantedAuthority("ROLE_RECLUTADOR"));
            return new UsernamePasswordAuthenticationToken(name, "", ga);
        }
        throw new UsernameNotFoundException("could not login");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}