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
import healthcare.doctors.model.SessionModel;

@Path("session")
public class SessionService {
	
	SessionModel sModel = new SessionModel();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<DoctorDTO> getSessionData(
			@QueryParam("hospital_id") String hospitalID,
			@QueryParam("doc_id") String docID,
			@QueryParam("date")String date,
			@QueryParam("type")String type
			) {
		return sModel.getSessionData(hospitalID,docID,date,type);
		
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DoctorDTO getSessionDataById(@PathParam("id") int sessionId) {
		return sModel.getSessionDataById(sessionId);
	}
	
	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_JSON)
	public String insertIntoDoctors(DoctorDTO doctorDTO) {
		return sModel.insertIntoSession(doctorDTO);
	}
	
	@PUT
	@Path("update/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String UpdateSession(@PathParam("id")int sessionId,DoctorDTO dto) {
		return sModel.UpdateSession(sessionId, dto);
	}
	
	@DELETE
	@Path("delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteSession(@PathParam("id") int sessionId) {
		return sModel.deleteSession(sessionId);
	}
	
}
