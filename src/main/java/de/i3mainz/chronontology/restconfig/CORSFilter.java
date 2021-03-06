package de.i3mainz.chronontology.restconfig;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

public class CORSFilter implements ContainerResponseFilter {

    @Override
    public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
        response.getHttpHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHttpHeaders().add("Access-Control-Allow-Methods", "GET");
        response.getHttpHeaders().add("Access-Control-Allow-Headers", "Content-Type, Accept, Accept-Encoding");
        response.getHttpHeaders().add("Access-Control-Allow-Credentials", "false");
        return response;
    }

}
