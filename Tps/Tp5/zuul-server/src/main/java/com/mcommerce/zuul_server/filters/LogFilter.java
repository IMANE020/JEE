package com.mcommerce.zuul_server.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class LogFilter extends ZuulFilter {
    Logger logger = LoggerFactory.getLogger(this.getClass()); //pour écrire des messages dans la console

    @Override
    public String filterType() {
        return "pre";
    }

    // Définit l’ordre d’exécution du filtre si plusieurs filtres sont présents (Un nombre plus petit = priorité plus haute)
    @Override
    public int filterOrder() {
        return 1;
    }


    //le filtre s’exécute pour toutes les requêtes
    @Override
    public boolean shouldFilter() {
        return true;
    }

    //le coeur de zuul, log, vérification, blocage, modification…
    @Override
    public Object run() {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest(); //récupère la requête HTTP courante.
        logger.info("=== ZUUL GATEWAY === Requête interceptée : {} {}",
                request.getMethod(), request.getRequestURL()); //affiche un message dans la console
        return null;
    }
}
