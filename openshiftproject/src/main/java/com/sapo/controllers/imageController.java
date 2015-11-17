package com.sapo.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.sapo.datatypes.DataResponse;
import com.sapo.entities.Producto;
import com.sun.jersey.multipart.FormDataParam;

@Stateless
@LocalBean
@Path("/imagenes")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class imageController {

    @PersistenceContext(unitName="SAPOLogica")
	EntityManager em;
	
	private Cloudinary cloudinary;
	private String cloudname= "drb5oiesn";
	private String api_key= "517377621511646";
	private String api_secret = "fxBV455tLKx1gJIrxCVCH2GpvIs";
	
    public imageController() {
    	 cloudinary= new Cloudinary(ObjectUtils.asMap(
    			  "cloud_name", cloudname,
    			  "api_key", api_key,
    			  "api_secret", api_secret));
    }
    

//curl -X POST http://localhost:8080/openshiftproject/rest/imagenes/upload/7 -H 'Content-Type: application/octet-stream' --data-binary @galli.jpg
    @POST
	@Path("/upload/{producto}")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveImage(@PathParam("producto") Long producto,  byte[] file){	
    	Producto p = em.find(Producto.class, producto);
    	System.out.println(file.toString());
    	
    	try {
			Map up = cloudinary.uploader().upload(file, ObjectUtils.asMap("resource_type", "auto"));
			String url = (String) up.get("url");
			List<String> images = new ArrayList<>();
			images.add(url);
			
			//persisto url en la base
			List<String> images_old = new ArrayList<>();
			if(p.getTags()!=null && !p.getTags().equals("")){
				images_old= Arrays.asList(p.getImagenes().split(","));
			}
			HashSet<String> set = new HashSet<>(images_old);
			set.addAll(images);
			String joined = String.join(",", set);
			p.setImagenes(joined);
			
			
		} catch (IOException e) {
			DataResponse dr = new DataResponse();
			dr.setMensaje("Error");
			dr.setDescripcion(e.getMessage());
			e.printStackTrace();
			return Response.status(500).entity(dr).build();
		}
    	
    	return Response.status(200).build();
    }
}
