package dto;


import java.util.List;


public class DoctorDTO {

	private Integer doc_id;//primary key-doctor
	
	private String doc_reg_no;
	private String doc_first_name;
	private String doc_last_name;
	private String doc_address;
	private String doc_address_no;
	private String doc_address_lane1;
	private String doc_address_lane2;
	private String doc_address_lane3;
	private String doc_city;
	private String doc_tp1;
	private String doc_tp2;
	private String doc_tp3;
	private String doc_email;
	
	private Integer doc_status_id;//pimary key status
	private String ststus_dis;
	
	private Integer specification_id;//peimary key spec
	private String specification_name;
	private String specification_dis;
	
	
	

	public Integer getDoc_id() {
		return doc_id;
	}

	public void setDoc_id(Integer doc_id) {
		this.doc_id = doc_id;
	}

	public String getDoc_reg_no() {
		return doc_reg_no;
	}

	public void setDoc_reg_no(String doc_reg_no) {
		this.doc_reg_no = doc_reg_no;
	}

	public String getDoc_first_name() {
		return doc_first_name;
	}

	public void setDoc_first_name(String doc_first_name) {
		this.doc_first_name = doc_first_name;
	}

	public String getDoc_last_name() {
		return doc_last_name;
	}

	public void setDoc_last_name(String doc_last_name) {
		this.doc_last_name = doc_last_name;
	}

	public String getDoc_address() {
		return doc_address;
	}

	public void setDoc_address(String doc_address) {
		this.doc_address = doc_address;
	}

	public String getDoc_address_no() {
		return doc_address_no;
	}

	public void setDoc_address_no(String doc_address_no) {
		this.doc_address_no = doc_address_no;
	}

	public String getDoc_address_lane1() {
		return doc_address_lane1;
	}

	public void setDoc_address_lane1(String doc_address_lane1) {
		this.doc_address_lane1 = doc_address_lane1;
	}

	public String getDoc_address_lane2() {
		return doc_address_lane2;
	}

	public void setDoc_address_lane2(String doc_address_lane2) {
		this.doc_address_lane2 = doc_address_lane2;
	}

	public String getDoc_address_lane3() {
		return doc_address_lane3;
	}

	public void setDoc_address_lane3(String doc_address_lane3) {
		this.doc_address_lane3 = doc_address_lane3;
	}

	public String getDoc_city() {
		return doc_city;
	}

	public void setDoc_city(String doc_city) {
		this.doc_city = doc_city;
	}

	public String getDoc_tp1() {
		return doc_tp1;
	}

	public void setDoc_tp1(String doc_tp1) {
		this.doc_tp1 = doc_tp1;
	}

	public String getDoc_tp2() {
		return doc_tp2;
	}

	public void setDoc_tp2(String doc_tp2) {
		this.doc_tp2 = doc_tp2;
	}

	public String getDoc_tp3() {
		return doc_tp3;
	}

	public void setDoc_tp3(String doc_tp3) {
		this.doc_tp3 = doc_tp3;
	}

	public String getDoc_email() {
		return doc_email;
	}

	public void setDoc_email(String doc_email) {
		this.doc_email = doc_email;
	}

	public Integer getDoc_status_id() {
		return doc_status_id;
	}

	public void setDoc_status_id(Integer doc_status_id) {
		this.doc_status_id = doc_status_id;
	}

	public String getStstus_dis() {
		return ststus_dis;
	}

	public void setStstus_dis(String ststus_dis) {
		this.ststus_dis = ststus_dis;
	}

	public Integer getSpecification_id() {
		return specification_id;
	}

	public void setSpecification_id(Integer specification_id) {
		this.specification_id = specification_id;
	}

	public String getSpecification_name() {
		return specification_name;
	}

	public void setSpecification_name(String specification_name) {
		this.specification_name = specification_name;
	}

	public String getSpecification_dis() {
		return specification_dis;
	}

	public void setSpecification_dis(String specification_dis) {
		this.specification_dis = specification_dis;
	}
	

	//Error DTO

	private ErrorDTO Error;
	public ErrorDTO getError() {
		return Error;
	}

	public void setError(ErrorDTO error) {
		Error = error;
	}

	//doc list
	private List<DoctorDTO> doc_list;
	private Integer response_status;
	
	public List<DoctorDTO> getDoc_list() {
		return doc_list;
	}

	public void setDoc_list(List<DoctorDTO> doc_list) {
		this.doc_list = doc_list;
	}

	//status
	public Integer getResponse_status() {
		return response_status;
	}

	public void setResponse_status(Integer response_status) {
		this.response_status = response_status;
	}



	


	
	
	
	
	
}
