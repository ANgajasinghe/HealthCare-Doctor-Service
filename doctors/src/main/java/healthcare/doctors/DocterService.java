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
	public List<DoctorDTO> getAllDoctors(@QueryParam("type") String ALL) {
		return dm.getAllDoctors(ALL);
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
	public String insertIntoDoctors(DoctorDTO doctorDTOs) {
		return dm.insertIntoDoctors(doctorDTOs);
	}
	
	@DELETE
	@Path("delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String DeleteDocAll(@PathParam("id") String docID ) {
		return dm.DeleteDocAll(Integer.valueOf(docID));
	}
	
	@PUT
	@Path("update/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String UpdateDoc(@PathParam("id") String docID , DoctorDTO dto) {
		return dm.UpdateDoc(docID, dto);
	}
	
	

}
