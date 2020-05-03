//package healthcare.doctors.model;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//
//import dto.DoctorDTO;
//import utility.ConnectionBuilder;
//import utility.Messages;
//
//public class SessionModel {
//
//	private final ConnectionBuilder cBuilder = new ConnectionBuilder();
//
//	public boolean connectionChecker(Connection MYSQLcon) {
//
//		if (MYSQLcon == null) {
//			return false;
//		}
//		return true;
//	}
//
//	public List<DoctorDTO> getSessionData(String hospitalID, String docID, String Date ,String type) {
//
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		Date dateobj = new Date();
//
//		String currentData = df.format(dateobj);
//
//		List<DoctorDTO> sessionList = new ArrayList<DoctorDTO>();
//		Connection MYSQLcon = cBuilder.MYSQLConnection();
//
//		if (this.connectionChecker(MYSQLcon)) {
//			StringBuilder sBuilder = new StringBuilder();
//			sBuilder.append("SELECT \n");
//			sBuilder.append("s.session_id,");
//			sBuilder.append("s.hostpital_id,");
//			sBuilder.append("s.available_date,");
//			sBuilder.append("s.start_time,");
//			sBuilder.append("s.end_time,");
//			sBuilder.append("s.isEveryday,");
//			sBuilder.append("d.doc_reg_no,");
//			sBuilder.append("d.doc_first_name,");
//			sBuilder.append("d.doc_last_name,");
//			sBuilder.append("d.doc_tp1,");
//			sBuilder.append("d.doc_city,");
//			sBuilder.append("d.doc_email,");
//			sBuilder.append("p.specification_name \n");
//			sBuilder.append("FROM doc_session s \n");
//			sBuilder.append("INNER JOIN doctors d ON d.doc_id = s.doc_id \n");
//			sBuilder.append("INNER JOIN doc_specification p ON p.specification_id = d.doc_specification_id \n");
//			sBuilder.append("WHERE (s.available_date >= ? || s.isEveryday = 1)");
//
//			if (hospitalID != null) {
//				sBuilder.append("\n AND s.hostpital_id =" + hospitalID);
//			}
//			if (docID != null) {
//				sBuilder.append("\n AND s.doc_id =" + docID);
//			}
//			if (!(type != null && type.equals("all"))) {
//				sBuilder.append("\n AND s.isActive = 1");
//			}
//
//			
//
//			String queryString = sBuilder.toString();
//
//			try {
//				PreparedStatement pStatement = MYSQLcon.prepareStatement(queryString);
//				if (Date == null) {
//					pStatement.setString(1, currentData);
//				} else {
//					pStatement.setString(1, Date);
//				}
//
//				ResultSet rs = pStatement.executeQuery();
//				while (rs.next()) {
//					DoctorDTO dto = new DoctorDTO();
//
//					dto.setSession_id(rs.getInt("session_id"));
//					dto.setDoc_first_name(rs.getString("doc_first_name"));
//					dto.setSpecification_name(rs.getString("specification_name"));
//					dto.setHospital_id(rs.getInt("hostpital_id"));
//					dto.setStart_time(rs.getString("start_time"));
//
//					sessionList.add(dto);
//				}
//			} catch (SQLException e) {
//
//				e.printStackTrace();
//			}
//
//		}
//
//		return (sessionList.isEmpty()) ? null : sessionList;
//
//	}
//
//	public DoctorDTO getSessionDataById(int sessionId) {
//		Connection MYSQLcon = cBuilder.MYSQLConnection();
//		DoctorDTO dto = new DoctorDTO();
//		if (this.connectionChecker(MYSQLcon)) {
//			StringBuilder sBuilder = new StringBuilder();
//			sBuilder.append("SELECT \n");
//			sBuilder.append("* \t");
//			sBuilder.append("FROM \n");
//			sBuilder.append("doc_session \n");
//			sBuilder.append("WHERE 	session_id =? AND isActive = 1\n");
//
//			String queryString = sBuilder.toString();
//
//			try {
//				PreparedStatement pStatement = MYSQLcon.prepareStatement(queryString);
//				pStatement.setInt(1, sessionId);
//
//				ResultSet rs = pStatement.executeQuery();
//				while (rs.next()) {
//					dto.setSession_id(rs.getInt("session_id"));
//					dto.setHospital_id(rs.getInt("hostpital_id"));
//					dto.setDoc_id(rs.getInt("doc_id"));
//					dto.setAvailable_date(rs.getString("available_date"));
//					dto.setStart_time(rs.getString("start_time"));
//					dto.setEnd_time(rs.getString("end_time"));
//					dto.setIsEveryday(rs.getInt("isEveryday"));
//					dto.setIsActive(rs.getInt("isActive"));
//					dto.setPatient_limit(rs.getInt("patient_limit"));
//					dto.setPrice(rs.getDouble("price"));
//					return dto;
//				}
//
//			} catch (SQLException e) {
//				e.printStackTrace();
//				dto.setIsActive(0);
//				return dto;
//			} finally {
//				try {
//					MYSQLcon.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//
//		} else {
//			System.out.println("connection not establish correctly");
//		}
//
//		return null;
//	}
//
//	public String insertIntoSession(DoctorDTO doctorDTOs) {
//		Connection MYSQLcon = cBuilder.MYSQLConnection();
//		StringBuilder sBuilder = new StringBuilder();
//		sBuilder.append("INSERT INTO doc_session ( \n");
//		sBuilder.append("hostpital_id, \n");
//		sBuilder.append("doc_id, \n");
//		sBuilder.append("available_date, \n");
//		sBuilder.append("start_time, \n");
//		sBuilder.append("end_time, \n");
//		sBuilder.append("isEveryday, \n");
//		sBuilder.append("isActive, \n");
//		sBuilder.append("patient_limit, \n");
//		sBuilder.append("price )\n");
//		sBuilder.append("VALUES (\n");
//		sBuilder.append("?,?,?,?,?,?,?,?,?\n");
//		sBuilder.append(")");
//		String queryString = sBuilder.toString();
//
//		try {
//			PreparedStatement pStatement = MYSQLcon.prepareStatement(queryString);
//			pStatement.setInt(1, doctorDTOs.getHospital_id());
//			pStatement.setInt(2, doctorDTOs.getDoc_id());
//			pStatement.setString(3, doctorDTOs.getAvailable_date());
//			pStatement.setString(4, doctorDTOs.getStart_time());
//			pStatement.setString(5, doctorDTOs.getEnd_time());
//			pStatement.setInt(6, doctorDTOs.getIsEveryday());
//			pStatement.setInt(7, doctorDTOs.getIsActive());
//			pStatement.setInt(8, doctorDTOs.getPatient_limit());
//			pStatement.setDouble(9, doctorDTOs.getPrice());
//
//			boolean result = pStatement.execute();
//
//			if (!result) {
//				return Messages.sessionnDtaOK;
//			}
//
//		} catch (SQLException e) {
//			return Messages.insertSessonErr + e.getMessage();
//
//		} catch (Exception e) {
//			return Messages.insertSessonErr + e.getMessage();
//		} finally {
//			try {
//				MYSQLcon.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//				return Messages.insertSessonErr + e.getMessage();
//			}
//		}
//		return Messages.insertSessonErr;
//
//	}
//
//	private DoctorDTO getAllSessionByID(int sessionId) {
//		Connection MYSQLcon = cBuilder.MYSQLConnection();
//		DoctorDTO dto = new DoctorDTO();
//		if (this.connectionChecker(MYSQLcon)) {
//			StringBuilder sBuilder = new StringBuilder();
//			sBuilder.append("SELECT \n");
//			sBuilder.append("* \t");
//			sBuilder.append("FROM \n");
//			sBuilder.append("doc_session \n");
//			sBuilder.append("WHERE 	session_id =? \n");
//
//			String queryString = sBuilder.toString();
//
//			try {
//				PreparedStatement pStatement = MYSQLcon.prepareStatement(queryString);
//				pStatement.setInt(1, sessionId);
//
//				ResultSet rs = pStatement.executeQuery();
//				while (rs.next()) {
//					dto.setSession_id(rs.getInt("session_id"));
//					dto.setHospital_id(rs.getInt("hostpital_id"));
//					dto.setDoc_id(rs.getInt("doc_id"));
//					dto.setAvailable_date(rs.getString("available_date"));
//					dto.setStart_time(rs.getString("start_time"));
//					dto.setEnd_time(rs.getString("end_time"));
//					dto.setIsEveryday(rs.getInt("isEveryday"));
//					dto.setIsActive(rs.getInt("isActive"));
//					dto.setPatient_limit(rs.getInt("patient_limit"));
//					dto.setPrice(rs.getDouble("price"));
//					return dto;
//				}
//
//			} catch (SQLException e) {
//				e.printStackTrace();
//				dto.setIsActive(0);
//				return dto;
//			} finally {
//				try {
//					MYSQLcon.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//
//		} else {
//			System.out.println("connection not establish correctly");
//		}
//
//		return null;
//	}
//
//	public String UpdateSession(int sessionId, DoctorDTO dto) {
//
//		DoctorDTO currentSession = this.getAllSessionByID(sessionId);
//		Connection MYSQLcon = cBuilder.MYSQLConnection();
//
//		if (currentSession != null) {
//			DoctorDTO doctorDTOs = MapSessionData(dto, currentSession);
//			StringBuilder sBuilder = new StringBuilder();
//
//			sBuilder.append("UPDATE doc_session SET \n");
//			sBuilder.append("hostpital_id = ? ,");
//			sBuilder.append("doc_id = ? ,");
//			sBuilder.append("available_date = ?,");
//			sBuilder.append("start_time = ? ,");
//			sBuilder.append("end_time = ? ,");
//			sBuilder.append("isEveryday = ?,");
//			sBuilder.append("isActive = ?,");
//			sBuilder.append("patient_limit = ?,");
//			sBuilder.append("price = ? \n");
//			sBuilder.append("WHERE\n");
//			sBuilder.append("session_id = ?");
//			String queryString = sBuilder.toString();
//			PreparedStatement pStatement;
//			try {
//				pStatement = MYSQLcon.prepareStatement(queryString);
//				pStatement.setInt(1, doctorDTOs.getHospital_id());
//				pStatement.setInt(2, doctorDTOs.getDoc_id());
//				pStatement.setString(3, doctorDTOs.getAvailable_date());
//				pStatement.setString(4, doctorDTOs.getStart_time());
//				pStatement.setString(5, doctorDTOs.getEnd_time());
//				pStatement.setInt(6, doctorDTOs.getIsEveryday());
//				pStatement.setInt(7, doctorDTOs.getIsActive());
//				pStatement.setInt(8, doctorDTOs.getPatient_limit());
//				pStatement.setDouble(9, doctorDTOs.getPrice());
//				pStatement.setDouble(10, sessionId);
//				pStatement.execute();
//				pStatement.close();
//
//				return Messages.updateSuccess;
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return Messages.systemFail+e.getMessage();
//			}
//
//		} else {
//			return Messages.insertSessonErr+"Invalide Id";
//		}
//
//	}
//
//	public String deleteSession(int sessionId) {
//
//		DoctorDTO currentSession = this.getAllSessionByID(sessionId);
//		Connection MYSQLcon = cBuilder.MYSQLConnection();
//
//		if (currentSession != null) {
//			String qurtString01 = "DELETE FROM doc_session WHERE session_id = ? \n";
//			PreparedStatement pStatement = null;
//
//			try {
//				pStatement = MYSQLcon.prepareStatement(qurtString01);
//				pStatement.setInt(1, sessionId);
//				pStatement.execute();
//				return Messages.DeleteSuccess;
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return Messages.systemFail+e.getMessage();
//			} finally {
//				try {
//					MYSQLcon.close();
//					pStatement.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//			}
//
//		} else {
//			return Messages.insertSessonErr+"Invalide Id";
//		}
//
//	}
//
//	private DoctorDTO MapSessionData(DoctorDTO dto, DoctorDTO currentSession) {
//
//		dto.setHospital_id((dto.getHospital_id() == null) ? currentSession.getHospital_id() : dto.getHospital_id());
//		dto.setDoc_id((dto.getDoc_id() == null) ? currentSession.getDoc_id() : dto.getDoc_id());
//		dto.setAvailable_date(
//				(dto.getAvailable_date() == null) ? currentSession.getAvailable_date() : dto.getAvailable_date());
//		dto.setStart_time((dto.getStart_time() == null) ? currentSession.getStart_time() : dto.getStart_time());
//		dto.setEnd_time((dto.getEnd_time() == null) ? currentSession.getEnd_time() : dto.getEnd_time());
//		dto.setIsEveryday((dto.getIsEveryday() == null) ? currentSession.getIsEveryday() : dto.getIsEveryday());
//		dto.setIsActive((dto.getIsActive() == null) ? currentSession.getIsActive() : dto.getIsActive());
//		dto.setPatient_limit(
//				(dto.getPatient_limit() == null) ? currentSession.getPatient_limit() : dto.getPatient_limit());
//		dto.setPrice((dto.getPrice() == null) ? currentSession.getPrice() : dto.getPrice());
//
//		return dto;
//	}
//}
