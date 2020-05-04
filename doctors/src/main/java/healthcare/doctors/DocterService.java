package healthcare.doctors;

import java.util.List;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import dto.DoctorDTO;
import healthcare.doctors.model.DoctorModel;
import healthcare.doctors.model.IDataModel;

@Path("doc")
public class DocterService {

	private IDataModel dm = new DoctorModel();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public DoctorDTO getAllDoctors(@QueryParam("type") String ALL) {
		return dm.getAllDoctors(ALL);
	}
	
	@GET
	@Path("spec")
	@Produces(MediaType.APPLICATION_JSON)
	public DoctorDTO getAllSpecifications() {
		return dm.getAllSpecifications();
	}
	
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DoctorDTO SelectDocById(@PathParam("id") String id) {
		System.out.println("calling+"+id);
		return dm.SelectDocById(id);
	}
	
	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DoctorDTO insertIntoDoctors(DoctorDTO doctorDTOs) {
		System.out.println("callinfkjkhgcfghjm,.uuuu");
		System.out.println(doctorDTOs);
		return dm.insertIntoDoctors(doctorDTOs);
	}
	
	@DELETE
	@Path("delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DoctorDTO DeleteDocAll(@PathParam("id") String docID ) {
		return dm.DeleteDoc(Integer.valueOf(docID));
	}
	
	@PUT
	@Path("update/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String UpdateDoc(@PathParam("id") String docID , DoctorDTO dto) {
		System.out.println("calling+ "+ dto);
		//return dm.UpdateDoc(docID, dto);
		return "sddddd";
	}
	
	

}
