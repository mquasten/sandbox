package de.mq.profile.web;

import java.util.Set;

import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("rest")
public class WebsterRESTConfig extends Application {
 
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }
 
   private void addRestResourceClasses(Set<Class<?>> resources) {
     resources.add(ProfileService.class);
    }
}